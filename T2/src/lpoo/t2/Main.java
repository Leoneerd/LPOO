import lpoo.sym.*;
import lpoo.util.*;

/**
 *
 * @author insert your code here
 */
public class Main
{
  public static void main(String[] args)
  {
    for (;;)
    {
      String line = Console.readString("> ");

      if (!line.equals("") && !Calc.execute(line))
        return;
    }
  }

} // Main
