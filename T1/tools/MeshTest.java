import lpoo.geom.*;
import lpoo.math.*;
import lpoo.util.*;
import java.io.*;

/**
 *
 * @author Paulo Pagliosa
 */
public final class MeshTest
{
  public static void main(String[] args)
    throws IOException
  {
    if (args.length < 1)
    {
      System.err.println("Use: java MeshTest <obj_filename> [out_filename]");
        return;
    }
    System.out.printf("Reading OBJ file '%s'\n", args[0]);
  
    TriangleMesh mesh = ObjReader.read(args[0]);
  
    System.out.println(mesh);

    PrintWriter out;
    
    if (args.length < 2)
      out = new PrintWriter(System.out);
    else
    {
      System.out.printf("Writing file '%s'\n", args[1]);
      out = new PrintWriter(new File(args[1]));
    }
    out.print(mesh.dumpToString());
    out.flush();
  }

} // MeshTest
