package lpoo.geom;

import lpoo.math.*;

/**
 *
 * @author Paulo Pagliosa
 */
public final class TriangleMesh
{
  public int vertexCount()
  {
    return vertices.length;
  }

  public Vector3 vertex(int index)
  {
    return vertices[index];
  }

  public int triangleCount()
  {
    return triangles.length;
  }

  public Index3 triangle(int index)
  {
    return triangles[index];
  }

  @Override
  public String toString()
  {
    return String.format("TriangleMesh [vertices:%d triangles:%d]",
      vertexCount(),
      triangleCount());
  }
 
  public String dumpToString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("vertices\n");
    for (int i = 0; i < vertices.length; i++)
      sb.append(String.format("  %d %s\n", i, vertices[i]));
    sb.append("triangles\n");
    for (int i = 0; i < triangles.length; i++)
      sb.append(String.format("  %d %s\n", i, triangles[i]));
    return sb.toString();
  }

  TriangleMesh(Vector3[] vertices, Index3[] triangles)
  {
    this.vertices = vertices;
    this.triangles = triangles;
  }

  private final Vector3[] vertices;
  private final Index3[] triangles;

} // TriangleMesh
