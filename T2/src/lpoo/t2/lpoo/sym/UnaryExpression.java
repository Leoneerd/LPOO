package lpoo.sym;

/**
 *
 * @author Leonardo Marcon Leal
 */
public abstract class UnaryExpression
  extends Expression
{
  @Override
  public String toString()
  {
    String operand = e.isBinary() ? String.format("(%s)", e) : e.toString();
    return op + operand;
  }

  protected Expression e;
  protected char op;

  protected UnaryExpression(Expression e, char op)
  {
    this.e = e;
    this.op = op;
  }

} // UnaryExpression
