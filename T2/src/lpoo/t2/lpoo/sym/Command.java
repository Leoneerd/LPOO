package lpoo.sym;

import lpoo.util.Console;

/**
 *
 * @author insert your name here
 */
public abstract class Command
{
  public void setArguments(Object... args)
  {
    arguments = args;
  }
  
  public final boolean execute()
  {
    boolean returnValue = run();
    
    arguments = null;
    return returnValue;
  }
  
  protected abstract boolean run();
  
  protected Object[] arguments;

  public static final Command eval = new Command()
  {
    @Override
    protected boolean run()
    {
      Expression expression = (Expression)arguments[0];
      float x = (Float)arguments[1];
      float y = (Float)arguments[2];
      float z = (Float)arguments[3];

      Console.println(expression.eval(x, y, z));
      return true;
    }
  };

  public static final Command diff = new Command()
  {
    @Override
    protected boolean run()
    {
      Expression expression = (Expression)arguments[0];
      char variable = (Character)arguments[1];

      Console.println(expression.diff(variable));
      return true;
    }
  };

  public static final Command exit = new Command()
  {
    @Override
    protected boolean run()
    {
      return false;
    }
  };

} // Command
