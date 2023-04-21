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

	<label><acme:message code="assistant.dashboard.form.label.show-tutorials"></acme:message></label>
	<acme:input-integer code="assistant.dashboard.form.label.total-tutorials" path="totalTutorials"/>
	<acme:input-double code="assistant.dashboard.form.label.tutorials-deviation" path="deviationTutorialTime"/>
	<acme:input-double code="assistant.dashboard.form.label.tutorials-min" path="minimumTutorialTime"/>
	<acme:input-double code="assistant.dashboard.form.label.tutorials-max" path="maximumTutorialTime"/>
	<acme:input-double code="assistant.dashboard.form.label.tutorials-average" path="averageTutorialTime"/>
	<label><acme:message code="assistant.dashboard.form.label.show-sessions"></acme:message></label>
	<acme:input-double code="assistant.dashboard.form.label.sessions-deviation" path="deviationSessionTime"/>
	<acme:input-double code="assistant.dashboard.form.label.sessions-min" path="minimumSessionTime"/>
	<acme:input-double code="assistant.dashboard.form.label.sessions-max" path="maximumSessionTime"/>
	<acme:input-double code="assistant.dashboard.form.label.sessions-average" path="averageSessionTime"/>
	
</acme:form>