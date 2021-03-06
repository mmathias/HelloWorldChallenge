

<%@ page import="modelo.Produtor" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produtor.label', default: 'Produtor')}" />

        <g:set var="entityNamePlural" value="${message(code: 'produtor.label.plural', default: 'Produtor')}" />

        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityNamePlural]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${produtorInstance}" field="version">
                <div class="errors">
                    <g:renderErrors bean="${produtorInstance}" field="version" as="list" />
                </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${produtorInstance?.id}" />
                <g:hiddenField name="version" value="${produtorInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usuario"><g:message code="produtor.usuario.label" default="Usuario" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produtorInstance, field: 'usuario', 'errors')}">
                                    <g:select name="usuario.id" from="${modelo.Usuario.list()}" optionKey="id" value="${produtorInstance?.usuario?.id}"  />
									
								    <g:eachError bean="${produtorInstance}" field="usuario">
								        <font style="color: red;">
								            <g:message error="${it}"/>
								        </font>
								    </g:eachError>
									
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="produtos"><g:message code="produtor.produtos.label" default="Produtos" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: produtorInstance, field: 'produtos', 'errors')}">
                                    <g:select name="produtos" from="${modelo.Produto.list()}" multiple="yes" optionKey="id" size="5" value="${produtorInstance?.produtos*.id}" />
									
								    <g:eachError bean="${produtorInstance}" field="produtos">
								        <font style="color: red;">
								            <g:message error="${it}"/>
								        </font>
								    </g:eachError>
									
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
