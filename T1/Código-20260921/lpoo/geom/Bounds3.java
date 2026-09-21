package lpoo.geom;

import lpoo.math.*;

/**
 *
 * @author Paulo Pagliosa
 */
public final class Bounds3
{
  public Bounds3()
  {
    min = new Vector3(Float.POSITIVE_INFINITY);
    max = new Vector3(Float.NEGATIVE_INFINITY);
  }

  public Bounds3(Vector3 min, Vector3 max)
  {
    this.min = Vector3.min(min, max);
    this.max = Vector3.max(min, max);
  }

  public Vector3 min()
  {
    return min;
  }

  public Vector3 max()
  {
    return max;
  }

  public Bounds3 expand(Vector3 p)
  {
    this.min = Vector3.min(min, p);
    this.max = Vector3.min(max, p);
    return this;
  }

  public Bounds3 expand(final Bounds3 b)
  {
    this.min = Vector3.min(min, b.min);
    this.max = Vector3.min(max, b.max);
    return this;
  }

  public Bounds3 union(Bounds3 b)
  {
    return new Bounds3(Vector3.min(min, b.min), Vector3.max(max, b.max));
  }

  @Override
  public String toString()
  {
    return "min" + min + " max" + max;
  }

  private Vector3 min;
  private Vector3 max;

} // Bounds3
