// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo.geom;


import lpoo.math.Matrix3;
import lpoo.math.Vector3;
import lpoo.exception.BadDimensionsException;

public class Box extends Shape {
    private final float halfSizeX;
    private final float halfSizeY;
    private final float halfSizeZ;

    private static void validateHalfSize(String parameter, float value) throws BadDimensionsException {
        if (value <= 0.0f) {
            throw new BadDimensionsException(parameter + " o valor precisa ser maior que zero " + value);
        }
    }

    public Box(String name, float density, float halfSizeX, float halfSizeY, float halfSizeZ) throws BadDimensionsException {
        super(name, density);
        validateDensity(density);
        validateHalfSize("halfSizeX", halfSizeX);
        validateHalfSize("halfSizeY", halfSizeY);
        validateHalfSize("halfSizeZ", halfSizeZ);
        this.halfSizeX = halfSizeX;
        this.halfSizeY = halfSizeY;
        this.halfSizeZ = halfSizeZ;
    }

    public Box(String name, float density, Pose pose, float halfSizeX, float halfSizeY, float halfSizeZ) throws BadDimensionsException {
        super(name, density, pose);
        validateDensity(density);
        validateHalfSize("halfSizeX", halfSizeX);
        validateHalfSize("halfSizeY", halfSizeY);
        validateHalfSize("halfSizeZ", halfSizeZ);
        this.halfSizeX = halfSizeX;
        this.halfSizeY = halfSizeY;
        this.halfSizeZ = halfSizeZ;
    }

    @Override
    public float getVolume() {
        return 8 * halfSizeX * halfSizeY * halfSizeZ;
    }

    @Override
    public float getSurfaceArea() {
        return 8 * (halfSizeX * halfSizeY + halfSizeX * halfSizeZ + halfSizeY * halfSizeZ);
    }

    @Override
    public Vector3 getCenterOfMass() {
        return Vector3.NULL;
    }

    @Override
    public Matrix3 getInertialTensor() {
        float mass = getMass();

        float Ixx = mass / 3.0f * (halfSizeY * halfSizeY + halfSizeZ * halfSizeZ);
        float Iyy = mass / 3.0f * (halfSizeX * halfSizeX + halfSizeZ * halfSizeZ);
        float Izz = mass / 3.0f * (halfSizeX * halfSizeX + halfSizeY * halfSizeY);

        return Matrix3.diagonal(Ixx, Iyy, Izz);
    }

    @Override
    public Bounds3 getBounds() {
        Vector3 min = new Vector3(-halfSizeX, -halfSizeY, -halfSizeZ);
        Vector3 max = new Vector3(halfSizeX, halfSizeY, halfSizeZ);

        return new Bounds3(min, max);
    }
}