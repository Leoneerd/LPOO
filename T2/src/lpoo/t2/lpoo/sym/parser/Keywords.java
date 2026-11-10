package lpoo.sym.parser;

import java.util.TreeMap;

/**
 *
 * @author insert your name here
 */
final class Keywords
{
  static void add(String name, TokenType type)
  {
    map.put(name, new Token(type, name));
  }
  
  static Token get(String name)
  {
    return map.get(name);
  }
  
  static
  {
    map = new TreeMap<>();
    add("eval", TokenType.EVAL);
    add("diff", TokenType.DIFF);
    add("define", TokenType.DEFINE);
    add("clear", TokenType.CLEAR);
    add("list", TokenType.LIST);
    add("exit", TokenType.EXIT);
  }

  static TreeMap<String, Token> map;

} // Keywords
