package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa
 */
public class Token
{
  TokenType type;
  String lexeme;
  int line;

  Token(TokenType type)
  {
    this.type = type;
  }

  Token(TokenType type, String lexeme)
  {
    this(type);
    this.lexeme = lexeme;
  }

  @Override
  public String toString()
  {
    return lexeme;
  }

  static class Name extends Token
  {
    Name(String lexeme)
    {
      super(TokenType.NAME, lexeme);
    }

    final String value()
    {
      return lexeme;
    }

  } // Name

  static class Float extends Token
  {
    Float(String lexeme)
    {
      super(TokenType.FLOAT, lexeme);
      try
      {
        value = java.lang.Float.valueOf(lexeme);
      }
      finally
      {
        // do nothing
      }
    }

    final float value()
    {
      return value;
    }
  
    private float value;

  } // Float

} // Token
