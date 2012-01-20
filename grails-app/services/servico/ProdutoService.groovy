package servico

import modelo.Tipo
import modelo.Produto
import modelo.ProdutoTipo

class ProdutoService {

    static transactional = true

	def removerTipoDeProduto(def produto, def tipo)
	{
		def produtoTipo = ProdutoTipo.find("from ProdutoTipo pc " +
			"where pc.produto = :produto and pc.tipo = :tipo", [ produto: produto, tipo: tipo ])

		if(produtoTipo)
		{
			produtoTipo.delete()
		}
	}

	def adicionarTipoAProduto(def produto, def tipo)
	{
		def produtoTipo= ProdutoTipo.find("from ProdutoTipo pc " +
			"where pc.produto = :produto and pc.tipo = :tipo", [ produto: produto, tipo: tipo ])

		if(!produtoTipo)
		{
			new ProdutoTipo(produto: produto, tipo: tipo).save()
		}
	}
	
	
}
