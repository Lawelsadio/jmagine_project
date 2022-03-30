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

    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main moderator_list">
        <h1 class="page-header"><g:message code="jmagine.moderators.edit"/></h1>

        <div class="row">
            <g:render template="/parcours_menu"/>
            <div class="content">
%{--                <div class="top_actions">--}%
%{--                    <g:link class="btn btn-primary" controller="moderators" action="add" params="${[p_id:parcours.id]}"><g:message code="jmagine.moderators.button.edit"/></g:link>--}%
%{--                </div>--}%
                <table class="table table-striped table-action">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        %{--                    <th scope="col"><g:message code="jmagine.table.utilisateur" /></th>--}%
                        <th scope="col"><g:message code="jmagine.table.utilisateur" /></th>
                        <th scope="col"><g:message code="jmagine.table.role" /></th>
                        <th scope="col"><g:message code="jmagine.table.date" /></th>
                        <th scope="col"><g:message code="jmagine.table.email" /></th>
                        <th scope="col"><g:message code="jmagine.table.statut" /></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ users }" var="user" status="i">
                        <tr>
                            <td scope="row">${i+1}</td>
                            <td class="td-self">${ user.username }</td>
                            <td class="td-self">
                                <g:if test="${ user.getAuthorities()[0].authority == 'ROLE_MOD' }">
                                    <g:message code="jmagine.roles.mod"/>
                                </g:if>
                                <g:elseif test="${ user.getAuthorities()[0].authority == 'ROLE_OP' }">
                                    <g:message code="jmagine.roles.op"/>
                                </g:elseif>
                                <g:elseif test="${ user.getAuthorities()[0].authority == 'ROLE_ADMIN' }">
                                    <g:message code="jmagine.roles.admin"/>
                                </g:elseif>
                            </td>
                            <td class="td-self">${g.formatDate([date:user.dateCreated, type:"datetime", style:"MEDIUM" ])}</td>
                            <td class="td-self">${ user.mail }</td>
                            <td class="td-self">
                                <g:if test="${user.enabled}"><g:message code="jmagine.users.enabled" /></g:if>
                                <g:else><g:message code="jmagine.users.disabled" /></g:else>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                %{--            <g:include view="users/_users_list_light.gsp" />--}%
            </div>
            <div class="row">
                <h4><g:message code="jmagine.moderators.button.edit"/></h4>
                <g:include view="users/_users_list_selectable_light.gsp" />
            </div>
        </div>
    </div>
    <script>
        var parcoursID = ${parcours.id};
    </script>

</body>
</html>