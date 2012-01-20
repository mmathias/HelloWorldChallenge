package tag

import modelo.Usuario

import org.codehaus.groovy.grails.commons.ApplicationHolder

class MenuTagLib 
{
	def authenticateService

	def perfis
	
	static namespace = "h"   // Para podermos utilizar um namespace diferente de g
	
	static HashSet<String> hs = new HashSet<String>()
	
	static
	{
		hs.add("perfil")
		hs.add("label")
		hs.add("class")
		hs.add("href")
		hs.add("action")
	}
			
    def toolBar = { attrs, body ->

//		String conta = authenticateService?.userDomain()?.conta
//		
//		if (conta)
//		{
//			perfis = Usuario.findAll ("from Usuario u left outer join fetch u.authorities where u.conta=?",[conta]).authorities 
//		    println ">>>>>> toolBar >>>>>>>>>>>>> perfis = " + perfis
//			perfis = perfis?.get(0)	
//		}
//		else
//		{	
//			perfis = null
//		} 
//
//		println ">>>>>> toolBar >>>>>>>>>>>>> perfis = " + perfis
				
		out << "<ul class='menu'>" + body() + "</ul>"
    }

	def dropDownMenu = { attrs, body ->
		
		def perfil = attrs.perfil
		def label = message(code: attrs.label)
		
		// if(perfil == null || perfis?.find { it.authority == perfil })

		// perfil tem que ser um String com os nomes dos perfis entre vírgulas.
		// Exemplo: perfil = "ROLE_USUARIO, ROLE_ADMIN"
		if(perfil == null || authenticateService?.ifAnyGranted(perfil)) 
        {	
			out << "<li class='top'><a class='top_link'><span>${label}</span></a>\n"
			out << "<ul class='sub'>\n"
			out << body() + "\n"
			out << "</ul>\n"
			out << "</li>\n"
		}
	}

	def menu = { attrs, body ->

//		def appName = ApplicationHolder.application.metadata['app.name']
//		def serverURL = grailsApplication.config.grails.serverURL
		
		def destino = attrs.destino  
		def perfil = attrs.perfil
		def label = message(code: attrs.label)

		// perfil tem que ser um String com os nomes dos perfis entre vÃ­rgulas.
		// Exemplo: perfil = "ROLE_USUARIO, ROLE_ADMIN"
		if(perfil == null || authenticateService?.ifAnyGranted(perfil)) 
        {	out << "<li class='top'><a class='top_link' "
			outrosAtributos(attrs)
			out << " href='${destino}'>" + body() + "</a></li>"
        }
    }

	def subMenu = { attrs, body ->
		
		def destino = attrs.destino
		def perfil = attrs.perfil
		def label = message(code: attrs.label)

		// perfil tem que ser um String com os nomes dos perfis entre vÃ­rgulas.
		// Exemplo: perfil = "ROLE_USUARIO, ROLE_ADMIN"
		if(perfil == null || authenticateService?.ifAnyGranted(perfil))
		{	out << "<li><a "
			outrosAtributos(attrs)
			out << " href='${destino}'>" + body() + "</a></li>"
		}
	}

	void outrosAtributos(def attrs)
	{
		attrs.each{
			if(!hs.contains(it.key))
			{
				out << it.key + "='" + it.value + "' "
			}
		}
	}
}
