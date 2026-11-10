package lpoo.sym;

import lpoo.sym.parser.*;
import lpoo.util.*;

/**
 *
 * @author Paulo Pagliosa
 */
public class Calc
{
  public static boolean execute(String s)
  {
    assert(s != null);
    try
    {
      parser.setInput(new InputBuffer(s.toCharArray()));
      return parser.parse().execute();
    }
    catch (ParseException e)
    {
      Console.println("Error: " + e.getMessage());
      return true;
    }
  }
  
  private static Parser parser = new Parser();

} // Calc
