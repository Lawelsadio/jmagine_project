<div class="body">
    <div class="username" data-user-id="${user.id}">
        ${ user.username }
        <g:if test="${ user.getAuthorities()[0].authority == 'ROLE_MOD' }">
            <span class="role"><g:message code="jmagine.roles.mod"/></span>
        </g:if>
        <g:elseif test="${ user.getAuthorities()[0].authority == 'ROLE_OP' }">
            <span class="role"><g:message code="jmagine.roles.op"/></span>
        </g:elseif>
        <g:elseif test="${ user.getAuthorities()[0].authority == 'ROLE_ADMIN' }">
            <span class="role"><g:message code="jmagine.roles.admin"/></span>
        </g:elseif>
        <g:if test="${user.enabled}"><span class="badge"><g:message code="jmagine.users.enabled" /></span></g:if>
        <g:else><span class="badge"><g:message code="jmagine.users.disabled" /></span></g:else>
    </div>
    <div class="date">
        <g:message code="jmagine.users.created_on" args="${[g.formatDate([date:user.dateCreated, type:"datetime", style:"MEDIUM" ])]}"/>
    </div>
    <div class="rest">
        <i class="glyphicon glyphicon-envelope"></i> ${ user.mail }
    </div>
</div>