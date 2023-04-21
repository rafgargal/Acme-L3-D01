

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.practicum.list.label.code" path="code"/>
	<acme:input-textarea code="authenticated.practicum.list.label.title" path="title"/>
	<acme:input-textarea code="authenticated.practicum.list.label.summary" path="summary"/>
	<acme:input-textarea code="authenticated.practicum.list.label.company" path="company.name"/>
	
</acme:form>
