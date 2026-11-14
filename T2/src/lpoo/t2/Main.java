import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import lpoo.sym.*;
import lpoo.util.*;

/**
 *
 * @author insert your code here
 */
public class Main
{
  public static void main(String[] args) {
    if (args.length == 0) {
      for (;;) {
        String line = Console.readString("> ");

        if (!line.equals("") && !Calc.execute(line)) {
          return;
        }
      }
    }

    for (String fileName : args) {
      if (!runFile(fileName)) {
        return;
      }
    }
  }

  private static boolean runFile(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line;

      while ((line = reader.readLine()) != null) {
        if (!line.trim().isEmpty() &&  !Calc.execute(line)) {
          return false;
        }
      }
    }
      catch(IOException e) {
        Console.println("Error reading " + fileName + ": " + e.getMessage());
      }

      return true;
    }
} // Main
