<div class="row users_list">
    <div class="col-xs-12 col-sm-12">
        <g:each in="${ users }" var="user">
            <div class="user">
                <div class="thumb img-circle">
                    <g:jmagineImage src="${user.thumbnail?.filename}"/>
                </div>
                <g:include view="users/_user_body.gsp" model="[user:user]"/>
                <div class="actions">
                    <g:link controller="users" action="edit" params="${[user_id:user.id]}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <g:message code="jmagine.users.button.edit"/></g:link>
                    <g:if test="${ !user.enabled }">
                        <g:link controller="users" action="enable" params="${[user_id:user.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> <g:message code="jmagine.users.button.enable"/></g:link>
                    </g:if>
                    <g:else>
                        <g:link controller="users" action="disable" params="${[user_id:user.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> <g:message code="jmagine.users.button.disable"/></g:link>
                    </g:else>
                %{--<g:link controller="users" onclick="return confirm('Êtes vous sûr ?')" action="delete" params="${[user_id:user.id]}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <g:message code="jmagine.users.button.delete"/></g:link>--}%
                </div>
            </div>
        </g:each>
    </div>
</div>