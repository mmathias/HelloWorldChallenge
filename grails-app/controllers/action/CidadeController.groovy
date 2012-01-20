package action

import modelo.Cidade

class CidadeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [cidadeInstanceList: Cidade.list(params), cidadeInstanceTotal: Cidade.count()]
    }

    def create = {
        def cc = new CidadeCommand()
        return [cidadeInstance: cc]
    }

    def save = {
        CidadeCommand cc -> 
		
		if(cc.hasErrors())
		{
			render(view: "create", model: [cidadeInstance: cc])
		}
		else
		{
			Cidade cidadeInstance = new Cidade(params)

			if (cidadeInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'cidade.label', default: 'Cidade'), cidadeInstance.id])}"
				redirect(action: "show", id: cidadeInstance.id)
			}
			else 
			{
				cc.errors = cidadeInstance.errors
				render(view: "create", model: [cidadeInstance: cc])
			}
		}
    }

	def show = {
        def cidadeInstance = Cidade.get(params.id)
        if (!cidadeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
            redirect(action: "list")
        }
        else {
            [cidadeInstance: cidadeInstance]
        }
    }

    def edit = {
        def cidadeInstance = Cidade.get(params.id)
        if (!cidadeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [cidadeInstance: cidadeInstance]
        }
    }

    def update = {

		CidadeCommand cc ->
		def cidadeInstance = Cidade.get(params.id)
		if (cidadeInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (cidadeInstance.version > version)
				{   cc = new CidadeCommand()
					atribuiCidadeAComando(cidadeInstance, cc)
					cc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'cidade.label', 
													   default: 'Cidade')] as Object[], 
												       "Outro usuário atualizou este(a) Cidade enquanto você estava editando")
					render(view: "edit", model: [cidadeInstance: cc])
					return
				}
			} 
			if (cc.hasErrors())
			{   cc.id = params.id as long
				cc.version = params.version as long
				render(view: "edit", model: [ cidadeInstance: cc ])
			}
			else
			{
				cidadeInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (cidadeInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'cidade.label', default: 'Cidade'), cidadeInstance.id])}"
					redirect(action: "show", id: cidadeInstance.id)
				}
				else
				{   cc.id = params.id as long
					cc.version = params.version as long
					cc.errors = cidadeInstance.errors
					render(view: "edit", model: [cidadeInstance: cc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def cidadeInstance = Cidade.get(params.id)
        if (cidadeInstance) {
            try {
                cidadeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'cidade.label', default: 'Cidade'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiCidadeAComando(def cidadeInstance, def cc)
	{
        cc.id = cidadeInstance.id
		cc.version = cidadeInstance.version
		
		cc.nome = cidadeInstance.nome

	}
}

class CidadeCommand
{
	Long id
    Long version
	String nome

	static constraints = {
        nome(blank: false)
	}
}

