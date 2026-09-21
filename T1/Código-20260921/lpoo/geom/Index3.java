package lpoo.geom;

/**
 *
 * @author Paulo Pagliosa
 */
public final class Index3
{
  public final int i;
  public final int j;
  public final int k;

  public Index3(int i, int j, int k)
  {
    this.i = i;
    this.j = j;
    this.k = k;
  }

  @Override
  public String toString()
  {
    return String.format("(%d,%d,%d)", i, j, k);
  }

} // Index3
