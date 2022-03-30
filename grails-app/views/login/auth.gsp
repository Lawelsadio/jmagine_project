<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 13/04/2015
  Time: 14:55
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="login"/>
    <title></title>
</head>

<body>
    <div class="container">
        <form method="POST" action="/login/authenticate" class="form-signin">
            <h2 class="form-signin-heading"><g:message code="jmagine.login_form.header"/></h2>
            <g:if test="${error}"><div class="alert alert-danger" role="alert"><g:message code="jmagine.login_form.login_error"/></div></g:if>
            <label for="username" class="sr-only"><g:message code="jmagine.login_form.username"/></label>
            <input type="text" name="username" id="username" class="form-control" placeholder="<g:message code="jmagine.login_form.username"/>" required autofocus>
            <label for="j_password" class="sr-only"><g:message code="jmagine.login_form.password"/></label>
            <input type="password" id="j_password" name="password" class="form-control" placeholder="<g:message code="jmagine.login_form.password"/>" required>
            <div>
                <div class="checkbox col-md-8">
                    <label>
                        <input type="checkbox" name="remember-me" value="remember-me"> <g:message code="jmagine.login_form.remember_me"/>
                    </label>
                </div>
                <div class="col-md-4" style="margin-top: 10px">
                    <a href="/users/signup">S'inscrire</a>
                </div>
            </div>
            <button class="btn btn-lg btn-primary btn-block" type="submit"><g:message code="jmagine.login_form.sign_in"/></button>
        </form>
    </div>
</body>
</html>