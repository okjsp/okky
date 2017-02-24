<%@ page import="net.okjsp.Banner" %>



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
</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="banner.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${banner?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'type', 'error')} required">
	<label for="type">
		<g:message code="banner.type.label" default="Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="type" from="${net.okjsp.BannerType?.values()}" keys="${net.okjsp.BannerType.values()*.name()}" required="" value="${banner?.type?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'url', 'error')} required">
	<label for="url">
		<g:message code="banner.url.label" default="Url" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="url" required="" value="${banner?.url}" />

</div>

<div class="fieldcontain ${hasErrors(bean: banner, field: 'visible', 'error')} ">
	<label for="visible">
		<g:message code="banner.visible.label" default="Visible" />
		
	</label>
	<g:checkBox name="visible" value="${banner?.visible}" />

</div>

