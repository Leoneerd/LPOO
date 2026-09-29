package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;
import java.util.List;

public class CompoundShapeInstance extends Shape {
    private final CompoundShape definition;

    public CompoundShapeInstance(String name, CompoundShape definition, Pose pose) {
        super(name, 0, pose);
        this.definition = definition;
    }

    @Override
    public float getMass() {
        return definition.getMass();
    }

    @Override
    public float getVolume() {
        return definition.getVolume();
    }

    @Override
    public float getSurfaceArea() {
        return definition.getSurfaceArea();
    }

    @Override
    public Vector3 getCenterOfMass() {
        return definition.getCenterOfMass();
    }

    @Override
    public Matrix3 getInertialTensor() {
        return definition.getInertialTensor();
    }

    @Override
    public Bounds3 getBounds() {
        return definition.getBounds();
    }

    public CompoundShape getDefinition() {
        return definition;
    }

    public List<Shape> getChildren() {
        return definition.getChildren();
    }
}
