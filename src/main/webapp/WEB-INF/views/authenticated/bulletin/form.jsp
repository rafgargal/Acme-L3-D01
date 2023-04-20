<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="authenticated.bulletin.form.label.moment" path="moment"/>	
	<acme:input-textbox code="authenticated.bulletin.form.label.title" path="title"/>
	<acme:input-textarea code="authenticated.bulletin.form.label.message" path="message"/>
	<acme:input-textbox code="authenticated.bulletin.form.label.critical" path="critical"/>
	<acme:input-url code="authenticated.bulletin.form.label.moreInfoLink" path="moreInfoLink"/>
</acme:form>
