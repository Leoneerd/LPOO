package lpoo.sym;

import lpoo.util.Console;

/**
 *
 * @author Guilherme Duarte Balduino, Leonardo Marcon Leal, Otávio Gabriel de Oliveira
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

  public static final Command eval = new Command() {
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

  public static final Command diff = new Command() {
    @Override
    protected boolean run()
    {
      Expression expression = (Expression)arguments[0];
      Variable variable = (Variable)arguments[1];

      Console.println(expression.diff(variable));
      return true;
    }
  };

  public static final Command define = new Command() {
    @Override
    protected boolean run() {
      String name = (String)arguments[0];
      Expression expression = (Expression)arguments[1];

      SymbolTable.put(name, expression);

      if (expression == null) {
        Console.println("Defined " + name);
      } else {
        Console.println("Defined " + name + " = " + expression);
      }

      return true;
    }
  };

  public static final Command assignment = new Command() {
    @Override
    protected boolean run() {
      String name = (String)arguments[0];
      Expression expression = (Expression)arguments[1];

      SymbolTable.put(name, expression);
      Console.println(name + " = " + expression);

      return true;
    }
  };

  public static final Command clear = new Command() {
    @Override
    protected boolean run() {
      if (arguments.length == 0) {
        SymbolTable.clear();
        Console.println("Cleared all names");
        return true;
      }

      String name = (String)arguments[0];
      boolean existed = SymbolTable.contains(name);
      SymbolTable.remove(name);

      if (existed) {
        Console.println("Cleared " + name);
      } else {
        Console.println("No name " + name);
      }

      return true;
    }
  };

  public static final Command list = new Command() {
    @Override
    protected boolean run() {
      if (SymbolTable.names().isEmpty()) {
        Console.println("No names defined");
        return true;
      }

      for (String name : SymbolTable.names()) {
        Expression expression = SymbolTable.get(name);

        if (expression == null) {
          Console.println(name);
        } else {
          Console.println(name + " = " + expression);
        }
      }

      return true;
    }
  };

  public static final Command exit = new Command() {
    @Override
    protected boolean run()
    {
      Console.println("Exiting");
      return false;
    }
  };

} // Command
