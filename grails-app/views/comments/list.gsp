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
    <title></title>
</head>

<body>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header"><g:message code="jmagine.comments.list"/></h1>
        <div class="row comments_list">
            <div class="col-xs-12 col-sm-12">
                <g:each in="${ comments }" var="comment">
                    <div class="comment">
                        ${ comment.dateCreated }
                        <g:if test="${comment.submittedByUserType == jmagine.OwnerType.BACKEND}">
                            %{--Publié par ${ fr.mbds.tokidev.jmagine.jmagine.User.get( comment.submittedByUserId ).id }--}%
                        </g:if>
                        <g:if test="${comment.submittedByUserType == jmagine.OwnerType.MOBILE}">
                            %{--Publié par mobile ID ${ ²comment.submittedByUserId }--}%
                        </g:if>
                    </div>
                </g:each>
            </div>
        </div>
    </div>

</body>
</html>