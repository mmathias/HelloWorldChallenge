package modelo

class Produtor {
	
	static belongsTo = [usuario: Usuario]
	static hasMany = [produtos: Produto]
	
    static constraints = {
		usuario()
    }
	
	
}
