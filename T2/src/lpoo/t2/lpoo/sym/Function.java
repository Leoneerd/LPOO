package lpoo.sym;

public class Function
  extends Expression
{
  public static final Function sin(Expression arg)
  {
    return new Function(NativeFunction.SIN, arg);
  }

  public static final Function cos(Expression arg)
  {
    return new Function(NativeFunction.COS, arg);
  }

  public static final Function loge(Expression arg) {
    return new Function(NativeFunction.LOGE, arg);
  }

  @Override
  public float eval(float x, float y, float z)
  {
    float value = arg.eval(x, y, z);
    return function.eval(value, 0, 0);
  }

  @Override
  public Expression diff(char variable)
  {
    Expression f = new Function(function.diff('x'), arg); 
    return (new Mul(f, arg.diff(variable))).simplify();
  }

  @Override
  public Expression substituteX(Expression replacement) {
    return new lpoo.sym.Function(function, arg.substituteX(replacement));
  }

  @Override
  public String toString()
  {
    return String.format("%s(%s)", function, arg);
  }

  private final Expression function;
  private final Expression arg;

  private Function(Expression function, Expression arg)
  {
    this.function = function;
    this.arg = arg;
  }

} // Function

abstract class NativeFunction
  extends Expression 
{
  static final NativeFunction SIN = new Sin();
  static final NativeFunction COS = new Cos();
  static final NativeFunction LOGE = new Loge();
} // NativeFunction

final class Sin
  extends NativeFunction
{
  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.sin(x);
  }

  @Override
  public Expression diff(char variable)
  {
    return COS;
  }

  @Override
  public String toString()
  {
    return "sin";
  }

} // Sin


final class Cos
  extends NativeFunction
{
  @Override
  public float eval(float x, float y, float z)
  {
    return (float)Math.cos(x);
  }

  @Override
  public Expression diff(char variable)
  {
    return new Minus(SIN);
  }

  @Override
  public String toString()
  {
    return "cos";
  }

} // Cos

final class Loge extends NativeFunction {
  @Override
  public float eval(float x, float y, float z) {
    return (float)Math.log(x);
  }

  @Override 
  public Expression diff(char variable) {
    return new Div(Constant.ONE, X.INSTANCE);
  }

  @Override
  public String toString() {
    return "loge";
  }
}