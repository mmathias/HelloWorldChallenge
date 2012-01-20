package excecao;

public class ProdutoSemLanceException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public ProdutoSemLanceException()
	{
	}

	public ProdutoSemLanceException(String msg)
	{	super(msg);
	}
}	