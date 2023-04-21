<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.code" path="code"/>	
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.cAbstract" path="cAbstract"/>
	<acme:input-textbox code="any.course.form.label.draftMode" path="draftMode"/>
	<acme:input-money code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="any.course.form.label.furtherInfo" path="furtherInfo"/>
</acme:form>
<jstl:if test="${anonymous == false}">
<acme:button code="authenticated.practicum.form.label.practicums" action="/authenticated/practicum/list?id=${id}"/>
</jstl:if>
