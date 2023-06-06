<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>
	<acme:input-textbox code="student.activity.form.label.summary" path="summary"/>
	<acme:input-select code="student.activity.form.label.activityType" path="activityType" choices="${activities}"/>
	<acme:input-moment code="student.activity.form.label.startDate" path="startDate"/>
	<acme:input-moment code="student.activity.form.label.endDate" path="endDate"/>
	<acme:input-url code="student.activity.form.label.moreInfo" path="moreInfo"/>

	<jstl:choose>
		<jstl:when test="${(_command == 'show'||_command == 'update'||_command == 'publish'||_command == 'delete') && draftMode == true}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
			<acme:submit code="student.activity.form.button.publish" action="/student/activity/publish"/>
		</jstl:when>
			
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="student.activity.list.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>
		</jstl:when>
</jstl:choose>	
	
</acme:form>