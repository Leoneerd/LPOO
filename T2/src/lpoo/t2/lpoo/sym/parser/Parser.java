package lpoo.sym.parser;

import lpoo.sym.*;

/**
 *
 * @author insert your name here
 */
public class Parser
{
  public Parser()
  {
    scanner = new Scanner();
  }

  public void setInput(Input input)
    throws ParseException
  {
    scanner.setInput(input);
    advance();
  }

  public Command parse()
    throws ParseException
  {
    Command c = null;

    if (token.type == TokenType.EVAL) {
      advance(); // EVAL
      match(TokenType.LPAREN);
      Expression e = expression();
      match(TokenType.COMMA);
      float[] values = args();
      match(TokenType.RPAREN);
      c = Command.eval;
      c.setArguments(e, values[0], values[1], values[2]);
    } else if (token.type == TokenType.DIFF) {
      advance();
      match(TokenType.LPAREN);
      Expression e = expression();
      match(TokenType.COMMA);

      if (token.type != TokenType.NAME) {
        error(IDENTIFIER_EXPECTED);
      }

      String name = ((Token.Name)token).value();

      if (!name.equals("x") && !name.equals("y") && !name.equals("z")) {
        error("Variable must be x, y, z");
      }

      advance();
      match(TokenType.RPAREN);

      c = Command.diff;
      c.setArguments(e, name.charAt(0));
    }

    else if (token.type == TokenType.EXIT)
    {
      advance(); // EXIT
      c = Command.exit;
    }
    if (token.type != TokenType.EOI)
      error(SYNTAX);
    return c;
  }

  private float[] args()
    throws ParseException
  {
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float x = ((Token.Float)token).value();

    advance();
    match(TokenType.COMMA);
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float y = ((Token.Float)token).value();

    advance();
    match(TokenType.COMMA);
    if (token.type != TokenType.FLOAT)
      error(NUMBER_EXPECTED);

    float z = ((Token.Float)token).value();

    advance();

    return new float[] {x, y, z};
    // insert your code here
  }

  private Expression expression() throws ParseException {
    Expression e = mul();

    while (token.type == TokenType.ADD_OP || token.type == TokenType.SUB_OP) {
      TokenType op = token.type;
      advance();
      Expression right = mul();

      if (op == TokenType.ADD_OP) {
        e = new Add(e, right);
      } else {
        e = new Sub(e, right);
      }
    }

    return e;
  }

  private Expression mul() throws ParseException {
    Expression e = pow();

    while (token.type == TokenType.MUL_OP || token.type == TokenType.DIV_OP) {
      TokenType op = token.type;
      advance();
      Expression right = pow();

      if (op == TokenType.MUL_OP) {
        e = new Mul(e, right);
      } else {
        e = new Div(e, right);
      }
    }
    
    return e;
  }

  private Expression pow() throws ParseException {
    boolean negative = false;

    if (token.type == TokenType.ADD_OP) {
      advance();
    } else if (token.type == TokenType.SUB_OP) {
      negative = true;
      advance();
    }

    Expression base = primary();

    if (negative) {
      base = new Minus(base);
    }

    if (token.type == TokenType.POW_OP) {
      advance();
      base = new Pow(base, pow());
    }

    return base;
  }

  private Expression primary() throws ParseException {
    if (token.type == TokenType.FLOAT) {
      float value = ((Token.Float)token).value();
      advance();
      return new Constant(value);
    }

    if (token.type == TokenType.LPAREN) {
      match(TokenType.LPAREN);
      Expression e = expression();
      match(TokenType.RPAREN);
      return e;
    }

    if (token.type == TokenType.NAME) {
      String name = ((Token.Name)token).value();
      advance();

      if (name.equals("x")) {
        return X.INSTANCE;
      }
      if (name.equals("y")) {
        return Y.INSTANCE;
      }
      if (name.equals("z")) {
        return Z.INSTANCE;
      }
      if (name.equals("pi")) {
        return Constant.PI;
      }
      if (name.equals("e")) {
        return Constant.E;
      }

      if (token.type == TokenType.LPAREN) {
        match(TokenType.LPAREN);
        Expression arg = expression();
        match(TokenType.RPAREN);

        if (name.equals("sin")) {
          return Function.sin(arg);
        }
        if (name.equals("cos")) {
          return Function.cos(arg);
        }
        if (name.equals("loge")) {
          return Function.loge(arg);
        }

        if (SymbolTable.contains(name)) {
          Expression definition = SymbolTable.get(name);

          if (definition == null) {
            error("Name '%s' has no expression", name);
            return null;
          }

          return definition.substituteX(arg).simplify();
        }

        error("Unknown function '%s'", name);
        return null;
      }

      if (SymbolTable.contains(name)) {
        Expression definition = SymbolTable.get(name);

        if (definition == null) {
          error("Name '%s' has no expression", name);
          return null;
        }

        return definition;
      }

      error("Unknown identifier '%s'", name);
      return null;
    }

    error(SYNTAX);
    return null;
  }

  private void error(int code, Object... args)
    throws ParseException
  {
    throw new ParseException(parseErrorMessages[code], args);
  }

  private void error(String s, Object... args)
    throws ParseException
  {
    throw new ParseException(s, args);
  }

  private Token advance()
    throws ParseException
  {
    try
    {
      token = scanner.nextToken();
    }
    catch (InvalidInputException e)
    {
      error(e.getMessage());
    }
    return token;
  }

  private void match(TokenType t)
    throws ParseException
  {
    if (token.type != t)
    {
      if (token.type == TokenType.EOI)
        error(EXPECTED, t.toString());
      error(UNEXPECTED, token.lexeme);
    }
    advance();
  }

  private Scanner scanner;
  private Token token;

  static final int SYNTAX = 0;
  static final int IDENTIFIER_EXPECTED = 1;
  static final int NUMBER_EXPECTED = 2;
  static final int EXPECTED = 3;
  static final int UNEXPECTED = 4;

  private static final String[] parseErrorMessages =
  {
    "Syntax error",
    "Identifier expected",
    "Number expected",
    "'%1$s' expected",
    "Unexpected '%1$s'"
  };

} // Parser
