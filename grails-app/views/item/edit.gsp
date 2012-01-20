

<%@ page import="modelo.Item" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />

        <g:set var="entityNamePlural" value="${message(code: 'item.label.plural', default: 'Item')}" />

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
            <g:hasErrors bean="${itemInstance}" field="version">
                <div class="errors">
                    <g:renderErrors bean="${itemInstance}" field="version" as="list" />
                </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${itemInstance?.id}" />
                <g:hiddenField name="version" value="${itemInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="quantidade"><g:message code="item.quantidade.label" default="Quantidade" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'quantidade', 'errors')}">
                                    <g:textField name="quantidade" value="${fieldValue(bean: itemInstance, field: 'quantidade')}" />
									
								    <g:eachError bean="${itemInstance}" field="quantidade">
								        <font style="color: red;">
								            <g:message error="${it}"/>
								        </font>
								    </g:eachError>
									
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="produto"><g:message code="item.produto.label" default="Produto" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'produto', 'errors')}">
                                    <g:select name="produto.id" from="${modelo.Produto.list()}" optionKey="id" value="${itemInstance?.produto?.id}"  />
									
								    <g:eachError bean="${itemInstance}" field="produto">
								        <font style="color: red;">
								            <g:message error="${it}"/>
								        </font>
								    </g:eachError>
									
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pedido"><g:message code="item.pedido.label" default="Pedido" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: itemInstance, field: 'pedido', 'errors')}">
<%--                                    <g:select name="pedido.id" from="${modelo.Pedido.list()}" optionKey="id" value="${itemInstance?.pedido?.id}"  />--%>
                                    
                                    ${itemInstance?.pedido?.id?.encodeAsHTML()}
									
								    <g:eachError bean="${itemInstance}" field="pedido">
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
