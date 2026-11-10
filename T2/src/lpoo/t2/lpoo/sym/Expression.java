package lpoo.sym;

public abstract class Expression
{
  public abstract float eval(float x, float y, float z);
  public abstract Expression diff(char variable);

  public Expression substituteX(Expression replacement) {
    return this;
  }

  public Expression simplify()
  {
    return this;
  }

  public final boolean isConstant()
  {
    return this instanceof Constant;
  }

  public final boolean isBinary()
  {
    return this instanceof BinaryExpression;
  }

} // Expression