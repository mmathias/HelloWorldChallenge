
<%@ page import="modelo.Produto" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
        
		<g:set var="entityNamePlural" 
		       value="${message(code: 'produto.label.plural', 
		       default: 'Produto')}" />
        
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: produtoInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.nome.label" default="Nome" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: produtoInstance, field: "nome")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.dataCadastro.label" default="Data Cadastro" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${produtoInstance?.dataCadastro}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.valor.label" default="Valor" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: produtoInstance, field: "valor")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.foto.label" default="Foto" /></td>

							<td valign="top" style="text-align: left;" class="value">
 				    			<g:set var="valor" value="${Math.random()}" />
		   		                <img height="126" id="foto" src="${createLinkTo(dir: 'imagens', file: produtoInstance.nome+ '.jpg')}?${valor}" alt=""/>

                            </td>
                            
                        </tr>
                    
<%--                        <tr class="prop">--%>
<%--                            <td valign="top" class="name"><g:message code="produto.itens.label" default="Itens" /></td>--%>
<%--                            --%>
<%--                            <td valign="top" style="text-align: left;" class="value">--%>
<%--                                <ul>--%>
<%--                                <g:each in="${produtoInstance.itens}" var="i">--%>
<%--                                    <li><g:link controller="item" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>--%>
<%--                                </g:each>--%>
<%--                                </ul>--%>
<%--                            </td>--%>
<%--                            --%>
<%--                        </tr>--%>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="produto.produtoTipos.label" default="Produto Tipos" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${produtoInstance.produtoTipos}" var="p">
                                    <li>${p?.encodeAsHTML()}</li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${produtoInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
