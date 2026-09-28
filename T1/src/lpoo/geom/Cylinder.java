package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class Cylinder extends Shape {
    private final float radius;
    private final float halfHeight;

    public Cylinder(String name, float density, float radius, float halfHeight) {
        super(name, density);
        this.radius = radius;
        this.halfHeight = halfHeight;
    }

    @Override
    public float getVolume() {
        return (float)(2 * Math.PI * radius * radius * halfHeight);
    }

    @Override
    public float getSurfaceArea() {
        return (float)((2 * Math.PI * radius * radius) + (4 * Math.PI * radius) * halfHeight);
    }

    @Override
    public Vector3 getCenterOfMass() {
        return Vector3.NULL;
    }

    @Override
    public Matrix3 getInertialTensor() {
        float mass = getMass();
        
        float Ixx = (mass / 12) * ((3 * radius * radius) + (4 * halfHeight * halfHeight));
        float Iyy = (mass * radius * radius) / 2.0f;

        return Matrix3.diagonal(Ixx, Iyy, Ixx);
    }

    @Override
    public Bounds3 getBounds() {
        Vector3 min = new Vector3(-radius, -halfHeight, -radius);
        Vector3 max = new Vector3(radius, halfHeight, radius);

        return new Bounds3(min, max);
    }
}