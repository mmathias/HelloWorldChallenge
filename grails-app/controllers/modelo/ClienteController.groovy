package modelo

class ClienteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [clienteInstanceList: Cliente.list(params), clienteInstanceTotal: Cliente.count()]
    }

    def create = {
        def cc = new ClienteCommand()
        return [clienteInstance: cc]
    }

    def save = {
        ClienteCommand cc -> 
		
		if(cc.hasErrors())
		{
			render(view: "create", model: [clienteInstance: cc])
		}
		else
		{
			Cliente clienteInstance = new Cliente(params)

			if (clienteInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), clienteInstance.id])}"
				redirect(action: "show", id: clienteInstance.id)
			}
			else 
			{
				cc.errors = clienteInstance.errors
				render(view: "create", model: [clienteInstance: cc])
			}
		}
    }

	def show = {
        def clienteInstance = Cliente.get(params.id)
        if (!clienteInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
            redirect(action: "list")
        }
        else {
            [clienteInstance: clienteInstance]
        }
    }

    def edit = {
        def clienteInstance = Cliente.get(params.id)
        if (!clienteInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [clienteInstance: clienteInstance]
        }
    }

    def update = {

		ClienteCommand cc ->
		def clienteInstance = Cliente.get(params.id)
		if (clienteInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (clienteInstance.version > version)
				{   cc = new ClienteCommand()
					atribuiClienteAComando(clienteInstance, cc)
					cc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'cliente.label', 
													   default: 'Cliente')] as Object[], 
												       "Outro usuário atualizou este(a) Cliente enquanto você estava editando")
					render(view: "edit", model: [clienteInstance: cc])
					return
				}
			} 
			if (cc.hasErrors())
			{   cc.id = params.id as long
				cc.version = params.version as long
				render(view: "edit", model: [ clienteInstance: cc ])
			}
			else
			{
				clienteInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (clienteInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), clienteInstance.id])}"
					redirect(action: "show", id: clienteInstance.id)
				}
				else
				{   cc.id = params.id as long
					cc.version = params.version as long
					cc.errors = clienteInstance.errors
					render(view: "edit", model: [clienteInstance: cc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def clienteInstance = Cliente.get(params.id)
        if (clienteInstance) {
            try {
                clienteInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiClienteAComando(def clienteInstance, def cc)
	{
        cc.id = clienteInstance.id
		cc.version = clienteInstance.version
		
		cc.pedidos = clienteInstance.pedidos
        cc.usuario = clienteInstance.usuario

	}
}

class ClienteCommand
{
	Long id
    Long version
	def pedidos
    Usuario usuario

	static constraints = {
        usuario(blank: false, nullable: false)

	}
}

