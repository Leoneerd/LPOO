package lpoo.math;

/**
 *
 * @author Paulo Pagliosa
 */
public class Real
{
  public static final float EPS = 1e-6f;

  public static boolean isZero(float a)
  {
    return Math.abs(a) <= EPS;
  }

  public static boolean isEqual(float a, float b)
  {
    return isZero(a - b);
  }

  public Real(float value)
  {
    this.value = value;
  }

} // Real
