<%=packageName ? "package ${packageName}\n\n" : ''%>class ${className}Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]
    }

    def create = {
        def ${propertyName.substring(0,1)}c = new ${className}Command()
        return [${propertyName}: ${propertyName.substring(0,1)}c]
    }

    def save = {
        ${className}Command ${propertyName.substring(0,1)}c -> 
		
		if(${propertyName.substring(0,1)}c.hasErrors())
		{
			render(view: "create", model: [${propertyName}: ${propertyName.substring(0,1)}c])
		}
		else
		{
			${className} ${propertyName} = new ${className}(params)

			if (${propertyName}.save(flush: true)) 
			{
				flash.message = "\${message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
				redirect(action: "show", id: ${propertyName}.id)
			}
			else 
			{
				${propertyName.substring(0,1)}c.errors = ${propertyName}.errors
				render(view: "create", model: [${propertyName}: ${propertyName.substring(0,1)}c])
			}
		}
    }

	def show = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
        else {
            [${propertyName}: ${propertyName}]
        }
    }

    def edit = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [${propertyName}: ${propertyName}]
        }
    }

    def update = {

		${className}Command ${propertyName.substring(0,1)}c ->
		def ${propertyName} = ${className}.get(params.id)
		if (${propertyName})
		{   if (params.version)
			{   def version = params.version.toLong()
				if (${propertyName}.version > version)
				{   ${propertyName.substring(0,1)}c = new ${className}Command()
					atribui${className}AComando(${propertyName}, ${propertyName.substring(0,1)}c)
					${propertyName.substring(0,1)}c.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: '${domainClass.propertyName}.label', 
													   default: '${className}')] as Object[], 
												       "Outro usuário atualizou este(a) ${className} enquanto você estava editando")
					render(view: "edit", model: [${propertyName}: ${propertyName.substring(0,1)}c])
					return
				}
			} 
			if (${propertyName.substring(0,1)}c.hasErrors())
			{   ${propertyName.substring(0,1)}c.id = params.id as long
				${propertyName.substring(0,1)}c.version = params.version as long
				render(view: "edit", model: [ ${propertyName}: ${propertyName.substring(0,1)}c ])
			}
			else
			{
				${propertyName}.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (${propertyName}.save(flush: true))
				{   flash.message = "\${message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])}"
					redirect(action: "show", id: ${propertyName}.id)
				}
				else
				{   ${propertyName.substring(0,1)}c.id = params.id as long
					${propertyName.substring(0,1)}c.version = params.version as long
					${propertyName.substring(0,1)}c.errors = ${propertyName}.errors
					render(view: "edit", model: [${propertyName}: ${propertyName.substring(0,1)}c])
				}
			}
		}
		else
		{   flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            try {
                ${propertyName}.delete(flush: true)
                flash.message = "\${message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "\${message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribui${className}AComando(def ${propertyName}, def ${propertyName.substring(0,1)}c)
	{
        ${propertyName.substring(0,1)}c.id = ${propertyName}.id
		${propertyName.substring(0,1)}c.version = ${propertyName}.version
		
		<%
		def nomes = domainClass.persistentProperties*.name
		def tipos = domainClass.persistentProperties*.type

		for (int i=0; i < nomes.size(); i++) 
		{
			if(tipos[i] == java.math.BigDecimal.class)
			{	println (( i ? "        " : "") + propertyName.substring(0,1) + 'c.' + nomes[i] + ' = Util.valorMonetarioToStr(' + propertyName + '.' + nomes[i] + ')')
			}
			else
			{	println (( i ? "        " : "") + propertyName.substring(0,1) + 'c.' + nomes[i] + ' = ' + propertyName + '.' + nomes[i])
			}
		}
		%>
	}
}

class ${className}Command
{
	Long id
    Long version
	<%

//  println "// getAssociationMap() = " + domainClass.getAssociationMap()
//	println "// getConstrainedProperties() = " + domainClass.getConstrainedProperties()
//	println "// getIdentifier() = " + domainClass.getIdentifier()
//	println "// getMappedBy() = " + domainClass.getMappedBy()
//	println "// getMappingStrategy() = " + domainClass.getMappingStrategy()
//	println "// getPersistentProperties() = " + domainClass.getPersistentProperties()
//	println "// getProperties() = " + domainClass.getProperties()
//	println "// getPropertyName() = " + domainClass.getPropertyName()
//	println "// getValidator() = " + domainClass.getValidator()
//	
//	println "// getClazz() = " + domainClass.getClazz()
//	println "// getFullName() = " + domainClass.getFullName()
//	println "// getLogicalPropertyName() = " + domainClass.getLogicalPropertyName()
//	println "// getMetaClass() = " + domainClass.getMetaClass()
//	println "// getValidator() = " + domainClass.getValidator()
//	println "// getNaturalName() = " + domainClass.getNaturalName()
//	println "// getPackageName() = " + domainClass.getPackageName()
//	println "// getReference() = " + domainClass.getReference()
//	println "// getShortName() = " + domainClass.getShortName()
	
	for (int i=0; i < nomes.size(); i++) 
	{
		// retorna um map com todos os atributos persistentes que representam associações.
		// Exemplo: [lances:class modelo.Lance] 
		
		if(domainClass.associationMap[nomes[i]] == null)
		{
			if (tipos[i].toString().indexOf("modelo") == -1)
			{	
				if(tipos[i] == java.util.Date.class)
				{   println (( i ? "    " : "") + 'Date ' + nomes[i])
				}
				else if(tipos[i] == boolean)
				{   println (( i ? "    " : "") + 'boolean ' + nomes[i])
				}
				else if(tipos[i] == byte[])
				{   println (( i ? "    " : "") + 'byte[] ' + nomes[i])
				}
				else
				{	println (( i ? "    " : "") + 'String ' + nomes[i])
				}
			}
			else
			{	
				println (( i ? "    " : "") + domainClass.getPropertyByName(nomes[i]).getType().getSimpleName() + " " + nomes[i])
			}
		}
		else
		{	println (( i ? "    " : "") + 'def ' + nomes[i])
		}
		
    }
    %>
	static constraints = {
        <%
		int j = 0;
        for (int i=0; i < nomes.size(); i++) 
        {
			if(domainClass.associationMap[nomes[i]] == null)
			{
//				if (tipos[i].toString().indexOf("modelo") == -1)
//				{	
					if(tipos[i] == java.math.BigDecimal.class)
					{   println (( j ? "        " : "") + nomes[i] + '(blank: false, validator: {' + nomes[i] + ' -> ValorMonetarioValidator.validaValorMonetario(' + nomes[i] + ') })')
					}
					else if(tipos[i] == java.lang.Integer.class)
					{   println (( j ? "        " : "") + nomes[i] + '(blank: false, validator: {' + nomes[i] + ' -> ValorInteiroValidator.validaValorInteiro(' + nomes[i] + ') })')
					}
					else if(tipos[i] == java.util.Date.class)
					{   
						println (( j ? "        " : "") + nomes[i] + '(blank: false, nullable: false)')
					}
					else if(tipos[i] == byte[])
					{   
						println (( j ? "        " : "") + nomes[i] + '(blank: true, nullable: true)')
					}
					else if(tipos[i] == boolean)
					{   
						println (( j ? "        " : "") + nomes[i] + '()')
					}
					else
					{   println (( j ? "        " : "") + nomes[i] + '(blank: false, nullable: false)')
					}
					
					j++
//				}
			}
		}
		%>
	}
}

