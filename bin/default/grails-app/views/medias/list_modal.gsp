<%--
  Created by IntelliJ IDEA.
  jmagine.User: Lionel
  Date: 01/04/2015
  Time: 17:41
--%>

<div class="media_list">
    <g:each in="${ medias }" var="media">
        <div class="media_item" data-media-id="${media.id}"
             data-media-url="${ grailsApplication.config.grails.assetspath.url + media.filename}">
            <div class="selected_overlay"><i class="glyphicon glyphicon-ok"></i></div>
            <div class="image">
                <img src="${ grailsApplication.config.grails.assetspath.url + media.filename}" />
            </div>
        </div>
    </g:each>
</div>