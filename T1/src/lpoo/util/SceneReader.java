// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal

package lpoo.util;

import lpoo.exception.BadDimensionsException;
import lpoo.exception.ErrorException;
import lpoo.exception.SceneFormatException;
import lpoo.geom.Box;
import lpoo.geom.Capsule;
import lpoo.geom.CompoundShape;
import lpoo.geom.CompoundShapeInstance;
import lpoo.geom.Cylinder;
import lpoo.geom.MeshShape;
import lpoo.geom.Pose;
import lpoo.geom.Shape;
import lpoo.geom.Sphere;
import lpoo.geom.TriangleMesh;
import lpoo.math.Quaternion;
import lpoo.math.Real;
import lpoo.math.Vector3;
import lpoo.phyx.RigidBody;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Permite que qualquer um use e que não seja herdada por ninguém
public final class SceneReader
{

    /** Nome da cena: nome do arquivo sem a extensao. */
    public static String sceneName(File file)
    {
        String name = file.getName();
        int dot = name.lastIndexOf('.');
        return dot > 0 ? name.substring(0, dot) : name;
    }

    //Lê o arquivo de cena e devolve os atores (corpos rigidos) na ordem do arquivo.
    public static List<RigidBody> read(File file)
            throws FileNotFoundException, ErrorException{
        if (!file.isFile())
            throw new FileNotFoundException("arquivo nao encontrado: " + file);
        SceneReader reader = new SceneReader(file);
        try {
            reader.tokenize();
        } catch (IOException e) {
            throw new SceneFormatException(file + ": erro de leitura: " + e.getMessage());
        }
        return reader.readFile();
    }

    //O arquivo a ser lido
    private final File file;

    //Todas as "palavras" do arquivo
    private final List<String> tokens = new ArrayList<>();

    //o numero da linha de CADA token
    private final List<Integer> lines = new ArrayList<>();

    //...
    private final Map<String, CompoundShape> definitions = new HashMap<>();

    //O indice do proximo token a ser lido, iniciando com 0
    private int next;

    //Construtor privado
    private SceneReader(File file)
    {
        this.file = file;
    }




    private List<RigidBody> readFile() throws ErrorException
    {
        //Cria  a lista de resultado, e se repete enquanto houver tokens.
        List<RigidBody> actors = new ArrayList<>();
        while (hasNext()) {
            String keyword = take();


            //Se for define
            if (keyword.equals("define"))
                {
                //Se ja existe ator na lista
                if (!actors.isEmpty())
                    //Error
                    throw error("'define' deve vir antes de todos os atores");

                //Senão le a definição
                readDefinition();
                } 
            
            //Se for ator
            else if (keyword.equals("actor"))
                //Le e acrescenta ele na lista
                actors.add(readActor());
                
            //Senão error (qualquer outra palavra)
            else
                throw error("esperado 'define' ou 'actor', encontrado '" + keyword + "'");
        }

        //Arquivo sem ator nenhum
        if (actors.isEmpty())
            throw error("a cena nao define nenhum ator");
        return actors;
    }

    private void readDefinition() throws ErrorException
    {

        //Após 'define' espera a palavra "composite"
        expect("composite");

        //Lê o nome
        String name = takeName();

        //Não permite nomes repetidos
        if (definitions.containsKey(name))
            throw error("forma composta '" + name + "' ja foi definida");

        //Apos o nome espera "{"
        expect("{");

        //Monta CompundShape com pose identidade e guarda no mapa. Onde o Instance procura depois
        definitions.put(name, new CompoundShape(name, identity(), readChildren(name)));
    }


    private RigidBody readActor() throws ErrorException
    {
        //Lê o nome e a posi
        String name = takeName();
        Pose pose = readPose();

        //E espera um "{"
        expect("{");

        //Lâ a forma
        Shape shape = readShape();

        //Se o proximo token não for "}" ocorre um erro
        if (!peek().equals("}"))
            throw error("ator '" + name + "' deve ter exatamente uma forma");

        // "}"
        take();

        //Cria o novo objeto RigidBody com name, shape, pose
        return new RigidBody(name, shape, pose);
    }


    private List<Shape> readChildren(String owner) throws ErrorException
    {
        List<Shape> children = new ArrayList<>();

        //enquanto o proximo token não for "}", se o arquivo acabar antes, peek lança "fim de arquivo inesperado"  
        while (!peek().equals("}"))
            //Lê uma forma e adiciona ela
            children.add(readShape());
        //Consome "{"
        take();

        //Rejeita lista vazia
        if (children.isEmpty())
            throw error("forma composta '" + owner + "' sem formas filhas");

        //Retorna o filho
        return children;
    }



    private Shape readShape() throws ErrorException
    {

        //Lê o tipo e o nome
        String type = take();
        String name = takeName();

        //Try serve para capturar erros dos construtores
        try {
            switch (type) {

                /*Lê os números, depois a pose opcional (readPose()), e chama o construtor da forma

                MESH: Lê a densidade e o caminho do .obj, depois a pose. readMesh carrega a malha
                e o MeshShape a recebe.

                Composite: a pose é lida antes do "{", porque o construtor CompoundShape pede pose e
                lista de filhos juntos.
                
                Instance:Lê o nome da definição e a procura no mapa. Se não existir guarda uma referência
                 à definição, mais a pose própria dela.
                */
                
                case "box":
                {
                    float d = positive("densidade");
                    float sx = positive("sx"), sy = positive("sy"), sz = positive("sz");
                    return new Box(name, d, readPose(), sx, sy, sz);
                }
                case "sphere":
                {
                    float d = positive("densidade");
                    float r = positive("raio");
                    return new Sphere(name, d, readPose(), r);
                }
                case "cylinder": 
                {
                    float d = positive("densidade");
                    float r = positive("raio"), s = positive("s");
                    return new Cylinder(name, d, readPose(), r, s);
                }
                case "capsule": {
                    float d = positive("densidade");
                    float r = positive("raio"), s = positive("s");
                    return new Capsule(name, d, readPose(), r, s);
                }
                case "mesh": 
                {
                    float d = positive("densidade");
                    String path = takeName();
                    Pose pose = readPose();
                    return new MeshShape(name, d, pose, readMesh(path));
                }
                case "composite": 
                {
                    Pose pose = readPose();
                    expect("{");
                    return new CompoundShape(name, pose, readChildren(name));
                }
                case "instance": 
                {
                    String defName = takeName();
                    CompoundShape def = definitions.get(defName);

                    if (def == null)
                        throw error("forma composta '" + defName + "' nao definida");
                    return new CompoundShapeInstance(name, def, readPose());
                }

                //Qualquer outro tipo é erro
                default:
                    throw error("tipo de forma desconhecido: '" + type + "'");
            }
        } 
        
        catch (BadDimensionsException | IllegalArgumentException e) {
            throw error(e.getMessage());
        }
    }


    //estudar mais profundamente
    private TriangleMesh readMesh(String path) throws ErrorException 
    {
        File obj = new File(path);

        if (!obj.isAbsolute() && file.getAbsoluteFile().getParentFile() != null)
            obj = new File(file.getAbsoluteFile().getParentFile(), path);
        try 
        {
            return ObjReader.read(obj);
        } 

        catch (IOException | RuntimeException e) 
        {
            throw error("nao foi possivel ler a malha '" + path + "': " + e.getMessage());
        }
    }

    //pose "neutra": retorna posição zero, sem rotação
    private static Pose identity() 
    {
        return new Pose(Vector3.NULL, Quaternion.IDENTITY);
    }

    //Le [pos x y z] e [rot qx qy qz qw], opcionais e em qualquer ordem.
    private Pose readPose() throws ErrorException {

        //Inicia com a posição neutra
        Vector3 position = Vector3.NULL;
        Quaternion orientation = Quaternion.IDENTITY;
        boolean hasPos = false, hasRot = false;

        //Enquanto há proximo
        while (hasNext()) 
        {

            //Pega o próximo token sem consumi-lo
            String t = peek();

            //se for == "pos" e ainda não leu nenhum "pos"
            if (t.equals("pos") && !hasPos) 
            {
                //Consome e lê 3 numeros
                take();
                position = readVector3();
                hasPos = true;

                //Mesma coisa com "rot"
            } 
            else if (t.equals("rot") && !hasRot) 
            {
                //Porém consome e lê 4
                take();
                orientation = readQuaternion();
                hasRot = true;

                //Senão encerra o laço sem consumir nada
            } 
            
            else
                break;
        }

        //Retorna a nova posição
        return new Pose(position, orientation);
    }

    //Lê os três números e monta o vetor
    private Vector3 readVector3() throws ErrorException 
    {
        float x = number("x");
        float y = number("y");
        float z = number("z");

        //Retorna o vetor montado
        return new Vector3(x, y, z);
    }

    //Lê um quaternio (x y z w) e calcula a norma: Pose exige quaternio unitario.
    private Quaternion readQuaternion() throws ErrorException 
    {
        float x = number("x");
        float y = number("y");
        float z = number("z");
        float w = number("w");
        float n = (float)Math.sqrt(x * x + y * y + z * z + w * w);


        //Verifica de n é nulo
        if (n <= Real.EPS)
            throw error("quaternio nulo nao representa uma rotacao");

        //Se "n" não for nulo divide todas as componentes por "n", deixando o quart com norma 1
        return new Quaternion(x / n, y / n, z / n, w / n);
    }


    private void tokenize() throws IOException 
    {
        //Abre o arquivo para leitura, try faz com que ele feche o mesmo ao final;
        try (BufferedReader r = new BufferedReader(new FileReader(file))) 
        {
            int n = 0;
            //Reponsável por contar o numero de linhas até acabar, n == nmr de linhas
            for (String line; (line = r.readLine()) != null; ) 
            {
                n++;
                //Verifica se é um token "#" e descarta todo o comentário
                int c = line.indexOf('#');
                if (c >= 0)
                    line = line.substring(0, c);

                //coloca espaços entre os "{" e "}" para se tornarem tokens sozinhas
                line = line.replace("{", " { ").replace("}", " } ").trim();
                //Linhas vazias são puladas
                if (line.isEmpty())
                    continue;
                for (String t : line.split("\\s+")) 
                {
                    tokens.add(t);
                    lines.add(n);
                }
                
            }
        }
    }

    //Verifica se ainda há tokens
    private boolean hasNext() 
    {
        return next < tokens.size();
    }


    //Devolve o próximo token sem avançar. Se acabou, erro (protege o código de arquivos cortados)
    private String peek() throws ErrorException 
    {
        if (!hasNext())
            throw error("fim de arquivo inesperado");
        return tokens.get(next);
    }

    //Devolve o token e avança o ponteiro "next"
    private String take() throws ErrorException 
    {
        String t = peek();

        next++;
        return t;
    }

    //Lê um token e exige que seja esperado, exemplo "{"
    private void expect(String expected) throws ErrorException 
    {
        String t = take();

        if (!t.equals(expected))
            throw error("esperado '" + expected + "', encontrado '" + t + "'");
    }


    //Lê um nome e rejeita chaves no lugar
    private String takeName() throws ErrorException 
    {
        String t = take();

        if (t.equals("{") || t.equals("}"))
            throw error("nome esperado, encontrado '" + t + "'");
        return t;
    }


    //Converte o token em float.
    private float number(String what) throws ErrorException 
    {
        String t = take();

        try {
            float v = Float.parseFloat(t);

            if (Float.isNaN(v) || Float.isInfinite(v))
                throw new NumberFormatException();
            return v;
        } 
        catch (NumberFormatException e) {
            throw error("numero invalido para " + what + ": '" + t + "'");
        }
    }


    /*Igual a number, porém exige valor maior que zero 
    (Densidade, Raio, Meias dimensões) */
    private float positive(String what) throws ErrorException 
    {
        float v = number(what);

        if (v <= 0)
            throw error(what + " deve ser maior que zero (encontrado " + v + ")");
        return v;
    }

    /** Erro com arquivo e linha do ultimo token consumido. */
    private SceneFormatException error(String msg) {
        int i = Math.min(Math.max(next - 1, 0), lines.size() - 1);
        int line = lines.isEmpty() ? 0 : lines.get(i);

        return new SceneFormatException(file.getName() + ", linha " + line + ": " + msg);
    }

} // SceneReader
