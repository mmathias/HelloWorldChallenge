package modelo

import javax.annotation.Resource.AuthenticationType;
import org.springframework.security.AuthenticationTrustResolverImpl
import org.springframework.security.DisabledException
import org.springframework.security.context.SecurityContextHolder as SCH
import org.springframework.security.ui.AbstractProcessingFilter
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter
import org.springframework.security.util.RedirectUtils
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.grails.plugins.springsecurity.service.AuthenticateService

import servico.ProdutoService;

import excecao.ArquivoInexistenteException
import excecao.ExtensaoNaoPermitidaException
import excecao.TamanhoDeArquivoSuperiorAoLimiteException
import grails.plugin.mail.MailService;
import modelo.Produtor;

class ProdutoController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def MailService mailService
	def authenticateService
	def produtoService

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[produtoInstanceList: Produto.list(params), produtoInstanceTotal: Produto.count()]
	}

	def create = {
		println Produto.list()
		def produtoInstance = new Produto()
		return [produtoInstance: produtoInstance]
	}

	def save = {  ProdutoCommand pc ->

		if(pc.hasErrors()) {
			render(view: "create", model: [produtoInstance: pc])
		}
		else {
			Produto produtoInstance = new Produto(params)

			Usuario usuario = Usuario.get(authenticateService.userDomain().id)
			produtoInstance.produtor = Produtor.findByUsuario(usuario)


			if (produtoInstance.save(flush: true)) {

				if(params.foto) {
					try {
						salvarImagem(pc.nome)
						flash.message = "${message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'produto'), produtoInstance.id])}"
					}
					catch(ArquivoInexistenteException e) {
						flash.message = "${message(code: 'produto.created.foto.inexistente.message', args: [produtoInstance.id])}"
					}
					catch(TamanhoDeArquivoSuperiorAoLimiteException e) {
						def config = grailsApplication.config.uploadDeArquivo["imagem"]

						def tamanhoMaximoEmKb = ((int) (config.tamanhoMaximo/1024))

						flash.message = "${message(code: 'produto.updated.foto.superior.ao.limite.message', args: [produtoInstance.id, tamanhoMaximoEmKb])}"
					}
					catch(ExtensaoNaoPermitidaException e) {
						flash.message = "${message(code: 'produto.created.foto.extensao.nao.permitida.message', args: [produtoInstance.id])}"
					}
				}
				else {
					flash.message = "${message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produtoInstance.id])}"
				}

				if(params.tipos){

					for (Tipo tipo : params.tipos) {
						produtoService.adicionarTipoAProduto(produtoInstance, Tipo.get(tipo))
						//						ProdutoTipo produtoTipo = new ProdutoTipo()
						//						produtoTipo.produto = produtoInstance
						//						produtoTipo.tipo = Tipo.get(tipo)
						//
						//						produtoTipo.save(flush: true)
					}
				}

				enviarEmails()

				redirect(action: "show", id: produtoInstance.id)
			}
			else {
				pc.errors = produtoInstance.errors
				render(view: "create", model: [produtoInstance: pc])
			}
		}
	}

	def show = {
		def produtoInstance = Produto.get(params.id)
		if (!produtoInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
			redirect(action: "list")
		}
		else {
			[produtoInstance: produtoInstance]
		}
	}

	def exibir = {
		if (params.offset == null) params.offset = 0

		Produto produto = Produto.find("from Produto p " +
				"left outer join fetch p.produtoTipo pc " +
				"left outer join fetch pc.tipo " +
				"where p.id = :id", [ id: params.id as Long ])

		//		if (!produto) {
		//			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
		//			redirect(action: "list")
		//		}
		//		else {

		def tipos = produto.produtoTipos.collect { it.tipo }
		def tiposOrdenados = tipos.sort { cat1, cat2 -> cat1.id <=> cat2.id }
		def listaDeIds = tiposOrdenados*.id

		def demaisTiposList
		def demaisTiposTotal

		if (listaDeIds.size() > 0)
		{
			demaisTiposList = Tipo.findAll( "from Tipo as t where t.id not in (:lista)",
					[lista: listaDeIds],
					[max: 10, offset: params.offset as long])
			demaisTiposTotal = Tipo.executeQuery( "select count(t) from Tipo as t " +
					"where t.id not in (:lista)", [lista: listaDeIds])[0]
		}
		else
		{
			demaisTiposList = Tipo.list(max: 10, offset: params.offset as long)
			demaisTiposTotal = Tipo.count()
		}

		[ produtoInstance : produto,
					tiposDoProduto : tiposOrdenados,
					demaisTiposList : demaisTiposList,
					demaisTiposTotal: demaisTiposTotal ]
		//		}
	}

	def removerTipoDeProduto =
	{
		produtoService.removerTipoDeProduto(params.idProduto, params.idTipo)

		chain(action: exibir, id: params.idProduto)
		// redirect ou chain para uma action e render para uma view
	}

	def adicionarTipoAProduto =
	{
		produtoService.adicionarTipoAProduto(params.idProduto, params.idTipo)

		chain(action: exibir, id: params.idProduto)
	}

	def edit = {
		def produtoInstance = Produto.get(params.id)
		if (!produtoInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [produtoInstance: produtoInstance]
		}
	}
	
	def listaItensEnviados = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def lista = new ArrayList<Produto>()
		
		Usuario usuario = Usuario.get(authenticateService.userDomain().id)
		Produtor produtor = Produtor.findByUsuario(usuario)
		
		def achei = false
		produtor.produtos.each{
			it.itens.each{
				if (it.enviado == true){
					achei = true	
				}
			}
			if (achei == true){
				lista.add(it)
				achei = false
			}
		}
		
		[produtoInstanceList: lista, produtoInstanceTotal: lista.count()]
	}

	def update = { ProdutoCommand pc ->
		def produtoInstance = Produto.get(params.id)
		if (produtoInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (produtoInstance.version > version)
				{   pc = new ProdutoCommand()
					atribuiProdutoAComando(produtoInstance, pc)
					pc.errors.rejectValue("version", "default.optimistic.locking.failure",
							[
								message(code: 'produto.label',
								default: 'Produto')]
							as Object[],
							"Outro usuário atualizou este(a) Produto enquanto você estava editando")
					render(view: "edit", model: [produtoInstance: pc])
					return
				}
			}
			if (pc.hasErrors())
			{   pc.id = params.id as long
				pc.version = params.version as long
				render(view: "edit", model: [ produtoInstance: pc ])
			}
			else
			{
				produtoInstance.properties = params

				if (produtoInstance.save(flush: true))
				{
					if(params.foto)
					{
						try
						{	salvarImagem(pc.nome)
							flash.message = "${message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'produto'), produtoInstance.id])}"
						}
						catch(ArquivoInexistenteException e)
						{	flash.message = "${message(code: 'produto.created.foto.inexistente.message', args: [produtoInstance.id])}"
						}
						catch(TamanhoDeArquivoSuperiorAoLimiteException e)
						{
							def config = grailsApplication.config.uploadDeArquivo["imagem"]

							def tamanhoMaximoEmKb = ((int) (config.tamanhoMaximo/1024))

							flash.message = "${message(code: 'produto.updated.foto.superior.ao.limite.message', args: [produtoInstance.id, tamanhoMaximoEmKb])}"
						}
						catch(ExtensaoNaoPermitidaException e)
						{	flash.message = "${message(code: 'produto.created.foto.extensao.nao.permitida.message', args: [produtoInstance.id])}"
						}
					}
					else
					{	flash.message = "${message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produtoInstance.id])}"
					}

					if(params.tipos){

						for (Tipo tipo : params.tipos) {
							produtoService.adicionarTipoAProduto(produtoInstance, Tipo.get(tipo))
						}
					}


					redirect(action: "show", id: produtoInstance.id)

				}
				else
				{   pc.id = params.id as long
					pc.version = params.version as long
					pc.errors = produtoInstance.errors
					render(view: "edit", model: [produtoInstance: pc])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def produtoInstance = Produto.get(params.id)
		if (produtoInstance) {
			try {
				produtoInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])}"
			redirect(action: "list")
		}
	}

	void atribuiProdutoAComando(def produtoInstance, def pc)
	{
		pc.id = produtoInstance.id
		pc.version = produtoInstance.version

		pc.dataCadastro = produtoInstance.dataCadastro
		pc.foto = produtoInstance.foto
		pc.itens = produtoInstance.itens
		pc.nome = produtoInstance.nome
		pc.produtoTipos = produtoInstance.produtoTipos
		pc.valor = Util.valorMonetarioToStr(produtoInstance.valor)

	}

	void salvarImagem(String conta)
	{
		/***********************************
		 verifica se o arquivo existe
		 ************************************/

		// CommonsMultipartFile: uma classe do Spring
		CommonsMultipartFile file = request.getFile("foto")

		if (file.size == 0)
		{
			throw new ArquivoInexistenteException()
		}

		/*************************
		 verifica extensões
		 **************************/

		def config = grailsApplication.config.uploadDeArquivo["imagem"]

		println 'grailsApplication.config.uploadDeArquivo["imagem"] = ' + config.getClass().getName()
		println 'file.originalFilename = ' + file.originalFilename

		def extensaoDoArquivo = file.originalFilename.substring(file.originalFilename.lastIndexOf('.')+1)


		if (!config.extensoesPermitidas[0].equals("*"))
		{
			if (!config.extensoesPermitidas.contains(extensaoDoArquivo.toUpperCase()))
			{
				throw new ExtensaoNaoPermitidaException()
			}
		}

		/************************************
		 verifica o tamanho do arquivo
		 *************************************/

		println "config.tamanhoMaximo = " + config.tamanhoMaximo

		if (config.tamanhoMaximo)  // verifica se o valor tamanhoMaximo do objeto config existe
		{
			if (file.size > config.tamanhoMaximo)  // verifica se tamanho do arquivo é maior do que o permitido
			{
				throw new TamanhoDeArquivoSuperiorAoLimiteException()
			}
		}

		String arquivo = "/imagens/" + conta + "." + extensaoDoArquivo;
		String enderecoReal = servletContext.getRealPath(arquivo)

		file.transferTo(new File(enderecoReal))
	}

	void apagarImagem(String conta)
	{
		String arquivo = "/imagens/" + conta + ".jpg";
		String enderecoReal = servletContext.getRealPath(arquivo)
		new File(enderecoReal).delete()
	}
	
	void enviarEmails() {
		def usuarios = Usuario.list()

		runAsync {

			long inicio = System.currentTimeMillis()

			for (int i = 0; i < 100000000; i++)
			{
				// fazendo o tempo passar...
			}

			long fim = System.currentTimeMillis()

			println ">>>>>>>>>>>>>>>>>>>>>> Levou = " + (fim-inicio)/1000 + " segundos"

			usuarios.each {

				def email = it.email
				def nome = it.nome

				mailService.sendMail {
					to email
					from "joana@gmail.com"
					subject "Novo Produto"
					body nome + ", \n Foi criado um novo produto"
				}
			}
		}
		flash.mensagem = "Os e-mails estão sendo enviados!"
	}

}

class ProdutoCommand
{
	Long id
	Long version
	Date dataCadastro
	byte[] foto
	def itens
	String nome
	def produtoTipos
	String valor

	static constraints = {
		dataCadastro(blank: false, nullable: false)
		foto(blank: true, nullable: true)
		nome(blank: false, nullable: false)
		valor(blank: false, validador: {valor -> ValorMonetarioValidador.validaValorMonetario(valor) })

	}
}

