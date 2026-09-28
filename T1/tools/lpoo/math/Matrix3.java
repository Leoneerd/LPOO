package lpoo.math;

/**
 *
 * @author Paulo Pagliosa
 */
public final class Matrix3
{
  public static Matrix3 zero()
  {
    return new Matrix3(new float[3][3]);
  }
  
  public static Matrix3 identity()
  {
    return diagonal(1);
  }
  
  public static Matrix3 diagonal(float a, float b, float c)
  {
    float[][] d = new float[3][3];

    d[0][0] = a;
    d[1][1] = b;
    d[2][2] = c;
    return new Matrix3(d);
  }
  
  public static Matrix3 diagonal(float a)
  {
    return diagonal(a, a, a);
  }

  public static Matrix3 outer(Vector3 v, float s)
  {
    float[] a = {v.x, v.y, v.z};
    float[][] r = new float[3][3];

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        r[i][j] = s * a[i] * a[j];
    return new Matrix3(r);
  }
  
  public float get(int row, int col)
  {
    return data[row][col];
  }
  
  public Matrix3 add(Matrix3 m)
  {
    float[][] r = new float[3][3];

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        r[i][j] = data[i][j] + m.data[i][j];
    return new Matrix3(r);
  }
  
  public Matrix3 mul(float s)
  {
    float[][] r = new float[3][3];

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        r[i][j] = data[i][j] * s;
    return new Matrix3(r);
  }

  public Matrix3 mul(Matrix3 m)
  {
    float[][] r = new float[3][3];

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
      {
        float s = 0;

        for (int k = 0; k < 3; k++)
          s += data[i][k] * m.data[k][j];
        r[i][j] = s;
      }
    return new Matrix3(r);
  }

  public Matrix3 transpose()
  {
    float[][] r = new float[3][3];

    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        r[i][j] = data[j][i];
    return new Matrix3(r);
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 3; i++)
    {
      sb.append("[ ");
      for (int j = 0; j < 3; j++)
        sb.append(String.format("%12.6g ", data[i][j]));
      sb.append("]\n");
    }
    return sb.toString();
  }

  Matrix3(float[][] m)
  {
    data = m;
  } 

  private final float[][] data;

} // Matrix3
