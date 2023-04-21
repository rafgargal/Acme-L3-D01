<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistant.session.list.label.title" path="title"/>
	<acme:list-column code="assistant.session.list.label.abstract" path="sAbstract"/>
	<acme:list-column code="assistant.session.list.label.type" path="type"/>
</acme:list>

<jstl:if test="${ canCreateSession }">
	<acme:button code="assistant.session.list.button.create" action="/assistant/session/create?tutorialId=${tutorialId}"/>
</jstl:if>