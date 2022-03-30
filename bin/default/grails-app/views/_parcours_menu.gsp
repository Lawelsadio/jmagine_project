<ul class="nav nav-tabs">
    <li role="presentation" class="<g:if test="${params.controller=='parcours'}">active</g:if>"><g:link controller="parcours" action="edit" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.informations_generales"/></g:link></li>
    <li role="presentation" class="<g:if test="${params.controller=='pois'}">active</g:if>"><g:link controller="pois" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.pois"/></g:link></li>
    <li role="presentation" class="<g:if test="${params.controller=='sections'}">active</g:if>"><g:link controller="sections" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.sections"/></g:link></li>
    <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_OP">
        <li role="presentation" class="<g:if test="${params.controller=='moderators'}">active</g:if>"><g:link controller="moderators" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.moderateurs"/></g:link></li>
    </sec:ifAnyGranted>
    <sec:ifAnyGranted roles="ROLE_MOD">
        <li role="presentation" class="disabled" class="<g:if test="${params.controller=='moderators'}">active</g:if>"><g:link onclick="return false;" controller="moderators" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.moderateurs"/></g:link></li>
    </sec:ifAnyGranted>

    %{-- Two followings have property  onclick="return false;" to disable them, delete when you want to reactivate them --}%
    <li role="presentation" class="disabled <g:if test="${params.controller=='medias'}">active</g:if>"><g:link onclick="return false;" controller="medias" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.medias"/></g:link></li>
    <li role="presentation" class="disabled <g:if test="${params.controller=='comments'}">active</g:if>"><g:link onclick="return false;" controller="comments" action="list" params="${[p_id:parcours.id]}"><g:message code="jmagine.parcours.tabs.commentaires"/></g:link></li>
</ul>
