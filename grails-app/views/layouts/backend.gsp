<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<base href="${ grailsApplication.config.grails.serverURL }" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title><g:layoutTitle default="JMAGINE"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
		<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">

		<asset:stylesheet src="bootstrap.min.css"/>
		<asset:stylesheet src="bootstrap-switch.min.css"/>
		<asset:stylesheet src="backend.css"/>

		<asset:javascript src="mustache.js"/>
		<asset:javascript src="mustache.min.js"/>
		<asset:javascript src="jquery-1.11.2.min.js"/>
		<asset:javascript src="jquery-ui.min.js"/>
		<asset:javascript src="bootstrap.min.js"/>
		<asset:javascript src="bootstrap-switch.min.js"/>
	<script type="text/javascript"
			src="https://maps.googleapis.com/maps/api/js?key=${grailsApplication.config.grails.google_maps_api_key}&libraries=places"></script>

	<script src="https://cdn.ckeditor.com/ckeditor5/24.0.0/classic/ckeditor.js"></script>
	%{--
        <ckeditor:resources/>
    --}%
	<asset:javascript src="jmagineMapPlugin.js"/>
	<asset:javascript src="jmagineImageUploadPlugin.js"/>

		<g:layoutHead/>
	</head>
	<body>
		<div class="modal fade" role="dialog" id="modal" aria-labelledby="gridSystemModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">

					</div>
				</div>
			</div>
		</div>
		<div class="navbar-top">
			<div class="navbar-header">
				<asset:image class="navbar-brand" src="reserve/logo_small.png"/>
			</div>
			<div class="user-dropdown">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle user_nav" data-toggle="dropdown" role="button" aria-expanded="false">
							<div class="icon">
								<span class="caret"></span>
							</div>
							<div class="thumb img-circle">
								<g:jmagineImage type="user" src="${me.thumbnail?.filename}" />
							</div>
							<div class="body">
								<div class="username">${me.username}</div>
								${me.mail}
							</div>
						</a>
						<ul class="dropdown-menu" role="menu">
							<li>
								<g:link controller="users" action="my_account"><g:message code="jmagine.users.my_account"/></g:link>
							</li>
							<li class="divider"></li>
							<li><a href="${ grailsApplication.config.grails.serverURL }/logoff">Logout</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="row">
				<g:render template="/menu" />
				<g:layoutBody/>


			</div>
		</div>

	</body>
</html>
