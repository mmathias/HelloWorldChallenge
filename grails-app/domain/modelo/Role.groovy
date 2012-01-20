package modelo

class Role {

	String description
	String authority

	static hasMany = [people: Usuario]
	static belongsTo = Usuario
	
	String toString()
	{
		return authority
	}

	static constraints = {
		authority(blank: false, unique: true)
		description(blank: false)
		people()
	}
}
