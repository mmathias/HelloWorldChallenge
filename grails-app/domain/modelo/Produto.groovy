package modelo

import java.util.Date;

class Produto {
	String nome
	BigDecimal valor
	byte[] foto

	static hasMany = [produtoTipos: ProdutoTipo, itens:Item]
	static belongsTo = [produtor: Produtor]
	Date dataCadastro = new Date()
	
	
    static constraints = {
		nome(maxSize: 50)
		dataCadastro()
		valor()
		foto(nullable: true)
    }
	
	static mapping =
	{
		nome index: 'produto_nome_idx'
		dataCadastro index : 'produto_data_cadastro_idx'
	}

	String toString()
	{   return nome
	}
}
