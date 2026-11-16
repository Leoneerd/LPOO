package lpoo.sym;

/**
 *
 * @author Leonardo Marcon Leal
 */
public final class Y
  extends Expression
{

  public static final Y INSTANCE = new Y();

  private Y() {
  }
  
  @Override
  public float eval(float x, float y, float z)
  {
    return y;
  }

  @Override
  public Expression diff(Variable variable)
  {
    if (variable == Variable.Y) {
      return Constant.ONE;
    } else {
      return Constant.ZERO;
    }
  }

  @Override
  public String toString()
  {
    return "y";
  }

}
