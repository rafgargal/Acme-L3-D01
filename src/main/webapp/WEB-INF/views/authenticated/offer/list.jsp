<%@page language="java"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.offer.list.label.heading" path="heading"/>
	<acme:list-column code="authenticated.offer.list.label.summary" path="summary"/>
</acme:list>

<sec:authorize access="hasRole('Administrator')">
		<acme:button code="authenticated.offer.form.button.create" action="/authenticated/offer/create"/>
</sec:authorize>