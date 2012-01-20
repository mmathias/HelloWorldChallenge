

<%@ page import="modelo.Produto"%>
<%@ page import="modelo.Tipo"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'produto.label', default: 'Produto')}" />

<g:set var="entityNamePlural"
	value="${message(code: 'produto.label.plural', default: 'Produto')}" />

<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
	<div class="nav">
		<span class="menuButton"><a class="home"
			href="${createLink(uri: '/')}"><g:message
					code="default.home.label" /></a></span> <span class="menuButton"><g:link
				class="list" action="list">
				<g:message code="default.list.label" args="[entityNamePlural]" />
			</g:link></span> <span class="menuButton"><g:link class="create"
				action="create">
				<g:message code="default.new.label" args="[entityName]" />
			</g:link></span>
	</div>
	<div class="body">
		<h1>
			<g:message code="default.edit.label" args="[entityName]" />
		</h1>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:hasErrors bean="${produtoInstance}" field="version">
			<div class="errors">
				<g:renderErrors bean="${produtoInstance}" field="version" as="list" />
			</div>
		</g:hasErrors>
		<g:form method="post" enctype="multipart/form-data">
			<g:hiddenField name="id" value="${produtoInstance?.id}" />
			<g:hiddenField name="version" value="${produtoInstance?.version}" />
			<div class="dialog">
				<table>
					<tbody>

						<tr class="prop">
							<td valign="top" class="name"><label for="nome"><g:message
										code="produto.nome.label" default="Nome" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: produtoInstance, field: 'nome', 'errors')}">
								<g:textField name="nome" maxlength="50"
									value="${produtoInstance?.nome}" /> <g:eachError
									bean="${produtoInstance}" field="nome">
									<font style="color: red;"> <g:message error="${it}" />
									</font>
								</g:eachError>

							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="dataCadastro"><g:message
										code="produto.dataCadastro.label" default="Data Cadastro" /></label>
							</td>
							<td valign="top"
								class="value ${hasErrors(bean: produtoInstance, field: 'dataCadastro', 'errors')}">
								<g:datePicker name="dataCadastro" precision="day"
									value="${produtoInstance?.dataCadastro}" /> <g:eachError
									bean="${produtoInstance}" field="dataCadastro">
									<font style="color: red;"> <g:message error="${it}" />
									</font>
								</g:eachError>

							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="valor"><g:message
										code="produto.valor.label" default="Valor" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: produtoInstance, field: 'valor', 'errors')}">
								<g:textField name="valor" value="${fieldValue(bean: produtoInstance, field: 'valor')}" />

								<g:eachError bean="${produtoInstance}" field="valor">
									<font style="color: red;"> <g:message error="${it}" />
									</font>
								</g:eachError>

							</td>
						</tr>

						<tr class="prop">
							<td valign="top" class="name"><label for="foto">
							<g:message code="produto.foto.label" default="Foto" /></label></td>
							<td valign="top"
								class="value ${hasErrors(bean: produtoInstance, field: 'foto', 'errors')}">
								<input type="file" id="foto" name="foto" src="${fieldValue(bean: produtoInstance, field: 'foto')}"/> 
								<g:eachError bean="${produtoInstance}" field="foto">
									<font style="color: red;"> 
										<g:message error="${it}" />
									</font>
								</g:eachError>
							</td>
						</tr>
						
						<tr class="prop">
							<td valign="top" class="name">
								<label for="tipos">
									<g:message code="produto.tipos.label" default="Tipos" />
								</label>
							</td>
							<td valign="top" class="value ${hasErrors(bean: produtoInstance, field: 'tipos', 'errors')}">							
								<g:select name="tipos" multiple="true" from="${Tipo.list()}" value="${produtoInstance.produtoTipos.tipo.id}" optionKey="id" />								
							</td>
						</tr>

					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button"><g:actionSubmit class="save"
						action="update"
						value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
				<span class="button"><g:actionSubmit class="delete"
						action="delete"
						value="${message(code: 'default.button.delete.label', default: 'Delete')}"
						onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
			</div>
		</g:form>
	</div>
</body>
</html>
