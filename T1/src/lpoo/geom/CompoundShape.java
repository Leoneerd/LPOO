package lpoo.geom;

import lpoo.math.Matrix3;
import lpoo.math.Vector3;
import java.util.List;
import java.util.ArrayList;

public class CompoundShape extends Shape {
    private final List<Shape> ListShape;

    public CompoundShape(String name, Pose pose, List<Shape> ListShape) {
        super(name, 0, pose);
        this.ListShape = new ArrayList<>(ListShape);
    }

    public List<Shape> getChildren() {
        return new ArrayList<>(ListShape);
    }

    @Override
    public float getMass() {
        float totalMass = 0;
        for (int i = 0; i < ListShape.size(); i++) {
            totalMass += ListShape.get(i).getMass();
        }

        return totalMass;
    }

    @Override
    public float getVolume() {
        float totalVolume = 0;
        for (int i = 0; i < ListShape.size(); i++) {
            totalVolume += ListShape.get(i).getVolume();
        }

        return totalVolume;
    }

    @Override
    public float getSurfaceArea() {
        float totalArea = 0;
        for (int i = 0; i < ListShape.size(); i++) {
            totalArea += ListShape.get(i).getSurfaceArea();
        }

        return totalArea;
    }

    @Override
    public Vector3 getCenterOfMass() {
        float totalMass = 0;
        float weightedX = 0;
        float weightedY = 0;
        float weightedZ = 0;

        for (int i = 0; i < ListShape.size(); i++) {
            Shape child = ListShape.get(i);
            float childMass = child.getMass();

            Vector3 childCenter = child.getPose().transformPoint(child.getCenterOfMass());

            totalMass += childMass;
            weightedX += childMass * childCenter.x;
            weightedY += childMass * childCenter.y;
            weightedZ += childMass * childCenter.z;
        }

        return new Vector3(weightedX / totalMass, weightedY / totalMass, weightedZ / totalMass);
    }

    @Override
    public Matrix3 getInertialTensor() {
        Matrix3 totalTensor = Matrix3.zero();
        Vector3 center = getCenterOfMass();

        for (int i = 0; i < ListShape.size(); i++) {
            Shape child = ListShape.get(i);
            float childMass = child.getMass();

            Matrix3 rotation = child.getPose().getRotationMatrix();
            Matrix3 rotatedTensor = rotation.mul(child.getInertialTensor()).mul(rotation.transpose());

            Vector3 childCenter = child.getPose().transformPoint(child.getCenterOfMass());
            Vector3 offset = childCenter.sub(center);

            Matrix3 parallelAxisTensor = Matrix3.diagonal(childMass * offset.normSquared()).add(Matrix3.outer(offset, -childMass));

            totalTensor = totalTensor.add(rotatedTensor).add(parallelAxisTensor);
        }

        return totalTensor;
    }

    @Override
    public Bounds3 getBounds() {
        Bounds3 compoundBounds = new Bounds3();
        
        for (int i = 0; i < ListShape.size(); i++) {
            Shape child = ListShape.get(i);
            Bounds3 childBounds = child.getBounds();

            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    for (int z = 0; z < 2; z++) {
                        float cornerX;
                        float cornerY;
                        float cornerZ;

                        if (x == 0) {
                            cornerX = childBounds.min().x;
                        } else {
                            cornerX = childBounds.max().x;
                        }

                        if (y == 0) {
                            cornerY = childBounds.min().y;
                        } else {
                            cornerY = childBounds.max().y;
                        }

                        if (z == 0) {
                            cornerZ = childBounds.min().z;
                        } else {
                            cornerZ = childBounds.max().z;
                        }

                        Vector3 corner = new Vector3(cornerX, cornerY, cornerZ);
                        Vector3 transformedCorner = child.getPose().transformPoint(corner);
                        compoundBounds.expand(transformedCorner);
                    }
                }
            }
        }
    
        return compoundBounds;
    }
}