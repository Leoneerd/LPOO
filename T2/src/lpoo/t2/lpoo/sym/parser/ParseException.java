package lpoo.sym.parser;

/**
 *
 * @author Paulo Pagliosa
 */
public class ParseException extends Exception
{
	protected ParseException(String s)
	{
		super(s);
	}

	protected ParseException(String s, Object... args)
	{
		this(String.format(s, args));
	}

} // ParseException
