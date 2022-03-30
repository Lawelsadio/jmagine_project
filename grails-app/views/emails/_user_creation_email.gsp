<html>
<head>
    <meta name="layout" content="null" />
    <style>
    body {
        font: 12px Verdana;
    }
    .main {
        width:600px;
        margin:0 auto;
    }

    .main .logo {
        background:#666;
        padding:25px 20px 20px 20px;
    }

    .link {
        display:inline;
        /*margin:20px 0;*/
        color: #fff;
        background-color: #337ab7;
        border-color: #2e6da4;
        line-height: 18px;
        margin-bottom: 0px;
        padding:5px;
        text-decoration: none;
        vertical-align: middle;
        white-space: nowrap;
        word-wrap: break-word;
    }

    .main .body {
        padding:20px;
    }

    .main .body .informations {
        padding-top:20px;
    }

    </style>
</head>

<body>
<div class="main">
    <div class="logo">
        <asset:image absolute="true" src="reserve/logo_small.png"/>
    </div>
    <div class="body">
        <g:message code="jmagine.emails.user_creation.helper" />
        <div class="informations">
            <div>
                <g:message code="jmagine.emails.user_creation.username" /> : <b>${user.username}</b>
            </div>
            <div>
                <g:message code="jmagine.emails.user_creation.password" /> : <b>${raw_password}</b>
            </div>
        </div>
        <div class="informations">
            <g:message code="jmagine.emails.user_creation.helper_password" />
        </div>
        <div class="informations">
            <g:link class="link" controller="users" absolute="true" action="my_account">
                <g:createLink absolute="true" controller="users" action="my_account"/>
            </g:link>
        </div>
        <div class="informations">
            <g:message code="jmagine.emails.user_creation.helper_footer" />
        </div>
    </div>
</div>
</body>
</html>
