package excecao;

public class ProdutoJaPossuiLanceVencedorException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public ProdutoJaPossuiLanceVencedorException()
	{
	}

	public ProdutoJaPossuiLanceVencedorException(String msg)
	{	super(msg);
	}
}	