package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa
 */
public abstract class Input
{
  // Gets the current character of this input.
  public final char current()
  {
    return buffer[current];
  }

  // Gets the next character of this input.
  public char advance()
  {
    if (current + 1 < buffer.length)
      ++current;
    return buffer[current];
  }

  // Sets the begin of the current lexeme.
  public void beginLexeme()
  {
    lexemeBegin = current;
  }

  // Gets the current lexeme.
  public String endLexeme()
  {
    if (lexemeBegin == -1)
      return null;

    String lexeme = new String(buffer, lexemeBegin, current - lexemeBegin);

    lexemeBegin = -1;
    return lexeme;
  }

  protected char[] buffer;

  protected int current = 0;
  protected int lexemeBegin = -1;

} // Input
