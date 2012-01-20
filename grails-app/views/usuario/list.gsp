
<%@ page import="modelo.Usuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'usuario.label.plural', 
		       default: 'Usuario')}" />
        
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'usuario.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nome" title="${message(code: 'usuario.nome.label', default: 'Nome')}" />

                            <g:sortableColumn property="email" title="${message(code: 'usuario.email.label', default: 'Email')}" />
                        
                            <g:sortableColumn property="conta" title="${message(code: 'usuario.conta.label', default: 'Conta')}" />
                        
                            <g:sortableColumn property="senha" title="${message(code: 'usuario.senha.label', default: 'Senha')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usuarioInstanceList}" status="i" var="usuarioInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${usuarioInstance.id}">${fieldValue(bean: usuarioInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "nome")}</td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "email")}</td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "conta")}</td>
                        
                            <td>${fieldValue(bean: usuarioInstance, field: "senha")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${usuarioInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
