package modelo

import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach;

class ItemController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def authenticateService

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[itemInstanceList: Item.list(params), itemInstanceTotal: Item.count()]
	}

	def create = {
		def ic = new ItemCommand()
		return [itemInstance: ic]
	}

	def executarPedido = {
		List itens = Item.list(params)
		
		Usuario usuario = Usuario.get(authenticateService.userDomain().id)
		Produtor produtor = Produtor.findByUsuario(usuario)
		
		if (produtor){
			itens.each {
				it.aprovado = true
			}			
		}else{
			Cliente cliente = Cliente.findByUsuario(usuario)
			itens.each {
				it.enviado = true
			}
		}
		
		flash.message = "Pedido executado com sucesso"
		render(view:"list", model:[itemInstanceList: itens, itemInstanceTotal: itens.count()])
	}
	
	def listaItensEnviados = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		
		def lista = new ArrayList<Item>()
		
		Usuario usuario = Usuario.get(authenticateService.userDomain().id)
		Produtor produtor = Produtor.findByUsuario(usuario)
		if (produtor){
			produtor.produtos.each{
				it.itens.each{
					if (it.enviado == true){
						lista.add(it)
					}
				}
			}
		}else{
			Cliente cliente = Cliente.findByUsuario(usuario)
			cliente.pedidos.each{
				it.itens.each{
					if (it.enviado == true){
						lista.add(it)
					}
				}
			}
		}
		
		[itemInstanceList: lista, itemInstanceTotal: lista.count()]
	}


	def save = {
		ItemCommand ic ->

		if(ic.hasErrors())
		{
			render(view: "create", model: [itemInstance: ic])
		}
		else
		{
			Item itemInstance = new Item(params)

			//procurar se ja tem um pedido, se ja tiver adiciona item ao pedido
			Usuario usuario = Usuario.get(authenticateService.userDomain().id)
			Cliente cliente = Cliente.findByUsuario(usuario)

			Pedido pedido = Pedido.findByCliente(cliente)

			if (pedido){
				itemInstance.pedido = pedido
			}else{
				pedido = new Pedido()
				//				pedido.itens = new.add(itemInstance)
				pedido.cliente = cliente

				pedido.save(flush: true)
				itemInstance.pedido = pedido
			}

			println itemInstance.produto
			println itemInstance.pedido

			if (itemInstance.save(flush: true))
			{
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
				redirect(action: "show", id: itemInstance.id)
			}
			else
			{
				ic.errors = itemInstance.errors
				render(view: "create", model: [itemInstance: ic])
			}
		}
	}

	def show = {
		def itemInstance = Item.get(params.id)
		if (!itemInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
			redirect(action: "list")
		}
		else {
			[itemInstance: itemInstance]
		}
	}

	def edit = {
		def itemInstance = Item.get(params.id)
		if (!itemInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [itemInstance: itemInstance]
		}
	}

	def update = {

		ItemCommand ic ->
		def itemInstance = Item.get(params.id)
		if (itemInstance)
		{   if (params.version)
			{   def version = params.version.toLong()
				if (itemInstance.version > version)
				{   ic = new ItemCommand()
					atribuiItemAComando(itemInstance, ic)
					ic.errors.rejectValue("version", "default.optimistic.locking.failure",
							[message(code: 'item.label',
								default: 'Item')] as Object[],
							"Outro usuário atualizou este(a) Item enquanto você estava editando")
					render(view: "edit", model: [itemInstance: ic])
					return
				}
			}
			if (ic.hasErrors())
			{   ic.id = params.id as long
				ic.version = params.version as long
				render(view: "edit", model: [ itemInstance: ic ])
			}
			else
			{
				itemInstance.properties = params

				if (itemInstance.save(flush: true))
				{   flash.message = "${message(code: 'default.updated.message', args: [message(code: 'item.label', default: 'Item'), itemInstance.id])}"
					redirect(action: "show", id: itemInstance.id)
				}
				else
				{   ic.id = params.id as long
					ic.version = params.version as long
					ic.errors = itemInstance.errors
					render(view: "edit", model: [itemInstance: ic])
				}
			}
		}
		else
		{   flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
			redirect(action: "list")
		}
	}

	def delete = {
		def itemInstance = Item.get(params.id)
		if (itemInstance) {
			try {
				itemInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'item.label', default: 'Item'), params.id])}"
			redirect(action: "list")
		}
	}

	void atribuiItemAComando(def itemInstance, def ic)
	{
		ic.id = itemInstance.id
		ic.version = itemInstance.version

		ic.pedido = itemInstance.pedido
		ic.produto = itemInstance.produto
		ic.quantidade = itemInstance.quantidade

	}
}

class ItemCommand
{
	Long id
	Long version
	Pedido pedido
	Produto produto
	String quantidade

	static constraints = {
		pedido()
		produto(blank: false, nullable: false)
		quantidade(blank: false, nullable: false)

	}
}

