package excecao;

public class ValorDoLanceInvalidoException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public ValorDoLanceInvalidoException(String msg)
	{	super(msg);
	}
}	