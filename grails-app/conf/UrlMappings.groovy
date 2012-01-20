class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/recuperaLances/$id"{
			controller = "lance"
			action = "recuperarLancesDeProduto"
		}
		
		// Se você quiser que seus erros sejam tratados por uma action em vez de 
		// diretamente por uma página gsp, isto também é suportado.  Esta action 
		// poderá  registrar  todos  os  erros  404 que estiverem acontecendo no 
		// site.  Segue  abaixo  como  configurar para  que os  erros 404  sejam 
		// tratados por um controlador de erros dedicado:
		
		"404"(controller: "erros", action: "naoEncontrada")
		
		// Veja a action efetuaLog do controlador erros
		// "500"(controller: "erros", action: "efetuaLog")
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
