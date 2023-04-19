<%@ page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<jstl:if test = "${addendumCheck == true}">
	<acme:list>
		<acme:list-column code="company.practicum.practicumSession.list.label.title" path="title"/>
		<acme:list-column code="company.practicum.practicumSession.list.label.summary" path="summary"/>
		<acme:list-column code="company.practicum.practicumSession.list.label.addendum" path="addendum"/>
	</acme:list>
</jstl:if>
<jstl:choose>
		<jstl:when test="${draftMode == true}">	
			<acme:button code="company.practicum.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
		
		<jstl:when test="${draftMode == false && addendumCheck == false}">	
			<acme:button code="company.practicum.list.button.createAddendum" action="/company/practicum-session/addendum?masterId=${masterId}"/>
		</jstl:when>
</jstl:choose>
	