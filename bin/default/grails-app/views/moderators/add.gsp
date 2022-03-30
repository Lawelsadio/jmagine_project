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
                <div class="top_actions">
                    <div id="mod_alert"></div>
                    <g:link class="btn btn-primary" controller="moderators" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.moderators.list.return"/></g:link>
                </div>
                <g:include view="users/_users_list_selectable_light.gsp" />
            </div>
        </div>
    </div>
    <script>
        var parcoursID = ${parcours.id};
    </script>
</body>
</html>