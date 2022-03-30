<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>

<%@ page import="jmagine.User" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="backend"/>
    <asset:stylesheet src="mine.css"/>
    <title></title>
</head>

<body>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header"><g:message code="jmagine.users.list"/></h1>
%{--        <g:include view="users/_users_list.gsp" model="[users:users]"/>--}%
        <table class="table table-striped table-action">
            <thead>
                <tr>
                    <th scope="col"></th>
%{--                    <th scope="col"><g:message code="jmagine.table.utilisateur" /></th>--}%
                    <g:sortableColumn property="username" title="${message(code: 'jmagine.table.utilisateur')}" />
                    <th scope="col"><g:message code="jmagine.table.role" /></th>
                    <th scope="col"><g:message code="jmagine.table.date" /></th>
                    <th scope="col"><g:message code="jmagine.table.email" /></th>
                    <g:sortableColumn property="enabled" title="${message(code: 'jmagine.table.statut')}"/>
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
                        <td>
                            <i class="glyphicon glyphicon-eye-open" data-toggle="modal" data-target="#myModal${i}"></i>
                            <sec:ifAllGranted roles="ROLE_OP">
                                <g:link controller="users" action="edit" params="${[user_id:user.id]}"><i class="glyphicon glyphicon-pencil"></i></g:link>
                                <g:if test="${user.enabled == false}">
                                    <g:link controller="users" action="enable" params="${[user_id:user.id]}" class="btn btn-primary btn-xs"><g:message code="jmagine.action.on"/></g:link>
                                </g:if>
                                <g:else>
                                    <g:link controller="users" action="disable" params="${[user_id:user.id]}" class="btn btn-warning btn-xs"><g:message code="jmagine.action.off"/></g:link>
                                </g:else>
                            </sec:ifAllGranted>
%{--                            <sec:ifAllGranted roles="ROLE_OP">--}%
%{--                                <select class="form-control select-role" id="role" name="role">--}%
%{--                                    <option value="ROLE_OP" <g:if test="${ user.getAuthorities()[0].authority == 'ROLE_OP' }">selected</g:if>><g:message code="jmagine.roles.op"/></option>--}%
%{--                                    <option value="ROLE_ADMIN"<g:if test="${ user.getAuthorities()[0].authority == 'ROLE_ADMIN' }">selected</g:if>><g:message code="jmagine.roles.admin"/></option>--}%
%{--                                    <option value="ROLE_MOD"<g:if test="${ user.getAuthorities()[0].authority == 'ROLE_MOD' }">selected</g:if>><g:message code="jmagine.roles.mod"/></option>--}%
%{--                                </select>--}%
%{--                            </sec:ifAllGranted>--}%
%{--                            <sec:ifAllGranted roles="ROLE_OP">--}%
%{--                                <select class="form-control select-role" id="role" name="statut" onchange="javascript:updateStatut()">--}%
%{--                                    <option value="true" <g:if test="${user.enabled == true}">selected</g:if>><g:message code="jmagine.checkbox.on"/></option>--}%
%{--                                    <option value="false"<g:if test="${user.enabled == false}">selected</g:if>><g:message code="jmagine.checkbox.off"/></option>--}%
%{--                                </select>--}%
%{--                            </sec:ifAllGranted>--}%
                        </td>
                    </tr>

                <div class="modal fade" id="myModal${i}" role="dialog">
                    <div class="modal-dialog">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-body">
                                <g:jmagineImage src="${users[i].thumbnail?.filename}" class="wawa"/>
                            </div>
                        </div>
                    </div>
                </div>
            </g:each>
            </tbody>
        </table>
    </div>
</body>
</html>