package modelo

class Item {

	int quantidade
	Boolean aprovado
	Boolean enviado
	static belongsTo = [produto: Produto, pedido: Pedido]
	
	
    static constraints = {
		quantidade()
		aprovado()
		enviado()
    }
}
