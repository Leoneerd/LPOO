// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import lpoo.exception.ErrorException;
import lpoo.phyx.RigidBody;
import lpoo.util.SceneReader;
import lpoo.util.SceneReport;

//Lê uma cena e imprime o relatorio. Uso: java SceneReaderDemo cena.txt
public final class SceneReaderDemo 
{
    public static void main(String[] args) 
    {
        if (args.length < 1) 
        {
            System.err.println("Use: java SceneReaderDemo <arquivo_de_cena>");
            return;
        }

        File file = new File(args[0]);
        try 
        {
            List<RigidBody> bodies = SceneReader.read(file);
            System.out.println("Scene: " + SceneReader.sceneName(file));
            SceneReport.write(bodies, new PrintWriter(System.out, true));
        } 
        catch (java.io.FileNotFoundException | ErrorException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
