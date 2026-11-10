package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa
 */
public class Scanner
{
  public void setInput(Input input)
  {
    assert(input != null);
    this.input = input;
    currentLine = 1;
  }

  public final int currentLine()
  {
    return currentLine;
  }

  public Token nextToken()
    throws InvalidInputException
  {
    char c = input.current();

    while (Character.isWhitespace(c))
    {
      if (c == '\n')
        ++currentLine;
      c = input.advance();
    }
    if (c == '\0')
      return new Token(TokenType.EOI);
    input.beginLexeme();
    if (Character.isLetter(c) || c == '_')
    {
      do
        c = input.advance();
      while (Character.isLetterOrDigit(c) || c == '_');

      String name = input.endLexeme();
      Token token = Keywords.get(name);

      if (token == null)
        token = new Token.Name(name);
      token.line = currentLine;
      return token;
    }
    if (Character.isDigit(c))
    {
      do
        c = input.advance();
      while (Character.isDigit(c));
      
      Token token;

      if (c == '.')
        token = scanFloat();
      else if (Character.toUpperCase(c) == 'E')
        token = scanExp();
      else
        token = new Token.Float(input.endLexeme());
      token.line = currentLine;
      return token;
    }
    if (c == '.')
    {
      if (!Character.isDigit(input.advance()))
        inputError(UNEXPECTED_CHAR, '.');
      do
        c = input.advance();
      while (Character.isDigit(c));

      Token token = Character.toUpperCase(c) != 'E' ?
        new Token.Float(input.endLexeme()) : scanExp();

      token.line = currentLine;
      return token;
    }
    input.advance();

    TokenType type = TokenType.ERROR;

    switch (c)
    {
      case '+':
        type = TokenType.ADD_OP;
        break;
      case '-':
        type = TokenType.SUB_OP;
        break;
      case '*':
        type = TokenType.MUL_OP;
        break;
      case '/':
        type = TokenType.DIV_OP;
        break;
      case '^':
        type = TokenType.POW_OP;
        break;
      case '(':
        type = TokenType.LPAREN;
        break;
      case ')':
        type = TokenType.RPAREN;
        break;
      case '=':
        type = TokenType.ASSIGN_OP;
        break;
      case ',':
        type = TokenType.COMMA;
        break;
      default:
        inputError(UNEXPECTED_CHAR, c);
        break;
    }
    return new Token(type, input.endLexeme());
  }

  private static final int UNEXPECTED_CHAR = 0;
  private static final int INCORRECT_NUMBER_FORMAT = 1;

  private static final String[] inputErrorMessages =
  {
    "Unexpected '%1c'",
    "Incorrect number format"
  };

  protected void inputError(int code, Object... args)
    throws InvalidInputException
  {
    throw new InvalidInputException(inputErrorMessages[code], args);
  }

  private Token scanFloat()
    throws InvalidInputException
  {
    while (Character.isDigit(input.advance()))
      ;
    return Character.toUpperCase(input.current()) != 'E' ?
      new Token.Float(input.endLexeme()) :
      scanExp();
  }

  private Token scanExp()
    throws InvalidInputException
  {
    int c = input.advance();

    if (c == '+' || c == '-')
      input.advance();
    if (!Character.isDigit(input.current()))
    {
      while (Character.isLetterOrDigit(input.current()))
          input.advance();
      inputError(INCORRECT_NUMBER_FORMAT);
    }
    while (Character.isDigit(input.advance()))
      ;
    return new Token.Float(input.endLexeme());
  }

  private Input input;
  private int currentLine;

} // Scanner
