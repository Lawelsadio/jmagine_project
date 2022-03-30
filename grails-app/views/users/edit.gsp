<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="backend"/>
    <title></title>
</head>

<body>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header"><g:message code="jmagine.users.edit_user"/></h1>

        <div class="row">
            %{--<g:render template="/parcours_menu_disabled"/>--}%
            <div class="content users">
                <g:form controller="users" action="do_edit" params="${[user_id:user.id]}" enctype="multipart/form-data">
                    <div class="col-xs-4 col-sm-4">
                        <g:if test="${!flash.errors?.username}">
                            <div class="form-group">
                                <label for="username"><g:message code="jmagine.users.username"/></label>
                                <input class="form-control" value="${ flash.fields?.username?flash.fields.username:user.username }" name="username" id="username" placeholder="<g:message code="jmagine.users.username"/>">
                            </div>
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
                            <div class="form-group">
                                <label for="password"><g:message code="jmagine.users.new_password"/></label>
                                <input class="form-control" type="password" name="password" id="password" placeholder="<g:message code="jmagine.users.new_password"/>">
                            </div>
                        </g:if>
                        <g:else>
                            <div class="form-group has-error has-feedback">
                                <label for="password"><g:message code="jmagine.users.new_password"/></label>
                                <input class="form-control" type="password" name="password" id="password" placeholder="<g:message code="jmagine.users.new_password"/>">
                            </div>
                        </g:else>

                        <g:if test="${!flash.errors?.password}">
                            <div class="form-group">
                                <label for="password"><g:message code="jmagine.users.retype_password"/></label>
                                <input class="form-control" type="password" name="verify_password" id="verify_password" placeholder="<g:message code="jmagine.users.retype_password"/>">
                            </div>
                        </g:if>
                        <g:else>
                            <div class="form-group has-error has-feedback">
                                <label for="password"><g:message code="jmagine.users.retype_password"/></label>
                                <input class="form-control" type="password" name="verify_password" id="verify_password" placeholder="<g:message code="jmagine.users.retype_password"/>">
                                <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                                <span id="password_error" class="sr-only">(error)</span>
                                <p class="help-block">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <g:if test="${flash.errors?.password=='missmatch'}">
                                            <g:message code="jmagine.users.form.errors.password.missmatch"/>
                                        </g:if>
                                        <g:else>
                                            <g:message code="jmagine.users.form.errors.password.invalid"/>
                                        </g:else>
                                    </div>
                                </p>
                            </div>
                        </g:else>

                        <g:if test="${!flash.errors?.email}">
                            <div class="form-group">
                                <label for="email"><g:message code="jmagine.users.email"/></label>
                                <input class="form-control" value="${ flash.fields?.email?flash.fields.email:user.mail }" name="email" id="email" placeholder="<g:message code="jmagine.users.email"/>">
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

                        <g:if test="${!flash.errors?.role}">
                            <sec:ifAllGranted roles="ROLE_OP">
                                <div class="form-group">
                                    <label for="role"><g:message code="jmagine.users.role"/></label>
                                    <g:select class="form-control" value="${ flash.fields?.role?flash.fields.role:user.getAuthorities()[0].authority }" name="role" from="${[g.message(code:'jmagine.roles.op'), g.message(code:'jmagine.roles.admin'), g.message(code:'jmagine.roles.mod')]}" keys="${['ROLE_OP', 'ROLE_ADMIN', 'ROLE_MOD']}"/>
                                </div>
                            </sec:ifAllGranted>
                        </g:if>
                        <g:else>
                            <sec:ifAllGranted roles="ROLE_OP">
                                <div class="form-group has-error has-feedback">
                                    <label for="role"><g:message code="jmagine.users.role"/></label>
                                    <select class="form-control" id="role" name="role">
                                        <option value="ROLE_OP"><g:message code="jmagine.roles.op"/></option>
                                        <option value="ROLE_ADMIN"><g:message code="jmagine.roles.admin"/></option>
                                        <option value="ROLE_MOD"><g:message code="jmagine.roles.mod"/></option>
                                    </select>
                                    <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
                                    <span id="role_error" class="sr-only">(error)</span>
                                    <p class="help-block">
                                    <div class="alert alert-danger alert-dismissible" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <g:message code="jmagine.users.form.errors.role.invalid"/>
                                    </div>
                                    </p>
                                </div>
                            </sec:ifAllGranted>
                        </g:else>

                        <div class="user_image_uploader"></div>
                        <input type="hidden" name="image_type" id="image_type" value="default"/>
                        <input type="hidden" name="image_id" id="image_id" value=""/>
                        <button type="submit" class="btn btn-default"><g:message code="jmagine.form.submit"/></button>
                    </div>
                </g:form>
            </div>
        </div>
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
                default_image_src:${ user.thumbnail?.filename?raw('"'+g.jmagineImage( src:user.thumbnail?.filename, makelink:true, absolute:true )+'"'):'null'},
                size:{width:200,height:200},
                onChange:updateInputs
            });
        });
    </script>
</body>
</html>