package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa
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
