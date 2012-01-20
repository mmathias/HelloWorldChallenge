<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <link rel="stylesheet" href="${resource(dir:'menu',file:'menu_style.css')}" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
      	<div id="full">
	        <div id="spinner" class="spinner" style="display:none;">
	            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
	        </div>
	        
	        <div id="grailsLogo">
	        	<a href="http://grails.org">
	        		<img src="${resource(dir:'images',file:'logo.gif')}" alt="Grails" border="0" />
	        	</a>
	        	<font style="font-family: fantasy;font-size: 60px;vertical-align: center;padding-left: 40px;">ENCOMENDAS & VOC&Ecirc;</font>	        
	        </div>
	        
	        <div id="pageBody">
	            <div id="controllerList" class="dialog">
			        <h:toolBar>
			            <h:menu destino="${createLink(uri: '/')}">
				            <span><g:message code="default.home.label" /></span>
				        </h:menu>
	
			            <h:dropDownMenu perfil="ROLE_ADMIN,ROLE_PRODUTOR" label="default.cadastrar.label"> 
		                    <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'usuario', action: 'create')}"><g:message code="usuario.label" /></h:subMenu>
		                    <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'role', action: 'create')}"><g:message code="role.label" /></h:subMenu>
		                    <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'cidade', action: 'create')}"><g:message code="cidade.label" /></h:subMenu>		                    
		                    <h:subMenu perfil="ROLE_PRODUTOR" destino="${createLink(controller: 'produto', action: 'create')}"><g:message code="produto.label" /></h:subMenu>
		                    <h:subMenu perfil="ROLE_PRODUTOR" destino="${createLink(controller: 'tipo', action: 'create')}"><g:message code="tipo.label" /></h:subMenu>
		                    
			            </h:dropDownMenu>
	
			            <h:dropDownMenu perfil="ROLE_ADMIN,ROLE_CLIENTE,ROLE_PRODUTOR" label="default.buscar.label">
			                <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'usuario', action: 'list')}"><g:message code="usuario.label" /></h:subMenu>
			                <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'role', action: 'list')}"><g:message code="role.label" /></h:subMenu>
			                <h:subMenu perfil="ROLE_ADMIN" destino="${createLink(controller: 'cidade', action: 'list')}"><g:message code="cidade.label" /></h:subMenu>
			                <h:subMenu perfil="ROLE_ADMIN,ROLE_PRODUTOR,ROLE_CLIENTE" destino="${createLink(controller: 'produto', action: 'list')}"><g:message code="produto.label" /></h:subMenu>
			                <h:subMenu perfil="ROLE_PRODUTOR,ROLE_CLIENTE" destino="${createLink(controller: 'item', action: 'listaItensEnviados')}"><g:message code="Itens Enviados" /></h:subMenu>
			            </h:dropDownMenu>
			            
			            <h:menu perfil="ROLE_CLIENTE" destino="${createLink(controller: 'item', action: 'list')}">
				            <span><g:message code="pedido.label" /></span>
				        </h:menu>
	
						<g:isLoggedIn>
	                        <h:menu destino="${createLink(controller: 'logout', action: 'index')}">
				                <span><g:message code="default.logout.label" /></span>
				            </h:menu>
			            </g:isLoggedIn>
	
						<g:isNotLoggedIn>
	                        <h:menu destino="${createLink(controller: 'login', action: 'login')}">
				                <span><g:message code="default.login.label" /></span>
				            </h:menu>
			            </g:isNotLoggedIn>
			        </h:toolBar>
	            </div>
	        </div>
	        
	        <g:layoutBody />
        </div>	        
    </body>
</html>