package lpoo.sym;

public final class Mul
  extends BinaryExpression
{
  public Mul(Expression e1, Expression e2)
  {
    super(e1, e2, '*');
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return e1.eval(x, y, z) * e2.eval(x, y, z);
  }

  @Override
  public Expression diff(char variable)
  {
    Expression t1 = (new Mul(e1.diff(variable), e2)).simplify();
    Expression t2 = (new Mul(e1, e2.diff(variable))).simplify();

    return (new Add(t1, t2)).simplify();
  }


  @Override
  public Expression substituteX(Expression replacement) {
    return (new Mul(e1.substituteX(replacement), e2.substituteX(replacement))).simplify();
  }

  @Override
  public Expression simplify()
  {
    Expression s1 = e1.simplify();
    Expression s2 = e2.simplify();

    if (s1.isConstant())
    {
      float v1 = ((Constant)s1).value;

      if (0 == v1)
        return Constant.ZERO;
      if (1 == v1)
        return s2;
      if (s2.isConstant())
        return new Constant(v1 * ((Constant)s2).value);
    }
    else if (s2.isConstant())
    {
      float v2 = ((Constant)s2).value;

      if (0 == v2)
        return Constant.ZERO;
      if (1 == v2)
        return s1;
    }
    return new Mul(s1, s2);
  }

} // Mul
