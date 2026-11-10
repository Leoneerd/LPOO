package lpoo.sym;

public abstract class BinaryExpression
  extends Expression
{
  @Override
  public String toString()
  {
    String s1 = String.format(e1.isBinary() ? "(%s)" : "%s", e1);
    String s2 = String.format(e2.isBinary() ? "(%s)" : "%s", e2);

    return s1 + op + s2;
  }

  protected Expression e1;
  protected Expression e2;
  protected char op;

  protected BinaryExpression(Expression e1, Expression e2, char op)
  {
    this.e1 = e1;
    this.e2 = e2;
    this.op = op;
  }

} // BinaryExpression
