package lpoo.sym.parser;

/**
 *
 * @author Guilherme Duarte Balduino, Leonardo Marcon Leal, Otávio Gabriel de Oliveira
 */
enum TokenType
{
  EOI,
  ADD_OP,
  SUB_OP,
  MUL_OP,
  DIV_OP,
  POW_OP,
  ASSIGN_OP,
  LPAREN,
  RPAREN,
  FLOAT,
  COMMA,
  NAME,
  EVAL,
  DIFF,
  EXIT,
  DEFINE,
  CLEAR,
  LIST,
  ERROR;

  @Override
  public String toString()
  {
    return strings[ordinal()];
  }

  private static String[] strings =
  {
    "<eoi>",
    "+",
    "-",
    "*",
    "/",
    "^",
    "=",
    "(",
    ")",
    "float",
    ",",
    "name",
    "eval",
    "diff",
    "exit",
    "define",
    "clear",
    "list",
    "<error>"
  };

} // TokenType
