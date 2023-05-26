<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	
	<acme:input-textbox code="lecturer.lectureCourse.label.course" path="courseCode" readonly="true"/>
	<jstl:if test="${_command=='add'}">
		<acme:input-select code="lecturer.lectureCourse.label.lecture" path="lecture" choices="${lectures}"/>
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">
		<acme:input-select code="lecturer.lectureCourse.label.lecture" path="lecture" choices="${lectures}"/>
	</jstl:if>
	
	<jstl:if test="${_command=='add'}">
		<acme:submit code="lecturer.lectureCourse.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>
		<acme:button code="lecturer.lectureCourse.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>
	</jstl:if>
	<jstl:if test="${_command == 'delete'}">
		<acme:submit test="${_command == 'delete'}" code="lecturer.lecture.button.delete" action="/lecturer/lecture-course/delete?courseId=${courseId}"/>
	</jstl:if>
	
</acme:form>
