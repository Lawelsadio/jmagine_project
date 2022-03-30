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
        <h1 class="page-header"><g:message code="jmagine.users.my_account"/></h1>

        <div class="row">
            <div class="content my_account">
                <div class="user_profile">
                    <div class="image img-circle">
                        <g:jmagineImage src="${me.thumbnail?.filename}"/>
                    </div>
                    <div class="body">
                        <div class="username">${me.username}
                        <g:if test="${ me.getAuthorities()[0].authority == 'ROLE_MOD' }">
                            <span class="role"><g:message code="jmagine.roles.mod"/></span>
                        </g:if>
                        <g:elseif test="${ me.getAuthorities()[0].authority == 'ROLE_OP' }">
                            <span class="role"><g:message code="jmagine.roles.op"/></span>
                        </g:elseif>
                        <g:elseif test="${ me.getAuthorities()[0].authority == 'ROLE_ADMIN' }">
                            <span class="role"><g:message code="jmagine.roles.admin"/></span>
                        </g:elseif>
                        </div>
                        <div class="email">${me.mail}</div>
                    </div>
                </div>

                <g:if test="${me.moderatedParcours?.size()}">
                    <h4><g:message code="jmagine.my_account.moderated_parcours"/></h4>
                    <div class="parcours_list">
                        <g:each in="${me.moderatedParcours}" var="${parcours}">
                            <g:link controller="parcours" action="edit" params="${[p_id:parcours.id]}">
                            <div class="parcours">
                                <div class="image">
                                    <g:jmagineImage type="background" src="${parcours.backgroundPic?.filename}"/>
                                </div>
                                <div class="body">
                                    <div class="heading">
                                        ${parcours.title}
                                    </div>
                                    ${parcours.description}
                                </div>
                            </div>
                            </g:link>
                        </g:each>
                    </div>
                </g:if>
                <g:else>
                    <g:message code="jmagine.my_account.no_moderated_parcours"/>
                </g:else>
                <g:link class="edit_me btn btn-primary" controller="users" action="edit_me"><g:message code="jmagine.users.button.edit_me"/></g:link>
            </div>
        </div>
    </div>
</body>
</html>