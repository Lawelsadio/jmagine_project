<div class="row users_list">
    <div class="col-xs-12 col-sm-12">
        <g:each in="${ users }" var="user">
            <div class="user">
                <div class="thumb img-circle">
                    <g:jmagineImage src="${user.thumbnail?.filename}"/>
                </div>
                    <g:include view="users/_user_body.gsp" model="[user:user]"/>
            </div>
        </g:each>
    </div>
</div>