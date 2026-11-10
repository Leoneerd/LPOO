package lpoo.sym;

public final class X
  extends Expression
{

  public static final X INSTANCE = new X();

  private X() {
  }

  @Override
  public Expression substituteX(Expression replacement) {
    return replacement;
  }
  
  @Override
  public float eval(float x, float y, float z)
  {
    return x;
  }

  @Override
  public Expression diff(char variable)
  {
    if (variable == 'x') {
      return Constant.ONE;
    } else {
      return Constant.ZERO;
    }
  }

  @Override
  public String toString()
  {
    return "x";
  }

} // X
