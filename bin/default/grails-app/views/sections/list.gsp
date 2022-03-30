<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="backend"/>
    <title></title>
</head>

<body>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
    <h1 class="page-header"><g:message code="jmagine.sections.list"/></h1>

    <div class="row">
        <g:render template="/parcours_menu"/>
        <div class="content">
            <div class="top_actions">
                <g:link class="btn btn-primary" controller="sections" action="add" params="${[p_id:parcours.id]}"><g:message code="jmagine.sections.button.new"/></g:link>
            </div>

            <ul id="sortable" class="row sections_list">
                <g:each in="${ sections_list }" var="s" status="section_index">
                    <li class="sections" data-section-id="${s.id}">
                        <div class="background_image">
                            <img src="${ grailsApplication.config.grails.assetspath.url + s.backgroundPic?.filename}" />
                        </div>
                        <div class="handle">
                            <div class="inner">
                                <div class="image"><asset:image src="reserve/poi_handle.png"/></div>
                            </div>
                        </div>
                        <div class="body">
                            <h4 class="heading">${s.title}
                                <div class="time">${s.dateCreated}</div>
                            </h4>
                            <div class="actions">
                                <g:link controller="sections" action="edit" params="${ [p_id:parcours.id,s_id:s.id] }" class="btn btn-primary btn-xs">
                                    <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> <g:message code="jmagine.pois.button.edit"/>
                                </g:link>
                                <g:link controller="sections" action="delete" onclick="return confirm('Êtes vous sûr ?')" params="${ [p_id:parcours.id,s_id:s.id] }" class="btn btn-danger btn-xs">
                                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> <g:message code="jmagine.pois.button.delete"/>
                                </g:link>
                            </div>
                        </div>
                    </li>
                </g:each>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function() {
        var parcoursID = '${parcours.id}';

        //handle:'.class' pour limiter le drag a un sous element
        $("#sortable").sortable({
            placeholder: "ui-state-highlight",
            handle: '.handle',

            update: function (event, ui) {
                $("#sortable li").each(function (i, e) {
                    if (ui.item[0] == e) {
                        // Do ajax call!
//                            console.log( 'Changed '+ui.item.data('poi-id') +' to index '+i )
                        $.ajax('/parcours/' + parcoursID + '/sections/' + ui.item.data('section-id') + '/move_to/' + i).done(function (data) {

                        });
                    }
                });
            }
        });
    });
</script>
</body>
</html>