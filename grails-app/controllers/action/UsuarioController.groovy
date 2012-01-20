package action

import modelo.Cidade
import modelo.Usuario

class UsuarioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	// A LINHA ABAIXO FOI ACRESCENTADA
	def authenticateService

	def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [usuarioInstanceList: Usuario.list(params), usuarioInstanceTotal: Usuario.count()]
    }

    def create = {
        def uc = new UsuarioCommand()
        return [usuarioInstance: uc]
    }

    def save = {
        UsuarioCommand uc -> 
		
		if(uc.hasErrors())
		{
			render(view: "create", model: [usuarioInstance: uc])
		}
		else
		{
			Usuario usuarioInstance = new Usuario(params)
			
			// A LINHA ABAIXO FOI ACRESCENTADA
			usuarioInstance.senha = authenticateService.encodePassword(usuarioInstance.senha)
			
			if (usuarioInstance.save(flush: true)) 
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])}"
				redirect(action: "show", id: usuarioInstance.id)
			}
			else 
			{
				uc.errors = usuarioInstance.errors
				render(view: "create", model: [usuarioInstance: uc])
			}
		}
    }

	def show = {
        def usuarioInstance = Usuario.get(params.id)
        if (!usuarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
            redirect(action: "list")
        }
        else {
            [usuarioInstance: usuarioInstance]
        }
    }

    def edit = {
        def usuarioInstance = Usuario.get(params.id)
        if (!usuarioInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
            redirect(action: "list")
        }
        else {
			return [usuarioInstance: usuarioInstance]
        }
    }

    def update = {

		UsuarioCommand uc ->
		def usuarioInstance = Usuario.get(params.id)
		if (usuarioInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (usuarioInstance.version > version)
				{   uc = new UsuarioCommand()
					atribuiUsuarioAComando(usuarioInstance, uc)
					uc.errors.rejectValue("version", "default.optimistic.locking.failure", 
						                               [message(code: 'usuario.label', 
													   default: 'Usuario')] as Object[], 
												       "Outro usuário atualizou este(a) Usuario enquanto você estava editando")
					render(view: "edit", model: [usuarioInstance: uc])
					return
				}
			} 
			if (uc.hasErrors())
			{   uc.id = params.id as long
				uc.version = params.version as long
				render(view: "edit", model: [ usuarioInstance: uc ])
			}
			else
			{
				// AS TRÊS LINHAS ABAIXO FORAM ACRESCENTADAS
				if(uc.senha != usuarioInstance.senha)
				{	params.senha = authenticateService.encodePassword(params.senha)
				}
				
				usuarioInstance.properties = params

//              bindData(usuarioInstance, params)
//              bindData(usuarioInstance, params, ['dataNascimento'])
//              usuarioInstance.dataNascimento = Util.strToDate(params.dataNascimento)
								
				if (usuarioInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])}"
					redirect(action: "show", id: usuarioInstance.id)
				}
				else
				{   uc.id = params.id as long
					uc.version = params.version as long
					uc.errors = usuarioInstance.errors
					render(view: "edit", model: [usuarioInstance: uc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
			redirect(action: "list")
		}
    }

    def delete = {
        def usuarioInstance = Usuario.get(params.id)
        if (usuarioInstance) {
            try {
                usuarioInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])}"
            redirect(action: "list")
        }
    }

	void atribuiUsuarioAComando(def usuarioInstance, def uc)
	{
        uc.id = usuarioInstance.id
		uc.version = usuarioInstance.version
		
		uc.authorities = usuarioInstance.authorities
        uc.cidade = usuarioInstance.cidade
        uc.conta = usuarioInstance.conta
        uc.dataCadastro = usuarioInstance.dataCadastro
        uc.enabled = usuarioInstance.enabled
        uc.endereco = usuarioInstance.endereco
        uc.nome = usuarioInstance.nome
        uc.senha = usuarioInstance.senha

	}
}

class UsuarioCommand
{
	Long id
    Long version
	def authorities
    String conta
    String email
    Date dataCadastro
    boolean enabled
    String nome
    String senha

	static constraints = {
        
        conta(blank: false, nullable: false)
        dataCadastro(blank: false, nullable: false)
        enabled()
        email(blank: false, nullable: false)
        nome(blank: false, nullable: false)
        senha(blank: false, nullable: false)

	}
}

