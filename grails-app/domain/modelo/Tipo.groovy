package modelo

class Tipo {
	
	String nome
	static hasMany = [produtoTipos: ProdutoTipo]
	Date dataCriacao = new Date()

    static constraints = {
		nome(maxSize: 50)
		dataCriacao()
    }
	
	@Override
	public String toString() {
		return nome
	}
}
