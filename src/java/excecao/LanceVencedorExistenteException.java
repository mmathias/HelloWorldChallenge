package excecao;

public class LanceVencedorExistenteException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public LanceVencedorExistenteException()
	{
	}

	public LanceVencedorExistenteException(String msg)
	{	super(msg);
	}
}	