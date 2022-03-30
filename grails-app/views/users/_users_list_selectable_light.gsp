<div class="row users_list">
    <div class="col-xs-12 col-sm-12">
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
        <g:each in="${ users_toadd }" var="user" status="i">
            <tr class='user selectable <g:if test="${parcours.moderators.contains(user)}">selected</g:if>'>
                <td scope="row" class="username" data-user-id="${user.id}">${i+1}</td>
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
    </div>
</div>
<script type="text/javascript">
    $(function()
    {
        $(".user.selectable").click(function()
        {
            var url;
            if ($(this).hasClass("selected"))
            {
                $(this).removeClass('selected');
                url = '${createLink(controller: 'moderators', action: 'do_remove', params:['p_id':parcours.id,'user_id':"user_id"])}';
                url = url.replace('user_id',$(this).find('.username').data('user-id'));
                location.reload();
            }
            else
            {
                $(this).addClass('selected');
                url = '${createLink(controller: 'moderators', action: 'do_add', params:['p_id':parcours.id,'user_id':"user_id"])}';
                url = url.replace('user_id',$(this).find('.username').data('user-id'));
                location.reload();
            }
            $.ajax(url).always(function(data,status,error)
            {
                popOutModAlert(data,status);
            });

            function popOutModAlert(data,status)
            {
                var div_alert = $('#mod_alert');
                switch (status)
                {
                    case "success":
                        div_alert.html('' +
                        '<div class="alert alert-success" id="success-alert">'+data+'</div>');
                        $("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
                            $("#success-alert").alert('close');
                        });
                        break;
                    case "error":
                        div_alert.html('' +
                        '<div class="alert alert-danger" id="error-alert">'+data.responseText+'</div>');
                        $("#error-danger").fadeTo(2000, 500).slideUp(500, function(){
                            $("#error-danger").alert('close');
                        });
                        break;
                }
            }
        });
    });
</script>