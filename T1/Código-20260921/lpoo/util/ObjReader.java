package lpoo.util;

import lpoo.geom.*;
import lpoo.math.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Paulo Pagliosa
 */
public final class ObjReader
	extends MeshBuilder
{
  public static TriangleMesh read(String filename)
    throws IOException
  {
    return read(new File(filename));
  }

  public static TriangleMesh read(File file)
    throws IOException
  {
    List<Vector3> vertices = new ArrayList<>();
    List<Index3> triangles = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file)))
    {
      for (String line; (line = reader.readLine()) != null; )
      {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#"))
          continue;

        String[] tokens = line.split("\\s+");

        switch (tokens[0])
        {
          case "v":
            float x = Float.parseFloat(tokens[1]);
            float y = Float.parseFloat(tokens[2]);
            float z = Float.parseFloat(tokens[3]);

            vertices.add(new Vector3(x, y, z));
            break;

          case "f":
            int[] fids = new int[tokens.length - 1];

            for (int i = 1; i < tokens.length; i++)
              fids[i - 1] = parseFaceIndex(tokens[i]); 
            for (int i = 1; i < fids.length - 1; i++)
              triangles.add(new Index3(fids[0], fids[i], fids[i + 1]));
            break;

          default:
            break;
        }
      }
    }
    return MeshBuilder.build(vertices.toArray(new Vector3[0]),
      triangles.toArray(new Index3[0]));
  }

  private static int parseFaceIndex(String token)
  {
    return Integer.parseInt(token.split("/")[0]) - 1;
  }

  private ObjReader()
  {
    // do nothing
  }

} // ObjReader
