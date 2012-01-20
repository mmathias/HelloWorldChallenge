

<%@ page import="modelo.Cidade" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cidade.label', default: 'Cidade')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'cidade.label.plural', 
		       default: 'Cidade')}" />
        
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <g:hasErrors bean="${cidadeInstance}" field="version">
                <div class="errors">
				   	<g:renderErrors bean="${cidadeInstance}" field="version" as="list" />
                </div>
            </g:hasErrors>
            
            <g:form method="post" >
                <g:hiddenField name="id" value="${cidadeInstance?.id}" />
                <g:hiddenField name="version" value="${cidadeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nome"><g:message code="cidade.nome.label" default="Nome" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: cidadeInstance, field: 'nome', 'errors')}">
                                    <g:textField name="nome" value="${cidadeInstance?.nome}" />

                                    <g:eachError bean="${cidadeInstance}" field="nome">
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
