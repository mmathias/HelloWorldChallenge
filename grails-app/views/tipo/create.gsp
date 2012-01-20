

<%@ page import="modelo.Tipo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tipo.label', default: 'Tipo')}" />

        <g:set var="entityNamePlural" value="${message(code: 'tipo.label.plural', default: 'Tipo')}" />

        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityNamePlural]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tipoInstance}" field="version">
                <div class="errors">
                    <g:renderErrors bean="${tipoInstance}" field="version" as="list" />
                </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nome"><g:message code="tipo.nome.label" default="Nome" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tipoInstance, field: 'nome', 'errors')}">
                                    <g:textField name="nome" maxlength="50" value="${tipoInstance?.nome}" />
									
								    <g:eachError bean="${tipoInstance}" field="nome">
								        <font style="color: red;">
								            <g:message error="${it}"/>
								        </font>
								    </g:eachError>
									
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dataCriacao"><g:message code="tipo.dataCriacao.label" default="Data Criacao" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tipoInstance, field: 'dataCriacao', 'errors')}">
                                    <g:datePicker name="dataCriacao" precision="day" value="${tipoInstance?.dataCriacao}"  />
									
								    <g:eachError bean="${tipoInstance}" field="dataCriacao">
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
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
