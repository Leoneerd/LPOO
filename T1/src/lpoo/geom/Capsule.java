package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class Capsule extends Shape {
    private final float radius;
    private final float halfHeight;

    public Capsule(String name, float density, float radius, float halfHeight) {
        super(name, density);
        this.radius = radius;
        this.halfHeight = halfHeight;
    }

    @Override
    public float getVolume() {
        return (float)((2 * Math.PI * radius * radius * halfHeight) + ((4.0/3.0) * Math.PI * radius * radius * radius));
    }

    @Override
    public float getSurfaceArea() {
        return (float)((4.0 * Math.PI * radius * radius) + ((4 * Math.PI * radius) * halfHeight));
    }

    @Override
    public Vector3 getCenterOfMass() {
        return Vector3.NULL;
    }

    @Override
    public Matrix3 getInertialTensor() {
        float density = getDensity();
        float cylinderMass = (float)(density * Math.PI * radius * radius * (2 * halfHeight));
        float hemispheresMass = (float)(density * (4.0 / 3.0) * Math.PI * radius * radius * radius);
        float offset = halfHeight + 3.0f * radius / 8.0f;

        float Ixx = cylinderMass / 12.0f * (3 * radius * radius + 4 * halfHeight * halfHeight) + hemispheresMass * (83.0f / 320.0f * radius * radius + offset * offset);
        float Iyy = 0.5f * cylinderMass * radius * radius + 0.4f * hemispheresMass * radius * radius;

        return Matrix3.diagonal(Ixx, Iyy, Ixx);
    }

    @Override
    public Bounds3 getBounds() {
        Vector3 min = new Vector3(-radius, -halfHeight-radius, -radius);
        Vector3 max = new Vector3(radius, halfHeight+radius, radius);

        return new Bounds3(min, max);
    }
}