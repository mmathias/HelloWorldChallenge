
<%@ page import="modelo.Item"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'item.label', default: 'Item')}" />

<g:set var="entityNamePlural"
	value="${message(code: 'item.label.plural', 
		       default: 'Item')}" />

<title><g:message code="default.list.label2"
		args="[entityNamePlural]" /></title>

</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /></a></span> <span class="menuButton"><g:link
				class="create" action="create">
				<g:message code="default.new.label" args="[entityName]" />
			</g:link></span>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.list.label2" args="[entityNamePlural]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:form>
			<div class="list">
				<table>
					<thead>
						<tr>

							<g:sortableColumn property="id"
								title="${message(code: 'item.id.label', default: 'Id')}" />

							<g:sortableColumn property="quantidade"
								title="${message(code: 'item.quantidade.label', default: 'Quantidade')}" />

							<th><g:message code="item.produto.label" default="Produto" /></th>

							<th><g:message code="item.pedido.label" default="Pedido" /></th>

							<g:sortableColumn property="enviado"
								title="${message(code: 'item.enviado.label', default: 'Enviado')}" />
							<g:sortableColumn property="aprovado"
								title="${message(code: 'item.aprovado.label', default: 'Aprovado')}" />

						</tr>
					</thead>
					<tbody>
						<g:each in="${itemInstanceList}" status="i" var="itemInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

								<td><g:link action="show" id="${itemInstance.id}">
										${fieldValue(bean: itemInstance, field: "id")}
									</g:link></td>

								<td>
									${fieldValue(bean: itemInstance, field: "quantidade")}
								</td>

								<td>
									${fieldValue(bean: itemInstance, field: "produto")}
								</td>

								<td>
									${fieldValue(bean: itemInstance, field: "pedido.id")}
								</td>
								
								<td>
									${fieldValue(bean: itemInstance, field: "enviado")}
								</td>
								
								<td>
									${fieldValue(bean: itemInstance, field: "aprovado")}
								</td>
								
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="paginateButtons">
				<g:paginate total="${itemInstanceTotal}" />
			</div>
			<div class="buttons">
                <span class="button"><g:actionSubmit class="executarPedido" action="executarPedido" value="Executar Pedido" /></span>
            </div>
		</g:form>
	</div>
</body>
</html>
