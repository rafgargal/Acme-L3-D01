<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	
	<acme:input-textbox code="lecturer.lectureCourse.label.course" path="courseCode" readonly="true"/>
	<jstl:if test="${_command=='add'}">
		<acme:input-select code="lecturer.lectureCourse.label.lecture" path="lecture" choices="${lectures}"/>
	</jstl:if>
	<jstl:if test="${_command=='addNew'}">
		<acme:input-select code="lecturer.lectureCourse.label.lecture" path="lecture" choices="${lectures}"/>
	</jstl:if>
	
	<acme:submit test="${_command=='add'}" code="lecturer.lectureCourse.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>
	<acme:button code="lecturer.lectureCourse.button.create" action="/lecturer/lecture/create?courseId=${courseId}"/>
	
</acme:form>
