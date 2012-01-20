

<%@ page import="modelo.Usuario" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'usuario.label.plural', 
		       default: 'Usuario')}" />
        
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>

            <span class="menuButton">
                <g:link class="list" action="list">
                    <g:message code="default.list.label" args="[entityNamePlural]" />
                </g:link>
            </span>

            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            
            <g:hasErrors bean="${usuarioInstance}" field="version">
                <div class="errors">
				   	<g:renderErrors bean="${usuarioInstance}" field="version" as="list" />
                </div>
            </g:hasErrors>
            
            <g:form method="post" >
                <g:hiddenField name="id" value="${usuarioInstance?.id}" />
                <g:hiddenField name="version" value="${usuarioInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="nome"><g:message code="usuario.nome.label" default="Nome" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'nome', 'errors')}">
                                    <g:textField name="nome" maxlength="70" value="${usuarioInstance?.nome}" />

                                    <g:eachError bean="${usuarioInstance}" field="nome">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endereco"><g:message code="usuario.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" maxlength="70" value="${usuarioInstance?.email}" />

                                    <g:eachError bean="${usuarioInstance}" field="email">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="conta"><g:message code="usuario.conta.label" default="Conta" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'conta', 'errors')}">
                                    <g:textField name="conta" maxlength="8" value="${usuarioInstance?.conta}" />

                                    <g:eachError bean="${usuarioInstance}" field="conta">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="senha"><g:message code="usuario.senha.label" default="Senha" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'senha', 'errors')}">
                                    <g:textField name="senha" value="${usuarioInstance?.senha}" />

                                    <g:eachError bean="${usuarioInstance}" field="senha">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="enabled"><g:message code="usuario.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${usuarioInstance?.enabled}" />

                                    <g:eachError bean="${usuarioInstance}" field="enabled">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dataCadastro"><g:message code="usuario.dataCadastro.label" default="Data Cadastro" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'dataCadastro', 'errors')}">
                                    <g:datePicker name="dataCadastro" precision="day" value="${usuarioInstance?.dataCadastro}"  />

                                    <g:eachError bean="${usuarioInstance}" field="dataCadastro">
                                        <font style="color: red;">
                                            <g:message error="${it}"/>
                                        </font>
                                    </g:eachError>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="authorities"><g:message code="usuario.authorities.label" default="Authorities" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: usuarioInstance, field: 'authorities', 'errors')}">
                                    <g:select name="authorities" from="${modelo.Role.list()}" multiple="yes" optionKey="id" size="5" value="${usuarioInstance?.authorities*.id}" />

                                    <g:eachError bean="${usuarioInstance}" field="authorities">
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
