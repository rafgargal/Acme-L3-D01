<%@ page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.practicum.list.label.code" path="code"/>
	<acme:list-column code="authenticated.practicum.list.label.title" path="title"/>
	<acme:list-column code="authenticated.practicum.list.label.summary" path="summary"/>
</acme:list>
