<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	
	<jstl:if test="${_command == 'create' && courseCode!=null}">
		<acme:input-textbox code="lecturer.lecture.form.label.courseCode" path="courseCode" readonly="true"/>
	</jstl:if>	
	<acme:input-textbox code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textbox code="lecturer.lecture.form.label.lAbstract" path="lAbstract"/>	
	<acme:input-double code="lecturer.lecture.form.label.learningTime" path="learningTime"/>
	<acme:input-textbox code="lecturer.lecture.form.label.body" path="body"/>
	<jstl:if test="${draftMode == false}">
	<acme:input-textbox code="lecturer.lecture.form.label.activityType" path="activityType" readonly="true"/>
	</jstl:if>
	<jstl:if test="${draftMode != false}">
	<acme:input-select code="lecturer.lecture.form.label.activityType" path="activityType" choices="${activityTypes}"/>
	</jstl:if>
	<jstl:if test="${_command != 'create' && _command != 'createFree'}">
	<acme:input-textbox code="lecturer.lecture.form.label.draftMode" path="draftMode" readonly="true"/>
	</jstl:if>

	<acme:submit test="${_command == 'create' && courseCode!=null}" code="lecturer.lecture.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>		
	<acme:submit test="${_command == 'create' && courseCode==null}" code="lecturer.lecture.button.create" action="/lecturer/lecture/create"/>
	<acme:submit test="${_command == 'createFree'}" code="lecturer.lecture.button.create" action="/lecturer/lecture/createFree"/>
	
	<jstl:if test="${_command != 'create' && _command != 'createFree' && draftMode == true }">	
		<acme:submit code="lecturer.lecture.button.update" action="/lecturer/lecture/update"/>
		<acme:submit code="lecturer.lecture.button.delete" action="/lecturer/lecture/delete"/>		
		<acme:submit code="lecturer.lecture.button.publish" action="/lecturer/lecture/publish"/>		
	</jstl:if>
	
</acme:form>
