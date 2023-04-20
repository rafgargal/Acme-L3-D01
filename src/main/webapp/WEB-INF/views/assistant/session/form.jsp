<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="assistant.session.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.session.form.label.sAbstract" path="sAbstract"/>
	<acme:input-moment code="assistant.session.form.label.startDateTime" path="startDateTime"/>
	<acme:input-moment code="assistant.session.form.label.endDateTime" path="endDateTime"/>
	<acme:input-url code="assistant.session.form.label.furtherInfo" path="furtherInformation"/>

	<acme:input-select code="assistant.session.form.label.type" path="type" choices="${types}"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="assistant.session.form.submit.save" action="/assistant/session/create?tutorialId=${ tutorialId }"/>
	</jstl:if>
	<jstl:if test="${_command != 'create' && canDeleteOrUpdateSession}">
		<acme:submit code="assistant.session.form.submit.delete" action="/assistant/session/delete"/>
		<acme:submit code="assistant.session.form.submit.update" action="/assistant/session/update"/>
	</jstl:if>
	
</acme:form>