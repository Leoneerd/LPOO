package lpoo.sym;

/**
 *
 * @author Leonardo Marcon Leal
 */
public final class Z
  extends Expression
{

  public static final Z INSTANCE = new Z();

  private Z() {
  }
  
  
  @Override
  public float eval(float x, float y, float z)
  {
    return z;
  }

  @Override
  public Expression diff(Variable variable)
  {
    if (variable == Variable.Z) {
      return Constant.ONE;
    } else {
      return Constant.ZERO;
    }
  }

  @Override
  public String toString()
  {
    return "z";
  }

}
