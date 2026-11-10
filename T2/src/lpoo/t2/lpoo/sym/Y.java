package lpoo.sym;

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
  public Expression diff(char variable)
  {
    if (variable == 'y') {
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
