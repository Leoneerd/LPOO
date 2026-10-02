package lpoo.geom;

import lpoo.math.Quaternion;
import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public abstract class Shape {

    private final String name;
    private final float density;
    private final Pose pose;

    protected static void validateDensity(float density) {
        if (density <= 0.0f) {
            throw new IllegalArgumentException("Densidade precisa ser maior que zero " + density);
        }
    }

    protected Shape(String name, float density) {
        this(name, density, new Pose(Vector3.NULL, Quaternion.IDENTITY));
    }

    protected Shape(String name, float density, Pose pose) {
        this.name = name;
        this.density = density;
        this.pose = pose;
    }

    public String getName() {
        return name;
    }

    public float getDensity() {
        return density;
    }

    public  float getMass() {
        return density * getVolume();
    }

    public Pose getPose() {
        return pose;
    }

    public abstract float getSurfaceArea();
    public abstract float getVolume();
    public abstract Vector3 getCenterOfMass();
    public abstract Matrix3 getInertialTensor();
    public abstract Bounds3 getBounds();

}