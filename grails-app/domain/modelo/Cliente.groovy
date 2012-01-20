package modelo

class Cliente {
	
	static belongsTo = [usuario: Usuario]
	static hasMany = [pedidos: Pedido]

    static constraints = {
		usuario()
    }
}
