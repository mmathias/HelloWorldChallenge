security {
	active = true
	cacheUsers = false
	loginUserDomainClass = "modelo.Usuario"
	authorityDomainClass = "modelo.Role"
	userName = "conta"
	password = "senha"
	useRequestMapDomainClass = false
	loginFormUrl = "/login/login"
	defaultTargetUrl = "/"
	requestMapString = """\
	CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
	PATTERN_TYPE_APACHE_ANT
	/usuario/**=ROLE_ADMIN
	/role/**=ROLE_ADMIN
	/produto/**=ROLE_PRODUTOR, ROLE_ADMIN
	/item/**=ROLE_PRODUTOR, ROLE_ADMIN, ROLE_CLIENTE
	/pedido/**=ROLE_CLIENTE
	/cliente/**=ROLE_CLIENTE, ROLE_ADMIN
	/produtor/**=ROLE_PRODUTOR, ROLE_ADMIN
	/tipo/**=ROLE_ADMIN, ROLE_PRODUTOR


	/=IS_AUTHENTICATED_ANONYMOUSLY
	/login/auth=IS_AUTHENTICATED_ANONYMOUSLY
	/login/login=IS_AUTHENTICATED_ANONYMOUSLY
	/js/**=IS_AUTHENTICATED_ANONYMOUSLY
	/css/**=IS_AUTHENTICATED_ANONYMOUSLY
	/images/**=IS_AUTHENTICATED_ANONYMOUSLY
	/plugins/**=IS_AUTHENTICATED_ANONYMOUSLY
	/menu/**=IS_AUTHENTICATED_ANONYMOUSLY
	/**=IS_AUTHENTICATED_REMEMBERED
	"""
}
