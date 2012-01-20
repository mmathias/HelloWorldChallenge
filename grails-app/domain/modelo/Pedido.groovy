package modelo

class Pedido {
	
	static belongsTo = [cliente: Cliente]
	static hasMany = [itens: Item]

    static constraints = {
    }
}
