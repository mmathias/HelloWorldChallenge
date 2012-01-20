package modelo

class PedidoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [pedidoInstanceList: Pedido.list(params), pedidoInstanceTotal: Pedido.count()]
    }

    def create = {
        def pc = new PedidoCommand()
        return [pedidoInstance: pc]
    }

    def save = {
        PedidoCommand pc -> 
		
		if(pc.hasErrors())
		{
			render(view: "create", model: [pedidoInstance: pc])
		}
		else
		{
			Pedido pedidoInstance = new Pedido(params)

			if (pedidoInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedidoInstance.id])}"
				redirect(action: "show", id: pedidoInstance.id)
			}
			else 
			{
				pc.errors = pedidoInstance.errors
				render(view: "create", model: [pedidoInstance: pc])
			}
		}
    }

	def show = {
        def pedidoInstance = Pedido.get(params.id)
        if (!pedidoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
            redirect(action: "list")
        }
        else {
            [pedidoInstance: pedidoInstance]
        }
    }

    def edit = {
        def pedidoInstance = Pedido.get(params.id)
        if (!pedidoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [pedidoInstance: pedidoInstance]
        }
    }

    def update = {

		PedidoCommand pc ->
		def pedidoInstance = Pedido.get(params.id)
		if (pedidoInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (pedidoInstance.version > version)
				{   pc = new PedidoCommand()
					atribuiPedidoAComando(pedidoInstance, pc)
					pc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'pedido.label', 
													   default: 'Pedido')] as Object[], 
												       "Outro usuário atualizou este(a) Pedido enquanto você estava editando")
					render(view: "edit", model: [pedidoInstance: pc])
					return
				}
			} 
			if (pc.hasErrors())
			{   pc.id = params.id as long
				pc.version = params.version as long
				render(view: "edit", model: [ pedidoInstance: pc ])
			}
			else
			{
				pedidoInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (pedidoInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'pedido.label', default: 'Pedido'), pedidoInstance.id])}"
					redirect(action: "show", id: pedidoInstance.id)
				}
				else
				{   pc.id = params.id as long
					pc.version = params.version as long
					pc.errors = pedidoInstance.errors
					render(view: "edit", model: [pedidoInstance: pc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def pedidoInstance = Pedido.get(params.id)
        if (pedidoInstance) {
            try {
                pedidoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'pedido.label', default: 'Pedido'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiPedidoAComando(def pedidoInstance, def pc)
	{
        pc.id = pedidoInstance.id
		pc.version = pedidoInstance.version
		
		pc.cliente = pedidoInstance.cliente
        pc.itens = pedidoInstance.itens

	}
}

class PedidoCommand
{
	Long id
    Long version
	Cliente cliente
    def itens

	static constraints = {
        cliente(blank: false, nullable: false)

	}
}

