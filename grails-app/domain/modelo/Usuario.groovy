package modelo

class Usuario 
{
	String nome
	String email
	String conta
	String senha
	Date dataCadastro
	boolean enabled

	static hasMany = [authorities: Role]
	
	String toString()
	{
		return nome
	}
	
	static constraints = {
        nome(maxSize: 70)
		conta(maxSize: 8, unique: true)
		senha()
		enabled()
		dataCadastro()
		authorities()
	}
}
