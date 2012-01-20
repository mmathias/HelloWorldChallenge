package modelo

class ProdutorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [produtorInstanceList: Produtor.list(params), produtorInstanceTotal: Produtor.count()]
    }

    def create = {
        def pc = new ProdutorCommand()
        return [produtorInstance: pc]
    }

    def save = {
        ProdutorCommand pc -> 
		
		if(pc.hasErrors())
		{
			render(view: "create", model: [produtorInstance: pc])
		}
		else
		{
			Produtor produtorInstance = new Produtor(params)

			if (produtorInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'produtor.label', default: 'Produtor'), produtorInstance.id])}"
				redirect(action: "show", id: produtorInstance.id)
			}
			else 
			{
				pc.errors = produtorInstance.errors
				render(view: "create", model: [produtorInstance: pc])
			}
		}
    }

	def show = {
        def produtorInstance = Produtor.get(params.id)
        if (!produtorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
            redirect(action: "list")
        }
        else {
            [produtorInstance: produtorInstance]
        }
    }

    def edit = {
        def produtorInstance = Produtor.get(params.id)
        if (!produtorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [produtorInstance: produtorInstance]
        }
    }

    def update = {

		ProdutorCommand pc ->
		def produtorInstance = Produtor.get(params.id)
		if (produtorInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (produtorInstance.version > version)
				{   pc = new ProdutorCommand()
					atribuiProdutorAComando(produtorInstance, pc)
					pc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'produtor.label', 
													   default: 'Produtor')] as Object[], 
												       "Outro usuário atualizou este(a) Produtor enquanto você estava editando")
					render(view: "edit", model: [produtorInstance: pc])
					return
				}
			} 
			if (pc.hasErrors())
			{   pc.id = params.id as long
				pc.version = params.version as long
				render(view: "edit", model: [ produtorInstance: pc ])
			}
			else
			{
				produtorInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (produtorInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'produtor.label', default: 'Produtor'), produtorInstance.id])}"
					redirect(action: "show", id: produtorInstance.id)
				}
				else
				{   pc.id = params.id as long
					pc.version = params.version as long
					pc.errors = produtorInstance.errors
					render(view: "edit", model: [produtorInstance: pc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def produtorInstance = Produtor.get(params.id)
        if (produtorInstance) {
            try {
                produtorInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produtor.label', default: 'Produtor'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiProdutorAComando(def produtorInstance, def pc)
	{
        pc.id = produtorInstance.id
		pc.version = produtorInstance.version
		
		pc.produtos = produtorInstance.produtos
        pc.usuario = produtorInstance.usuario

	}
}

class ProdutorCommand
{
	Long id
    Long version
	def produtos
    Usuario usuario

	static constraints = {
        usuario(blank: false, nullable: false)

	}
}

