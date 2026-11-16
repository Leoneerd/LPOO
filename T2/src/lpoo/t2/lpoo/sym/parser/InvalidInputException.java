package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa, Leonardo Marcon Leal
 */
class InvalidInputException extends Exception
{
  InvalidInputException(String s)
  {
    super(s);
  }

  InvalidInputException(String fmt, Object... args)
  {
    super(String.format(fmt, args));
  }

} // InvalidInputException
