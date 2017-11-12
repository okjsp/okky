<%@ page import="net.okjsp.Banner" %>



<div class="fieldcontain ${hasErrors(bean: banner, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="banner.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${net.okjsp.BannerType?.values()}" keys="${net.okjsp.BannerType.values()*.name()}" required="" value="${banner?.type?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'contentType', 'error')} required">
	<label for="contentType">
		<g:message code="banner.contentType.label" default="ContentType" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="contentType" from="${net.okjsp.BannerContentType?.values()}" keys="${net.okjsp.BannerContentType.values()*.name()}" required="" value="${banner?.contentType?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="banner.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${banner?.name}"/>

</div>


<g:if test="${banner.contentType == net.okjsp.BannerContentType.IMAGE_FILE}">
<div class="fieldcontain ${hasErrors(bean: banner, field: 'target', 'error')} ">
	<label for="target">
		<g:message code="banner.target.label" default="Target" />
		
	</label>
	<g:textField name="target" value="${banner?.target}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'image', 'error')} required">
	<label for="image">
		<g:message code="banner.image.label" default="Image" />
	</label>

	<input type="file" name="imageFile" id="iamge" />

	<g:if test="${banner?.image}">
		<a href="${banner?.image}" target="_blank">${banner?.image}</a>
	</g:if>

	<div class="fieldcontain ${hasErrors(bean: banner, field: 'url', 'error')} required">
		<label for="url">
			<g:message code="banner.url.label" default="Url" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="url" required="" value="${banner?.url}" />

	</div>
</div>
</g:if>

<g:if test="${banner.contentType == net.okjsp.BannerContentType.IMAGE_URL}">
	<div class="fieldcontain ${hasErrors(bean: banner, field: 'target', 'error')} ">
		<label for="target">
			<g:message code="banner.target.label" default="Target" />

		</label>
		<g:textField name="target" value="${banner?.target}"/>

	</div>

	<div class="fieldcontain ${hasErrors(bean: banner, field: 'image', 'error')} required">
		<label for="image">
			<g:message code="banner.image.label" default="Image" />
		</label>

		<g:textField name="image" value="${banner?.image}"/>
	</div>

	<div class="fieldcontain ${hasErrors(bean: banner, field: 'url', 'error')} required">
		<label for="url">
			<g:message code="banner.url.label" default="Url" />
			<span class="required-indicator">*</span>
		</label>
		<g:textField name="url" required="" value="${banner?.url}" />

	</div>
</g:if>

<g:if test="${banner.contentType == net.okjsp.BannerContentType.TAG}">
	<div class="fieldcontain ${hasErrors(bean: banner, field: 'target', 'error')} ">
		<label for="target">
			<g:message code="banner.tagDesktop.label" default="Tag Desktop" />

		</label>
		<g:textArea name="tagDesktop" value="${banner?.tagDesktop}"/>

	</div>

	<div class="fieldcontain ${hasErrors(bean: banner, field: 'image', 'error')}">
		<label for="image">
			<g:message code="banner.tagMobile.label" default="Tag Mobile" />
		</label>

		<g:textArea name="tagMobile" value="${banner?.tagMobile}"/>
	</div>
</g:if>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'visible', 'error')} ">
	<label for="visible">
		<g:message code="banner.visible.label" default="Visible" />
		
	</label>
	<g:checkBox name="visible" value="${banner?.visible}" />

</div>


<content tag="script">
	<script>
        $('#contentType').change(function() {
          $('#bannerForm').attr('action', location.href)
            .submit();
        });

	</script>
</content>
