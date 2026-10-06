// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo.geom;


import lpoo.math.Quaternion;
import lpoo.math.Matrix3;
import lpoo.math.Vector3;

public final class Pose {
    private final Vector3 position;
    private final Quaternion orientation;

    public Pose(Vector3 position, Quaternion orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public Matrix3 getRotationMatrix() {
        return orientation.toRotationMatrix();
    }

    public Vector3 transformPoint(Vector3 point) {
        Matrix3 rotation = getRotationMatrix();

        float x = rotation.get(0,0) * point.x + rotation.get(0,1) * point.y + rotation.get(0,2) * point.z + position.x;
        float y = rotation.get(1,0) * point.x + rotation.get(1,1) * point.y + rotation.get(1,2) * point.z + position.y;
        float z = rotation.get(2,0) * point.x + rotation.get(2,1) * point.y + rotation.get(2,2) * point.z + position.z;

        return new Vector3(x,y,z);
    }

    public Vector3 transformVector(Vector3 vector) {
        Matrix3 rotation = getRotationMatrix();

        float x = rotation.get(0,0) * vector.x + rotation.get(0,1) * vector.y + rotation.get(0,2) * vector.z;
        float y = rotation.get(1,0) * vector.x + rotation.get(1,1) * vector.y + rotation.get(1,2) * vector.z;
        float z = rotation.get(2,0) * vector.x + rotation.get(2,1) * vector.y + rotation.get(2,2) * vector.z;

        return new Vector3(x,y,z);
    }
}