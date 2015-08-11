<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>

<g:if test="${category?.anonymity}">
    %{--<div class="form-group ${hasErrors(bean: article, field: 'title', 'error')} has-feedback">
        <div class="alert alert-info">
            <ul>
                <li><b>블라블라</b> 블라블라</li>
            </ul>
        </div>
    </div>--}%
</g:if>


<g:if test="${!article.id || !article.category.anonymity}">
    <sec:ifAllGranted roles="ROLE_ADMIN">

        <div class="form-group ${hasErrors(bean: article, field: 'choice', 'has-error')} has-feedback">
            <div class="checkbox">
                <label>
                    <g:checkBox name="choice" value="${article?.choice}"  />
                    <g:message code="article.choice.label" default="Editor`s Choice" />
                </label>
            </div>
        </div>

        <div class="form-group ${hasErrors(bean: article, field: 'category', 'has-error')} has-feedback">
            <div>
                <select id="category" name="categoryCode" class="form-control">
                    <option value="">게시판을 선택해 주세요.</option>
                    <g:each in="${categories}" var="category">
                        <option value="${category.code}" <g:if test="${category.code == article?.category?.code}">selected="selected"</g:if>>${message(code: category.labelCode, default: category.defaultLabel)}</option>
                    </g:each>
                </select>
            </div>
        </div>
    </sec:ifAllGranted>

    <sec:ifNotGranted roles="ROLE_ADMIN">
        <g:if test="${categories.size() > 1}">
        <div class="form-group ${hasErrors(bean: article, field: 'category', 'has-error')} has-feedback">
            <div>
                <select id="category" name="categoryCode" class="form-control">
                    <option value="">게시판을 선택해 주세요.</option>
                    <g:each in="${categories}" var="category">
                        <option value="${category.code}"
                                <g:if test="${category.code == article?.category?.code}">selected="selected"</g:if>
                                data-external="${category.writeByExternalLink}"
                                data-anonymity="${category.anonymity}">
                            ${message(code: category.labelCode, default: category.defaultLabel)}
                        </option>
                    </g:each>
                </select>
            </div>
        </div>
        </g:if>
        <g:else>
            <g:hiddenField name="categoryCode" value="${categories?.getAt(0).code}" />
        </g:else>
    </sec:ifNotGranted>
</g:if>

<div class="form-group ${hasErrors(bean: article, field: 'title', 'has-error')} has-feedback">
    <div>
        <g:textField name="title" required="" value="${article?.title}" placeholder="제목을 입력해 주세요." class="form-control"/>
    </div>
</div>

<div class="form-group ${hasErrors(bean: article, field: 'tagString', 'has-error')} has-feedback">
    <div>
        <g:textField name="tagString" value="${article?.tagString}" placeholder="Tags," data-role="tagsinput" class="form-control"/>
    </div>
</div>

<div class="form-group ${hasErrors(bean: article.content, field: 'text', 'has-error')} has-feedback">
    <g:if test="${article?.content?.textType == ContentTextType.MD}">
        <g:textArea name="content.text" id="summernote" value="${markdown.renderHtml([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:if>
    <g:elseif test="${article?.content?.textType == ContentTextType.HTML}">
        <g:textArea name="content.text" id="summernote" value="${filterHtml([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:elseif>
    <g:else>
        <g:textArea name="content.text" id="summernote" value="${lineToBr([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:else>

</div>

<g:hiddenField name="content.textType" value="HTML"/>
<asset:script type="text/javascript">
    $('#summernote').summernote({minHeight: 300, lang: 'ko-KR',
      onInit: function() {
        if($(window).height() > 400)
            $('.note-editable').css('max-height', $(window).height()-100);
      },
      onImageUpload: $.onImageUpload($('#summernote'))
    });

    function postForm() {
        $('textarea[name="content.text"]').val($('#summernote').code());
    }
</asset:script>
