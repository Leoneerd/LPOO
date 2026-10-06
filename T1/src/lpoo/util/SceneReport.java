// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo.util;

import lpoo.phyx.RigidBody;
import lpoo.geom.Shape;
import lpoo.geom.CompoundShape;
import lpoo.geom.CompoundShapeInstance;
import lpoo.geom.Bounds3;
import lpoo.math.Matrix3;
import lpoo.math.Vector3;
import java.io.PrintWriter;
import java.util.List;

public final class SceneReport {
    private SceneReport() {
    }

    public static void write(List<RigidBody> bodies, PrintWriter out) {
        for (int i = 0; i < bodies.size(); i++) {
            RigidBody body = bodies.get(i);

            String name = body.getName();
            float area = body.getSurfaceArea();
            float volume = body.getVolume();
            float mass = body.getMass();
            Vector3 centerOfMass = body.getCenterOfMass();
            Matrix3 inertialTensor = body.getInertialTensor();
            Bounds3 bounds = body.getBounds();
            Shape shape = body.getShape();

            out.println("Body: " + name);
            out.println("Surface area: " + area);
            out.println("Volume: " + volume);
            out.println("Mass: " + mass);
            out.println("Center of mass: " + centerOfMass);
            out.println("Inertia tensor:");
            out.println(inertialTensor);
            out.println("Bounds: " + bounds);

            writeShape(shape, out, " ");
            out.println();
        }
            
        out.flush();
    }

    private static void writeShape(Shape shape, PrintWriter out, String indent) {
        out.println(indent + "Shape type: " + shape.getClass().getSimpleName());
        out.println(indent + "Name: " + shape.getName());
        out.println(indent + "Surface area: " + shape.getSurfaceArea());
        out.println(indent + "Volume: " + shape.getVolume());
        out.println(indent + "Mass: " + shape.getMass());
        out.println(indent + "Center of mass: " + shape.getCenterOfMass());
        out.println(indent + "Inertia tensor:");
        out.println(shape.getInertialTensor());
        out.println(indent + "Bounds: " + shape.getBounds());

        List<Shape> children = null;

        if (shape instanceof CompoundShape) {
            children = ((CompoundShape) shape).getChildren();
        } else if (shape instanceof CompoundShapeInstance) {
            children = ((CompoundShapeInstance) shape).getChildren();
        }

        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                Shape child = children.get(i);
                writeShape(child, out, indent + " ");
            }
        }
    }


}

