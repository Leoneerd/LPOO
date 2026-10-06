// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo.phyx;

import lpoo.geom.Bounds3;
import lpoo.geom.Pose;
import lpoo.geom.Shape;
import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public class RigidBody {
    private final String name;
    private final Shape shape;
    private final Pose pose;

    public RigidBody(String name, Shape shape, Pose pose) {
        this.name = name;
        this.shape = shape;
        this.pose = pose;
    }

    public String getName() {
        return name;
    }

    public Shape getShape() {
        return shape;
    }

    public Pose getPose() {
        return pose;
    }

    public float getMass() {
        return shape.getMass();
    }

    public float getVolume() {
        return shape.getVolume();
    }

    public float getSurfaceArea() {
        return shape.getSurfaceArea();
    }

    public Vector3 getCenterOfMass() {
        Vector3 center = shape.getCenterOfMass();
        Vector3 poseform = shape.getPose().transformPoint(center);
        return pose.transformPoint(poseform);
    }

    public Matrix3 getInertialTensor() {
        Matrix3 shapeRotation = shape.getPose().getRotationMatrix();
        Matrix3 bodyRotation = pose.getRotationMatrix();
        Matrix3 rotation = bodyRotation.mul(shapeRotation);

        Matrix3 localTensor = shape.getInertialTensor();

        return rotation.mul(localTensor).mul(rotation.transpose());
    }

    public Bounds3 getBounds() {
        Bounds3 localBounds = shape.getBounds();
        Bounds3 globalBounds = new Bounds3();

        for (int Ix = 0; Ix < 2; Ix++) {
            for (int Iy = 0; Iy < 2; Iy++) {
                for (int Iz = 0; Iz < 2; Iz++) {
                    float x;
                    float y;
                    float z;

                    if (Ix == 0) {
                        x = localBounds.min().x;
                    } else {
                        x = localBounds.max().x;
                    }

                    if (Iy == 0) {
                        y = localBounds.min().y;
                    } else {
                        y = localBounds.max().y;
                    }

                    if (Iz == 0) {
                        z = localBounds.min().z;
                    } else {
                        z = localBounds.max().z;
                    }

                    Vector3 corner = new Vector3(x, y, z);
                    Vector3 shapePoint = shape.getPose().transformPoint(corner);
                    Vector3 globalPoint = pose.transformPoint(shapePoint);

                    globalBounds.expand(globalPoint);
                }
            }
        }
        return globalBounds;
    }
}