<%--
  Created by IntelliJ IDEA.
  User: mahefarazonarison
  Date: 16/07/2021
  Time: 10:57
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="login"/>
    <asset:javascript src="mustache.js"/>
    <asset:javascript src="mustache.min.js"/>
    <asset:javascript src="jquery-1.11.2.min.js"/>
    <asset:javascript src="jquery-ui.min.js"/>
    <asset:javascript src="bootstrap.min.js"/>
    <asset:javascript src="bootstrap-switch.min.js"/>
    <asset:javascript src="jmagineImageUploadPlugin.js"/>
    <title></title>
</head>

<body>
<div class="container">
<g:form autocomplete="off" controller="users" action="do_add" enctype="multipart/form-data" class="form-signin">
    <g:if test="${!flash.errors?.username}">
        <h1 class="page-header"><g:message code="jmagine.users.new"/></h1>
        <label for="username"><g:message code="jmagine.users.username"/></label>
        <input class="form-control" value="${ flash.fields?.username }" name="username" id="username" placeholder="<g:message code="jmagine.users.username"/>">
    </g:if>
    <g:else>
        <div class="form-group has-error has-feedback">
            <label for="username"><g:message code="jmagine.users.username"/></label>
            <input aria-describedby="title_error" value="${ flash.fields?.username }" class="form-control" name="username" id="username" placeholder="<g:message code="jmagine.users.username"/>">
            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
            <span id="title_error" class="sr-only">(error)</span>
            <p class="help-block">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <g:if test="${flash.errors?.username=='unavailable'}"><g:message code="jmagine.users.form.errors.username.unavailable"/></g:if>
                <g:else><g:message code="jmagine.users.form.errors.username.empty"/></g:else>
            </div>
            </p>
        </div>
    </g:else>
    <g:if test="${!flash.errors?.password}">
        <label for="password"><g:message code="jmagine.users.password"/></label>
        <input class="form-control" name="password" id="password" type="password" placeholder="<g:message code="jmagine.users.password"/>">
        <p class="help-block">
%{--            TODO : Réactiver les mails--}%
%{--            <g:message code="jmagine.users.password_helper"/>--}%
        </p>
    </g:if>
    <g:else>
        <div class="form-group has-error has-feedback">
            <label for="password"><g:message code="jmagine.users.password"/></label>
            <input class="form-control" name="password" id="password" type="password" placeholder="<g:message code="jmagine.users.password"/>">
            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
            <span id="password_error" class="sr-only">(error)</span>
            <p class="help-block">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <g:message code="jmagine.users.form.errors.password.invalid"/>
            </div>
            </p>
        </div>
    </g:else>
    <g:if test="${!flash.errors?.email}">
        <div class="form-group">
            <label for="email"><g:message code="jmagine.users.email"/></label>
            <input class="form-control" value="${ flash.fields?.email }" name="email" id="email" placeholder="<g:message code="jmagine.users.email"/>">
        </div>
    </g:if>
    <g:else>
        <div class="form-group has-error has-feedback">
            <label for="email"><g:message code="jmagine.users.email"/></label>
            <input aria-describedby="email_error" value="${ flash.fields?.email }" class="form-control" name="email" id="email" placeholder="<g:message code="jmagine.users.email"/>">
            <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
            <span id="email_error" class="sr-only">(error)</span>
            <p class="help-block">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <g:if test="${flash.errors?.email=='unavailable'}"><g:message code="jmagine.users.form.errors.email.unavailable"/></g:if>
                <g:elseif test="${flash.errors?.email=='invalid'}"><g:message code="jmagine.users.form.errors.email.invalid"/></g:elseif>
                <g:else><g:message code="jmagine.users.form.errors.email.empty"/></g:else>
            </div>
            </p>
        </div>
    </g:else>

        <div class="user_image_uploader"></div>
        <input type="hidden" name="image_type" id="image_type" value="default"/>
        <input type="hidden" name="image_id" id="image_id" value=""/>
        <a href="/auth">Se connecter</a>
        <button type="submit" class="btn btn-lg btn-primary btn-block"><g:message code="jmagine.form.submit"/></button>
    </g:form>
</div>
<g:render template="/mustache_templates/user_image_uploader"/>
<script>
    $( function(){
        function updateInputs( data ) {
            $('#image_type').val( data.type );
        };

        $('.user_image_uploader').imageUploader({
            allow_browse_gallery:false,
            allow_delete_image:true,
            allow_upload:true,
            uid:1,
            file_upload_name_and_id:'profile_picture',
            size:{width:200,height:200},
            onChange:updateInputs
        });
    });
</script>
</body>
</html>