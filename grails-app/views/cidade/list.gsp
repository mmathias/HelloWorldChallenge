
<%@ page import="modelo.Cidade" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cidade.label', default: 'Cidade')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'cidade.label.plural', 
		       default: 'Cidade')}" />
        
        <title><g:message code="default.list.label2" args="[entityNamePlural]" />
</title>

    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label2" args="[entityNamePlural]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'cidade.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="nome" title="${message(code: 'cidade.nome.label', default: 'Nome')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${cidadeInstanceList}" status="i" var="cidadeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${cidadeInstance.id}">${fieldValue(bean: cidadeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: cidadeInstance, field: "nome")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${cidadeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
