<div class="col-sm-3 col-md-2 sidebar">
    <div class="header"><i class="glyphicon glyphicon-map-marker"></i>Parcours</div>
    <ul class="nav nav-sidebar">
        <li><g:link controller="parcours" action="list"><g:message code="jmagine.parcours.list"/></g:link></li>
        <sec:ifAnyGranted roles="ROLE_OP,ROLE_ADMIN">
            <li><g:link controller="parcours" action="add"><g:message code="jmagine.parcours.new"/></g:link></li>
        </sec:ifAnyGranted>
    </ul>
    <sec:ifAnyGranted roles="ROLE_OP,ROLE_ADMIN">
        <div class="header"><i class="glyphicon glyphicon-user"></i>Utilisateurs</div>
        <ul class="nav nav-sidebar">
            <li><g:link controller="users" action="list"><g:message code="jmagine.users.list"/></g:link></li>
            <li><g:link controller="users" action="add"><g:message code="jmagine.users.new"/></g:link></li>
        </ul>
    </sec:ifAnyGranted>
</div>
