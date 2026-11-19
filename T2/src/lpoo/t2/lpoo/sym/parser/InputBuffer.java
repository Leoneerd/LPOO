package lpoo.sym.parser;

import java.util.Arrays;

/**
 *
 * @author Paulo Pagliosa, Guilherme Duarte Balduino, Leonardo Marcon Leal, Otávio Gabriel de Oliveira
 */
public final class InputBuffer extends Input
{
  public InputBuffer(char[] buffer)
  {
    assert(buffer != null);
    this.buffer = Arrays.copyOf(buffer, buffer.length + 1);
  }

} // InputBuffer
