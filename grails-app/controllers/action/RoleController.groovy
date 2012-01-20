package action

import modelo.Role

class RoleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [roleInstanceList: Role.list(params), roleInstanceTotal: Role.count()]
    }

    def create = {
        def rc = new RoleCommand()
        return [roleInstance: rc]
    }

    def save = {
        RoleCommand rc -> 
		
		if(rc.hasErrors())
		{
			render(view: "create", model: [roleInstance: rc])
		}
		else
		{
			Role roleInstance = new Role(params)

			if (roleInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
				redirect(action: "show", id: roleInstance.id)
			}
			else 
			{
				rc.errors = roleInstance.errors
				render(view: "create", model: [roleInstance: rc])
			}
		}
    }

	def show = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            [roleInstance: roleInstance]
        }
    }

    def edit = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [roleInstance: roleInstance]
        }
    }

    def update = {

		RoleCommand rc ->
		def roleInstance = Role.get(params.id)
		if (roleInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (roleInstance.version > version)
				{   rc = new RoleCommand()
					atribuiRoleAComando(roleInstance, rc)
					rc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'role.label', 
													   default: 'Role')] as Object[], 
												       "Outro usuário atualizou este(a) Role enquanto você estava editando")
					render(view: "edit", model: [roleInstance: rc])
					return
				}
			} 
			if (rc.hasErrors())
			{   rc.id = params.id as long
				rc.version = params.version as long
				render(view: "edit", model: [ roleInstance: rc ])
			}
			else
			{
				roleInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (roleInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), roleInstance.id])}"
					redirect(action: "show", id: roleInstance.id)
				}
				else
				{   rc.id = params.id as long
					rc.version = params.version as long
					rc.errors = roleInstance.errors
					render(view: "edit", model: [roleInstance: rc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def roleInstance = Role.get(params.id)
        if (roleInstance) {
            try {
                roleInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiRoleAComando(def roleInstance, def rc)
	{
        rc.id = roleInstance.id
		rc.version = roleInstance.version
		
		rc.authority = roleInstance.authority
        rc.description = roleInstance.description
        rc.people = roleInstance.people

	}
}

class RoleCommand
{
	Long id
    Long version
	String authority
    String description
    def people

	static constraints = {
        authority(blank: false, nullable: false)
        description(blank: false, nullable: false)

	}
}

