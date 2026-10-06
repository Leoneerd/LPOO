// Autor(es): Guilherme Duarte, Otavio Gabriel, Leonardo Leal
package lpoo.exception;

// Erro de formato (ou de conteudo) em um arquivo de cena. */
public class SceneFormatException extends ErrorException 
{
    public SceneFormatException(String msg)
    {
        super(msg);
    }
}
