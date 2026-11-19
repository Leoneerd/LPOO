package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa, Guilherme Duarte Balduino, Leonardo Marcon Leal, Otávio Gabriel de Oliveira
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
