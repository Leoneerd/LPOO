package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class MeshShape extends Shape {
    private final TriangleMesh mesh;
    private final MassProperties massProperties;

    public MeshShape(String name, float density, Pose pose, TriangleMesh mesh) {
        super(name, density, pose);
        this.mesh = mesh;
        this.massProperties = computeMassProperties(mesh, getDensity());
    }

    public TriangleMesh getMesh() {
        return mesh;
    }

    @Override
    public float getSurfaceArea() {
        float totalArea = 0;

        for(int t = 0; t < mesh.triangleCount(); t++) {
            Index3 triangle = mesh.triangle(t);
            Vector3 a = mesh.vertex(triangle.i);
            Vector3 b = mesh.vertex(triangle.j);
            Vector3 c = mesh.vertex(triangle.k);

            Vector3 u = b.sub(a);
            Vector3 v = c.sub(a);

            float crossX = u.y * v.z - u.z * v.y;
            float crossY = u.z * v.x - u.x * v.z;
            float crossZ = u.x * v.y - u.y * v.x;

            float triangleArea = 0.5f * new Vector3(crossX, crossY, crossZ).norm();

            totalArea += triangleArea;
        }

        return totalArea;
    }

    @Override
    public float getVolume() {
        return massProperties.volume;
    }

    @Override
    public Vector3 getCenterOfMass() {
        return massProperties.center;
    }

    @Override
    public Matrix3 getInertialTensor() {
        return massProperties.inertialTensor;
    }

    @Override
    public Bounds3 getBounds() {
        Bounds3 bounds = new Bounds3();

        for (int i = 0; i < mesh.vertexCount(); i++) {
            bounds.expand(mesh.vertex(i));
        }

        return bounds;
    }

    private static MassProperties computeMassProperties(TriangleMesh mesh, float density) {
        float volume = 0.0f;
        Vector3 firstMoment = Vector3.NULL;
        Matrix3 secondMoment = Matrix3.zero();

        for (int t = 0; t < mesh.triangleCount(); t++) {
            Index3 triangle = mesh.triangle(t);
            Vector3 a = mesh.vertex(triangle.i);
            Vector3 b = mesh.vertex(triangle.j);
            Vector3 c = mesh.vertex(triangle.k);

            float tetrahedronVolume = a.dot(cross(b,c)) / 6.0f;
            Vector3 vertexSum = a.add(b).add(c);

            volume += tetrahedronVolume;
            firstMoment = firstMoment.add(vertexSum.mul(tetrahedronVolume / 4.0f));

            Matrix3 tetrahedronSecondMoment = Matrix3.outer(vertexSum, 1.0f).add(Matrix3.outer(a, 1.0f)).add(Matrix3.outer(b, 1.0f)).add(Matrix3.outer(c, 1.0f)).mul(tetrahedronVolume / 20.0f);
            
            secondMoment = secondMoment.add(tetrahedronSecondMoment);
        }

        if (volume == 0.0f) {
            throw new IllegalArgumentException(
                    "Mesh must enclose a non-zero volume");
        }
        
        if (volume < 0.0f) {
            volume = -volume;
            firstMoment = firstMoment.mul(-1.0f);
            secondMoment = secondMoment.mul(-1.0f);
        }

        Vector3 center = firstMoment.mul(1.0f / volume);
        Matrix3 centeredSecondMoment = secondMoment.add(Matrix3.outer(center, -volume));
        float trace = centeredSecondMoment.get(0, 0) + centeredSecondMoment.get(1, 1) + centeredSecondMoment.get(2, 2);
        Matrix3 inertialTensor = Matrix3.identity().mul(density * trace).add(centeredSecondMoment.mul(-density));

        return new MassProperties(volume, center, inertialTensor);
    }

    private static Vector3 cross(Vector3 u, Vector3 v) {
        return new Vector3(u.y * v.z - u.z * v.y, u.z * v.x - u.x * v.z, u.x * v.y - u.y * v.x);
    }

    private static final class MassProperties {
        final float volume;
        final Vector3 center;
        final Matrix3 inertialTensor;

        MassProperties(float volume, Vector3 center, Matrix3 inertialTensor) {
            this.volume = volume;
            this.center = center;
            this.inertialTensor = inertialTensor;
        }
    }
}