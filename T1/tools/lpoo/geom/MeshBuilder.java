package lpoo.geom;

import lpoo.math.*;

/**
 *
 * @author Paulo Pagliosa
 */
public abstract class MeshBuilder
{  
  protected static TriangleMesh build(Vector3[] vertices, Index3[] triangles)
  {
    return new TriangleMesh(vertices, triangles);
  }

} // MeshBuilder
