package lpoo.sym;

public final class Minus
  extends UnaryExpression
{
  public Minus(Expression e)
  {
    super(e, '-');
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return -e.eval(x, y, z);
  }

  @Override
  public Expression diff(char variable)
  {
    return (new Minus(e.diff(variable))).simplify();
  }

  @Override
  public Expression substituteX(Expression replacement) {
    return (new Minus(e.substituteX(replacement))).simplify();
  }

  @Override
  public Expression simplify()
  {
    Expression s = e.simplify();

    if (s.isConstant())
    {
      float v = ((Constant)s).value;
      return 0 == v ? Constant.ZERO : new Constant(-v);
    }
    return s instanceof Minus ? s : new Minus(s);
  }

} // Minus
