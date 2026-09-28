package lpoo.geom;


import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class Box extends Shape {
    private final float halfSizeX;
    private final float halfSizeY;
    private final float halfSizeZ;

    public Box(String name, float density, float halfSizeX, float halfSizeY, float halfSizeZ) {
        super(name, density);
        this.halfSizeX = halfSizeX;
        this.halfSizeY = halfSizeY;
        this.halfSizeZ = halfSizeZ;
    }

    public float getVolume() {
        return 8 * halfSizeX * halfSizeY * halfSizeZ;
    }

    public float getSurfaceArea() {
        return 8 * (halfSizeX * halfSizeY + halfSizeX * halfSizeZ + halfSizeY * halfSizeZ);
    }

    public Vector3 getCenterOfMass() {
        return Vector3.NULL;
    }

    public Matrix3 getInertialTensor() {
        float mass = getMass();

        float Ixx = mass / 3.0f * (halfSizeY * halfSizeY + halfSizeZ * halfSizeZ);
        float Iyy = mass / 3.0f * (halfSizeX * halfSizeX + halfSizeZ * halfSizeZ);
        float Izz = mass / 3.0f * (halfSizeX * halfSizeX + halfSizeY * halfSizeY);

        return Matrix3.diagonal(Ixx, Iyy, Izz);
    }

    public Bounds3 getBounds() {
        Vector3 min = new Vector3(-halfSizeX, -halfSizeY, -halfSizeZ);
        Vector3 max = new Vector3(halfSizeX, halfSizeY, halfSizeZ);

        return new Bounds3(min, max);
    }
}