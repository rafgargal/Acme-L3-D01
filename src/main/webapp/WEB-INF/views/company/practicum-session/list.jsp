<%@ page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="company.practicum.practicumSession.list.label.title" path="title"/>
	<acme:list-column code="company.practicum.practicumSession.list.label.summary" path="summary"/>
</acme:list>
<acme:button code="company.practicum.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>