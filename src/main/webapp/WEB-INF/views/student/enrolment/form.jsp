<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show') && draftMode}">
			<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
		
		
			<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment/publish?id=${id}"/>	
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>

			
			

		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
			<acme:input-select code="student.enrolment.form.label.course" path="course" choices="${courses}"/>
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
			<acme:submit test="${_command == 'create'}" code="student.enrolment.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'publish')}">
			<acme:input-textbox code="student.enrolment.form.label.course" path="course"/>
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
			<acme:input-textbox code="student.enrolment.form.label.holderName" path="holderName"/>
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble" path="lowerNibble"/>
			<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/publish"/>
		
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show')}">
			<acme:input-textbox code="student.enrolment.form.label.course" path="courseCode"/>
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
			
			<acme:button code="student.activity.form.button.activities" action="/student/activity/list?enrolmentId=${id}"/>
		</jstl:when>
		
				
	</jstl:choose>		
</acme:form>
