import grails.util.Environment

import java.util.Date

import org.junit.internal.runners.statements.FailOnTimeout;

import modelo.Cidade
import modelo.Cliente
import modelo.Produto
import modelo.ProdutoTipo
import modelo.Produtor
import modelo.Role
import modelo.Tipo
import modelo.Usuario
import modelo.Item
import modelo.Pedido

class BootStrap {
	def authenticateService

	def init = { servletContext ->
		switch (Environment.current) {
			case Environment.DEVELOPMENT:
				popularBancoDeDados()
				break;
			case Environment.TEST:
				popularBancoDeDados()
				break;
			case Environment.PRODUCTION:
				println "Nenhuma configuração especial necessária"
				break;
		}

		servletContext["listaDeCidades"] = Cidade.list()
	}

	def destroy = {
	}

	void popularBancoDeDados() {

		Usuario usuarioAdmin = new Usuario(nome: "Admin",
				email:"magnomathias21@gmail.com",
				conta: "admin",
				senha: authenticateService.encodePassword("admin"),
				enabled: true,
				dataCadastro: new Date()).save(failOnError: true)

		Usuario usuarioNormal = new Usuario(nome: "Usuário",
				email:"magnomathias21@gmail.com",
				conta: "usuario",
				senha: authenticateService.encodePassword("usuario"),
				enabled: true,
				dataCadastro: new Date()).save(failOnError: true)
				
		Usuario usuarioProdutor = new Usuario(nome: "Produtor",
				email:"magnomathias21@gmail.com",
				conta: "produtor",
				senha: authenticateService.encodePassword("produtor"),
				enabled: true,
				dataCadastro: new Date()).save(failOnError: true)
				
		Usuario usuarioCliente = new Usuario(nome: "Cliente", 
				email:"magnomathias21@gmail.com",
				conta: "cliente",
				senha: authenticateService.encodePassword("cliente"),
				enabled: true,
				dataCadastro: new Date()).save(failOnError: true)
				
		Produtor produtor = new Produtor(usuario: usuarioProdutor).save(failOnError: true)
		Cliente cliente = new Cliente(usuario: usuarioCliente).save(failOnError: true)		

		Role roleUsuario = new Role(description: "Perfil de usuário", authority: "ROLE_USER").save(failOnError: true)
		Role roleAdmin = new Role(description: "Perfil de administrador", authority: "ROLE_ADMIN").save(failOnError: true)
		Role roleCliente= new Role(description: "Perfil de cliente", authority: "ROLE_CLIENTE").save(failOnError: true)
		Role roleProdutor = new Role(description: "Perfil de produtor", authority: "ROLE_PRODUTOR").save(failOnError: true)

		usuarioAdmin.addToAuthorities(roleUsuario)
		usuarioAdmin.addToAuthorities(roleAdmin)
		usuarioNormal.addToAuthorities(roleUsuario)		 
		usuarioProdutor.addToAuthorities(roleProdutor)
		usuarioCliente.addToAuthorities(roleCliente)
		
		Produto paoDeBatata = new Produto(valor: 123.00, nome: "Pao de batata", dataCadastro: new Date(), produtor: produtor).save(failOnError: true)
		Produto boloDeCenoura = new Produto(valor: 333.00, nome: "Bolo de cenoura", dataCadastro: new Date(), produtor: produtor).save(failOnError: true)
		Produto croassaintDeChocolate = new Produto(valor: 20.00, nome: "Croassaint de chocolate", dataCadastro: new Date(), produtor: produtor ).save(failOnError: true)
		Produto paoIntegral= new Produto(valor: 13.00, nome: "Pao integral", dataCadastro: new Date(), produtor: produtor).save(failOnError: true)
		Produto tortaAmendoim = new Produto(valor: 23.00, nome: "Torta de amendoim", dataCadastro: new Date(), produtor: produtor).save(failOnError: true)
		
		
		Tipo doce = new Tipo(nome:"Doce").save(failOnError: true)
		Tipo salgado = new Tipo(nome:"Salgado").save(failOnError: true)
		Tipo confeitado = new Tipo(nome:"Confeitado").save(failOnError: true)
	
		ProdutoTipo produtoTipo1 = new ProdutoTipo(produto:paoDeBatata, tipo: salgado).save(failOnError: true)
		ProdutoTipo produtoTipo2 = new ProdutoTipo(produto:boloDeCenoura, tipo: doce).save(failOnError: true)
		ProdutoTipo produtoTipo3 = new ProdutoTipo(produto:boloDeCenoura, tipo: confeitado).save(failOnError: true)
		ProdutoTipo produtoTipo4 = new ProdutoTipo(produto:croassaintDeChocolate, tipo: doce).save(failOnError: true)
		ProdutoTipo produtoTipo5 = new ProdutoTipo(produto:croassaintDeChocolate, tipo: confeitado).save(failOnError: true)
		ProdutoTipo produtoTipo6 = new ProdutoTipo(produto:paoIntegral, tipo: salgado).save(failOnError: true)
		ProdutoTipo produtoTipo8 = new ProdutoTipo(produto:tortaAmendoim, tipo: doce).save(failOnError: true)
		
		
		Pedido pedido = new Pedido(cliente: cliente).save(failOnError: true)
		
		Item item1 = new Item(enviado: false, aprovado: false, quantidade:12, produto: paoIntegral, pedido: pedido).save(failOnError: true)
		Item item2 = new Item(enviado: false, aprovado: false, quantidade:22, produto: tortaAmendoim, pedido: pedido).save(failOnError: true)
		Item item3 = new Item(enviado: true, aprovado: false, quantidade:43, produto: boloDeCenoura, pedido: pedido).save(failOnError: true)
		Item item4 = new Item(enviado: true, aprovado: false, quantidade:4, produto: croassaintDeChocolate, pedido: pedido).save(failOnError: true)
		Item item5 = new Item(enviado: true, aprovado: false, quantidade:9, produto: paoDeBatata, pedido: pedido).save(failOnError: true)
		
		pedido.addToItens(item1)
		pedido.addToItens(item2)
		pedido.addToItens(item3)
		pedido.addToItens(item4)
		pedido.addToItens(item5)	
		
	}
}
