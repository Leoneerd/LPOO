package lpoo.math;

/**
 *
 * @author Paulo Pagliosa
 */
public final class Vector3
{
  public static final Vector3 NULL = new Vector3(0);

  public static Vector3 min(Vector3 a, Vector3 b)
  {
    return new Vector3(Math.min(a.x, b.x),
      Math.min(a.y, b.y),
      Math.min(a.z, b.z));
  }

  public static Vector3 max(Vector3 a, Vector3 b)
  {
    return new Vector3(Math.max(a.x, b.x),
      Math.max(a.y, b.y),
      Math.max(a.z, b.z));
  }

  public final float x;
  public final float y;
  public final float z;

  public Vector3(float x, float y, float z)
  {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3(float x)
  {
    this(x, x, x);
  }

  public Vector3 add(Vector3 v)
  {
    return new Vector3(x + v.x, y + v.y, z + v.z);
  }

  public Vector3 sub(Vector3 v)
  {
    return new Vector3(x - v.x, y - v.y, z - v.z);
  }

  public Vector3 mul(float s)
  {
    return new Vector3(x * s, y * s, z * s);
  }

  public float dot(Vector3 v)
  {
    return x * v.x + y * v.y + z * v.z;
  }

  public float normSquared()
  {
    return dot(this);
  }

  public float norm()
  {
    return (float)Math.sqrt(normSquared());
  }

  @Override
  public String toString()
  {
    return String.format("(%g,%g,%g)", x, y, z);
  }

} // Vector3
