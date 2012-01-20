
<%@ page import="modelo.Produto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'produto.label.plural', 
		       default: 'Produto')}" />
        
        <title><g:message code="default.list.label2" args="[entityNamePlural]" />
</title>

    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label2" args="[entityNamePlural]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'produto.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nome" title="${message(code: 'produto.nome.label', default: 'Nome')}" />
                        
                            <g:sortableColumn property="dataCadastro" title="${message(code: 'produto.dataCadastro.label', default: 'Data Cadastro')}" />
                        
                            <g:sortableColumn property="valor" title="${message(code: 'produto.valor.label', default: 'Valor')}" />
                        
                            <g:sortableColumn property="foto" title="${message(code: 'produto.foto.label', default: 'Foto')}" />
                            
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${produtoInstanceList}" status="i" var="produtoInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${produtoInstance.id}">${fieldValue(bean: produtoInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: produtoInstance, field: "nome")}</td>
                        
                            <td><g:formatDate date="${produtoInstance.dataCadastro}" /></td>
                        
                            <td>${fieldValue(bean: produtoInstance, field: "valor")}</td>
                        
                            <td>
                            <g:set var="valor" value="${Math.random()}" />
		   		                <img height="126" id="foto" src="${createLinkTo(dir: 'imagens', file: produtoInstance.nome+ '.jpg')}?${valor}" alt=""/>
                            
                            
                            
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${produtoInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
