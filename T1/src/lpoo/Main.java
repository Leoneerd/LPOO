// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import lpoo.exception.ErrorException;
import lpoo.phyx.RigidBody;
import lpoo.util.SceneReader;
import lpoo.util.SceneReport;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments");
            return;
        }

        File sceneFile = new File(args[0]);
        File outputFile = new File(SceneReader.sceneName(sceneFile) + ".txt");

        List<RigidBody> bodies;

        try {
            bodies = SceneReader.read(sceneFile);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return;
        } catch (ErrorException e) {
            System.err.println(e.getMessage());
            return;
        }

        try (PrintWriter output = new PrintWriter(new FileWriter(outputFile))) {
            SceneReport.write(bodies, output);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}