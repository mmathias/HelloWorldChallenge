package modelo

class ProdutoTipo implements Serializable{
	
	Tipo tipo
	Produto produto
	
	static belongsTo = [Produto]

	static mapping =
	{
		table 'PRODUTO_TIPO'
		id composite:['produto', 'tipo']
		produto column: 'PRODUTO_ID'
		categoria column: 'TIPO_ID'
	}
	
	@Override
	public String toString() {
		return tipo.nome;
	}
}
