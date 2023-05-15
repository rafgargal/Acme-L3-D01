<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<label><acme:message code="assistant.dashboard.form.label.show-tutorials"></acme:message></label>
	<acme:input-integer code="assistant.dashboard.form.label.total-tutorials" path="totalTutorials"/>
	<jstl:if test="${ haveTutorials == true}">
		<acme:input-double code="assistant.dashboard.form.label.tutorials-deviation" path="deviationTutorialTime"/>
		<acme:input-double code="assistant.dashboard.form.label.tutorials-min" path="minimumTutorialTime"/>
		<acme:input-double code="assistant.dashboard.form.label.tutorials-max" path="maximumTutorialTime"/>
		<acme:input-double code="assistant.dashboard.form.label.tutorials-average" path="averageTutorialTime"/>
		
		<jstl:if test="${ haveSessions == true}">
			<label><acme:message code="assistant.dashboard.form.label.show-sessions"></acme:message></label>
			<acme:input-double code="assistant.dashboard.form.label.sessions-deviation" path="deviationSessionTime"/>
			<acme:input-double code="assistant.dashboard.form.label.sessions-min" path="minimumSessionTime"/>
			<acme:input-double code="assistant.dashboard.form.label.sessions-max" path="maximumSessionTime"/>
			<acme:input-double code="assistant.dashboard.form.label.sessions-average" path="averageSessionTime"/>
		</jstl:if>
		<jstl:if test="${ haveSessions != true}">
			<label><acme:message code="assistant.dashboard.form.label.no-sessions"></acme:message></label>
		</jstl:if>
	</jstl:if>
	<jstl:if test="${ haveTutorials != true}">
		<label><acme:message code="assistant.dashboard.form.label.no-tutorials"></acme:message></label>
	</jstl:if>
	
</acme:form>