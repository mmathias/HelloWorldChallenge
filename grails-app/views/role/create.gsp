

<%@ page import="modelo.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
       
		<g:set var="entityNamePlural" 
		       value="${message(code: 'role.label.plural', 
		       default: 'Role')}" />
       
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton">
                <g:link class="list" action="list">
                    <g:message code="default.list.label" args="[entityNamePlural]" />
                </g:link>
            </span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="authority"><g:message code="role.authority.label" default="Authority" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'authority', 'errors')}">
                                    <g:textField name="authority" value="${roleInstance?.authority}" />
									
                                    <g:hasErrors bean="${roleInstance}" field="authority">
                                        <g:eachError bean="${roleInstance}" field="authority">
                                            <font style="color: red;">
                                                <g:message error="${it}"/>
                                            </font>
                                        </g:eachError>
                                    </g:hasErrors>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="role.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: roleInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${roleInstance?.description}" />
									
                                    <g:hasErrors bean="${roleInstance}" field="description">
                                        <g:eachError bean="${roleInstance}" field="description">
                                            <font style="color: red;">
                                                <g:message error="${it}"/>
                                            </font>
                                        </g:eachError>
                                    </g:hasErrors>
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
