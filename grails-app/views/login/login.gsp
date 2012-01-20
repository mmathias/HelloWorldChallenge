<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="default.login.label" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.paginaLogin.label" /></h1>
            <g:if test="${message}">
                <div class="errors">
                    <ul><li>${message}</li></ul>
                </div>
            </g:if>
            <g:hasErrors bean="${usuario}">
                <div class="errors">
                    <g:renderErrors bean="${usuario}" as="list" />
                </div>
            </g:hasErrors>
            <form method="post" action="${createLink(uri: '/j_spring_security_check')}">
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="conta"><g:message code="usuario.conta.label" default="Conta" /></label>
                                </td>
                                <td valign="top" class="value ">
                                    <g:textField name="j_username" value="" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="senha"><g:message code="usuario.senha.label" default="Senha" /></label>
                                </td>
                                <td valign="top" class="value ">
                                    <g:passwordField name="j_password" value="" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lembrar"><g:message code="usuario.lembrar.label" default="Lembrar" /></label>
                                </td>
                                <td valign="top" class="value ">
									<input type='checkbox' name='_spring_security_remember_me' id='remember_me'
									<g:if test='${hasCookie}'>checked='checked'</g:if> />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="efetuarLogin" value="${message(code: 'default.button.enviar.label', default: 'Enviar')}" /></span>
                </div>
            </form>
        </div>
    </body>
</html>
