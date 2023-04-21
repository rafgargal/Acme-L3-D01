<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="50%"/>
	<acme:list-column code="lecturer.lecture.list.label.learningTime" path="learningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.activityType" path="activityType" width="30%"/>
</acme:list>

<acme:button code="lecturer.lecture.button.add" action="/lecturer/lecture-course/add?courseId=${courseId}"/>