package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class Sphere extends Shape {
    private final float radius;

    public Sphere(String name, float density, float radius) {
        super(name, density);
        this.radius = radius;
    }

    public Sphere(String name, float density, Pose pose, float radius) {
        super(name, density, pose);
        this.radius = radius;
    }

    @Override
    public float getVolume() {
        return (float)((4.0/3.0) * Math.PI * radius * radius * radius);
    }
    
    @Override
    public float getSurfaceArea() {
        return (float)(4.0 * Math.PI * radius * radius);
    }

    @Override
    public Vector3 getCenterOfMass() {
        return Vector3.NULL;
    }

    @Override
    public Matrix3 getInertialTensor() {
        float mass = getMass();
        return Matrix3.diagonal((2.0f/5.0f) * mass * radius * radius);
    }

    @Override
    public Bounds3 getBounds() {
        Vector3 min = new Vector3(-radius, -radius, -radius);
        Vector3 max = new Vector3(radius, radius, radius);
        return new Bounds3(min, max);
    }
    
}