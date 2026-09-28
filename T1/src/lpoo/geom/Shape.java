package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public abstract class Shape {
    private final String name;
    private final float density;

    protected Shape(String name, float density) {
        this.name = name;
        this.density = density;
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

    public abstract float getSurfaceArea();
    public abstract float getVolume();
    public abstract Vector3 getCenterOfMass();
    public abstract Matrix3 getInertialTensor();
    public abstract Bounds3 getBounds();

}