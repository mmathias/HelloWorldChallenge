
<%@ page import="modelo.Produtor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produtor.label', default: 'Produtor')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'produtor.label.plural', 
		       default: 'Produtor')}" />
        
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'produtor.id.label', default: 'Id')}" />
                        
                            <th><g:message code="produtor.usuario.label" default="Usuario" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${produtorInstanceList}" status="i" var="produtorInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${produtorInstance.id}">${fieldValue(bean: produtorInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: produtorInstance, field: "usuario")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${produtorInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
