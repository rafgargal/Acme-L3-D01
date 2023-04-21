<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.tutorial.list.label.title" path="title" width="50%"/>
	<acme:list-column code="authenticated.tutorial.list.label.course" path="course.title" width="50%"/>
</acme:list>
