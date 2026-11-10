package lpoo.sym;

public final class Pow
  extends BinaryExpression
{
  public Pow(Expression base, Expression exp)
  {
    super(base, exp, '^');
  }

  public Pow(Expression base, float exp)
  {
    this(base, new Constant(exp));
  }

  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.pow(e1.eval(x, y, z), e2.eval(x, y, z));
  }

  @Override
  public Expression diff(char variable)
  {
    Expression exponent = e2.simplify();

    if (exponent.isConstant()) {
      float n = ((Constant)exponent).value;

      if (n == 0) {
        return Constant.ZERO;
      } else if (n == 1) {
        return e1.diff(variable);
      }

      return (new Mul(
        new Mul(new Constant(n), new Pow(e1, n - 1)),
        e1.diff(variable)
      )).simplify();
    }
  
  Expression t1 = (new Mul(e2.diff(variable), Function.loge(e1))).simplify();
  Expression t2 = (new Mul(e2, new Div(e1.diff(variable), e1))).simplify();

  return (new Mul(
    new Pow(e1, e2),
    new Add(t1, t2)
  )).simplify();
  }

  @Override
  public Expression substituteX(Expression replacement) {
    return (new Pow(e1.substituteX(replacement), e2.substituteX(replacement))).simplify();
  }

  @Override
  public Expression simplify()
  {
    Expression base = e1.simplify();
    Expression exponent = e2.simplify();

    if (exponent.isConstant()) {
      float n = ((Constant)exponent).value;

      if (n == 0) {
        return Constant.ONE;
      } else if (n == 1) {
        return base;
      }

      if (base.isConstant()) {
        return new Constant((float)Math.pow(((Constant)base).value, n));
      }

    }
    return new Pow(base, exponent);
  }

} // Pow
