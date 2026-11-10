package lpoo.sym;

public final class Constant
  extends Expression
{
  public final static Constant ZERO = new Constant(0);
  public final static Constant ONE = new Constant(1);
  public final static Constant PI = new Constant((float)Math.PI);
  public final static Constant E = new Constant((float)Math.E);
 
  public final float value;

  public Constant(float value)
  {
    this.value = value;
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return value;
  }

  @Override
  public Expression diff(char variable)
  {
    return ZERO;
  }

  @Override
  public String toString()
  {
    if (value == PI.value) {
      return "pi";
    }
    if (value == E.value) {
      return "e";
    }

    return Float.toString(value);
  }

} // Constant
