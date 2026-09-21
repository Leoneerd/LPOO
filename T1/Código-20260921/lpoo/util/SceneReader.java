package lpoo.util;

import lpoo.geom.*;
import lpoo.math.*;
import lpoo.phyx.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author insert your name here
 */
public final class SceneParser
{
  public static List<RigidBody> read(File file)
    throws FileNotFoundException
  {
    try (Scanner sc = new Scanner(file))
    {
      return new SceneReader(sc).readFile();
    }
  }

  private final Scanner sc;

  private SceneParser(Scanner sc)
  {
    this.sc = sc;
  }

  private List<RigidBody> readFile()
  {
    // TODO: insert your here
    return null;
  }

  private Vector3 readVector3()
  {
    float x = sc.nextFloat();
    float y = sc.nextFloat();
    float z = sc.nextFloat();

    return new Vector3(x, y, z);
  }

  private Quaternion readQuaternion()
  {
    float x = sc.nextFloat();
    float y = sc.nextFloat();
    float z = sc.nextFloat();
    float w = sc.nextFloat();

    return new Quaternion(x, y, z, w);
  }

} // SceneReader
