package modelo

class TipoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [tipoInstanceList: Tipo.list(params), tipoInstanceTotal: Tipo.count()]
    }

    def create = {
        def tc = new TipoCommand()
        return [tipoInstance: tc]
    }

    def save = {
        TipoCommand tc -> 
		
		if(tc.hasErrors())
		{
			render(view: "create", model: [tipoInstance: tc])
		}
		else
		{
			Tipo tipoInstance = new Tipo(params)

			if (tipoInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'tipo.label', default: 'Tipo'), tipoInstance.id])}"
				redirect(action: "show", id: tipoInstance.id)
			}
			else 
			{
				tc.errors = tipoInstance.errors
				render(view: "create", model: [tipoInstance: tc])
			}
		}
    }

	def show = {
        def tipoInstance = Tipo.get(params.id)
        if (!tipoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
            redirect(action: "list")
        }
        else {
            [tipoInstance: tipoInstance]
        }
    }

    def edit = {
        def tipoInstance = Tipo.get(params.id)
        if (!tipoInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [tipoInstance: tipoInstance]
        }
    }

    def update = {

		TipoCommand tc ->
		def tipoInstance = Tipo.get(params.id)
		if (tipoInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (tipoInstance.version > version)
				{   tc = new TipoCommand()
					atribuiTipoAComando(tipoInstance, tc)
					tc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'tipo.label', 
													   default: 'Tipo')] as Object[], 
												       "Outro usuário atualizou este(a) Tipo enquanto você estava editando")
					render(view: "edit", model: [tipoInstance: tc])
					return
				}
			} 
			if (tc.hasErrors())
			{   tc.id = params.id as long
				tc.version = params.version as long
				render(view: "edit", model: [ tipoInstance: tc ])
			}
			else
			{
				tipoInstance.properties = params
//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (tipoInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'tipo.label', default: 'Tipo'), tipoInstance.id])}"
					redirect(action: "show", id: tipoInstance.id)
				}
				else
				{   tc.id = params.id as long
					tc.version = params.version as long
					tc.errors = tipoInstance.errors
					render(view: "edit", model: [tipoInstance: tc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def tipoInstance = Tipo.get(params.id)
        if (tipoInstance) {
            try {
                tipoInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'tipo.label', default: 'Tipo'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiTipoAComando(def tipoInstance, def tc)
	{
        tc.id = tipoInstance.id
		tc.version = tipoInstance.version
		
		tc.dataCriacao = tipoInstance.dataCriacao
        tc.nome = tipoInstance.nome
        tc.produtoTipos = tipoInstance.produtoTipos

	}
}

class TipoCommand
{
	Long id
    Long version
	Date dataCriacao
    String nome
    def produtoTipos

	static constraints = {
        dataCriacao(blank: false, nullable: false)
        nome(blank: false, nullable: false)

	}
}

