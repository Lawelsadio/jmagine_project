<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>
<g:set var="urlService" bean="friendlyUrlService"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="backend"/>
    <title></title>
</head>

<body>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header"><g:message code="jmagine.parcours.list"/></h1>
        <div class="row parcours_list">
            <div class="col-xs-12 col-sm-12">
                <g:each in="${ parcours_list }" var="p">
                    <div class="parcours">
                        <div class="image">
                            <g:jmagineImage type="background" src="${p.backgroundPic?.filename}" />
                        </div>
                        <div class="body">
                            <h4 class="heading">${p.title}
                                <div class="time">${p.dateCreated}</div>
                                <g:if test="${p.isValidated}"><span class="badge"><g:message code="jmagine.parcours.enabled" /></span></g:if>
                                <g:else><span class="badge"><g:message code="jmagine.parcours.disabled" /></span></g:else>
                            </h4>
                            ${p.description}

                            <div class="counts">
                                <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span> ${p.pois.size()} <span class="spc"></span>
                                <span class="glyphicon glyphicon-comment" aria-hidden="true"></span> ${p.comments.size()} <span class="spc"></span>
                                <span class="glyphicon glyphicon-file" aria-hidden="true"></span> ${p.fileList.size()} <span class="spc"></span>
                                <span class="glyphicon glyphicon-new-window" aria-hidden="true"></span> ${ urlService.sanitizeWithDashes( p.title )+'-'+p.id }
                            </div>
                            <div class="actions">
                                <g:link controller="parcours" action="edit" params="${[p_id:p.id]}" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.edit"/></g:link>
                                <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_OP">
                                    <g:if test="${ !p.isValidated && p.backgroundPic && p.pois.size() }">
                                        <g:link controller="parcours" action="enable" params="${[p_id:p.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.enable"/></g:link>
                                    </g:if>
                                    <g:elseif test="${ !p.isValidated }">
                                        <button class="btn btn-default btn-xs" disabled="disabled"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.enable"/></button>
                                    </g:elseif>
                                    <g:if test="${ p.isValidated }">
                                        <g:link controller="parcours" action="disable" params="${[p_id:p.id]}" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.disable"/></g:link>
                                    </g:if>
                                    <g:link controller="parcours" onclick="return confirm('Êtes vous sûr ?')" action="delete" params="${[p_id:p.id]}" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.delete"/></g:link>
                                </sec:ifAnyGranted>
                                <sec:ifAnyGranted roles="ROLE_MOD">
                                    <g:if test="${ !p.isValidated }">
                                        <button class="btn btn-default btn-xs" disabled="disabled"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.enable"/></button>
                                    </g:if>
                                    <g:else>
                                        <button class="btn btn-default btn-xs" disabled="disabled"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.disable"/></button>
                                    </g:else>
                                    <button class="btn btn-danger btn-xs" disabled="disabled"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <g:message code="jmagine.parcours.button.delete"/></button>
                                </sec:ifAnyGranted>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
        </div>
    </div>

</body>
</html>