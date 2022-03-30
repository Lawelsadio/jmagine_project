// See src/main/resources/config for external config files

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'jmagine.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'jmagine.UserRole'
grails.plugin.springsecurity.authority.className = 'jmagine.Role'
grails.plugin.springsecurity.auth.loginFormUrl = '/auth'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = "/auth?login_error=1"
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"

//grails.plugin.springsecurity.rest.token.storage.useGorm = true
//grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'security.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.jwt.useEncryptedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret



grails.plugin.springsecurity.interceptUrlMap = [

		[pattern: '/',               				access: ['ROLE_ADMIN', 'ROLE_OP', 'ROLE_MOD']],
		[pattern: '/parcours/add', 					access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/parcours/do_add', 				access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/parcours/**/moderators/**', 	access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/parcours/**', 					access: ['ROLE_ADMIN', 'ROLE_OP', 'ROLE_MOD']],
		[pattern: '/pois/**', 					access: ['permitAll']],
		[pattern: '/sections/**', 					access: ['permitAll']],


		[pattern: '/my_account/**', 				access: ['ROLE_ADMIN', 'ROLE_OP', 'ROLE_MOD']],
		//[pattern: '/users/**', 						access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users',							access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/signup',							access: ['permitAll']],
		[pattern: '/users/add',  					access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/**/edit',					access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/**/do_edit',				access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/**/enable',				access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/**/disable',				access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/users/user_id/delete',			access: ['ROLE_ADMIN', 'ROLE_OP']],
		[pattern: '/qrcode/**', 					access: ['ROLE_ADMIN', 'ROLE_OP', 'ROLE_MOD']],
		[pattern: '/api/', 						access: ['permitAll']],
		[pattern: '/error', 						access: ['permitAll']],
		[pattern: '/index', 						access: ['permitAll']],
		[pattern: '/index.gsp',						access: ['permitAll']],
		[pattern: '/shutdown', 						access: ['permitAll']],
		[pattern: '/auth', 							access: ['permitAll']],
		[pattern: '/page_not_found', 				access: ['permitAll']],
		[pattern: '/page_forbidden', 				access: ['permitAll']],
		[pattern: '/login', 						access: ['permitAll']],
		[pattern: '/h2-console/**', 				access: ['permitAll']],
		[pattern: '/users/signup',					access: ['permitAll']],
		[pattern: '/users/do_add',					access: ['permitAll']],
		[pattern: '/static/api/**',					access: ['permitAll']],
		[pattern: '/api/doc/**',					access: ['permitAll']],
		[pattern: '/api/parcours/**',access: ['ROLE_ADMIN']],
]

grails.plugin.springsecurity.filterChain.chainMap = [
	//	[ pattern: '/api/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
		[pattern: '/assets/**', filters: 'none'],
		[pattern: '/node_modules/**', filters: 'none'],
		[pattern: '/**/js/**', filters: 'none'],
		[pattern: '/**/css/**', filters: 'none'],
		[pattern: '/**/images/**', filters: 'none'],
		[pattern: '/**/favicon.ico', filters: 'none'],
		[pattern: '/fonts/**', filters: 'none'],
		[pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
//grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'
grails.plugin.springsecurity.rest.token.storage.memcached.hosts = 'localhost:11211'
grails.plugin.springsecurity.rest.token.storage.memcached.username = ''
grails.plugin.springsecurity.rest.token.storage.memcached.password = ''
grails.plugin.springsecurity.rest.token.storage.memcached.expiration = 86400

ckeditor {
	skipAllowedItemsCheck = false
	defaultFileBrowser = "ofm"
	config = "/ck/ckconfig.gsp"
	upload {

		basedir = "/uploads/img"
//        baseurl = "http://jmagine.tokidev.fr/uploads/wsw/"
//        baseurl = "http://localhost/test/"
//        basedir = "/var/www/jmagine/uploads/wsw/"
//        basedir = "F:/Programmes/EasyPHP-13.1VC11/data/localweb/"
//        overwrite = false
//        link {
//            browser = true
//            upload = false
//            allowed = []
//            denied = ['html', 'htm', 'php', 'php2', 'php3', 'php4', 'php5',
//                      'phtml', 'pwml', 'inc', 'asp', 'aspx', 'ascx', 'jsp',
//                      'cfm', 'cfc', 'pl', 'bat', 'exe', 'com', 'dll', 'vbs', 'js', 'reg',
//                      'cgi', 'htaccess', 'asis', 'sh', 'shtml', 'shtm', 'phtm']
//        }
		image {
			browser = true
//            upload = true
//            allowed = ['jpg', 'gif', 'jpeg', 'png']
//            denied = ['bmp','pcx']
		}
	}
}