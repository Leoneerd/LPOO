import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lpoo.geom.Box;
import lpoo.geom.Capsule;
import lpoo.geom.CompoundShape;
import lpoo.geom.Cylinder;
import lpoo.geom.Pose;
import lpoo.geom.Shape;
import lpoo.geom.Sphere;
import lpoo.math.Quaternion;
import lpoo.math.Vector3;
import lpoo.phyx.RigidBody;
import lpoo.util.SceneReport;

/** Builds a sample scene and prints its SceneReport to the terminal. */
public final class SceneReportDemo {
    private SceneReportDemo() {
    }

    public static void main(String[] args) {
        List<RigidBody> bodies = new ArrayList<RigidBody>();
        Pose identity = new Pose(Vector3.NULL, Quaternion.IDENTITY);

        Box box = new Box("box-shape", 1.0f, 1.0f, 2.0f, 3.0f);
        bodies.add(new RigidBody("box-body", box,
                new Pose(new Vector3(10.0f, 0.0f, 0.0f),
                        Quaternion.IDENTITY)));

        Sphere sphere = new Sphere("sphere-shape", 1.0f, 1.0f);
        bodies.add(new RigidBody("sphere-body", sphere, identity));

        Cylinder cylinder = new Cylinder("cylinder-shape", 1.0f,
                1.0f, 1.0f);
        bodies.add(new RigidBody("cylinder-body", cylinder, identity));

        Capsule capsule = new Capsule("capsule-shape", 1.0f,
                1.0f, 1.0f);
        bodies.add(new RigidBody("capsule-body", capsule, identity));

        Pose leftPose = new Pose(new Vector3(-3.0f, 0.0f, 0.0f),
                Quaternion.IDENTITY);
        Pose rightPose = new Pose(new Vector3(3.0f, 0.0f, 0.0f),
                Quaternion.IDENTITY);
        Box leftBox = new Box("left-child", 1.0f, leftPose,
                1.0f, 1.0f, 1.0f);
        Box rightBox = new Box("right-child", 1.0f, rightPose,
                1.0f, 1.0f, 1.0f);
        CompoundShape compound = new CompoundShape("compound-shape", identity,
                Arrays.<Shape>asList(leftBox, rightBox));
        bodies.add(new RigidBody("compound-body", compound,
                new Pose(new Vector3(0.0f, 5.0f, 0.0f),
                        Quaternion.IDENTITY)));

        SceneReport.write(bodies, new PrintWriter(System.out, true));
    }
}
