<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit.list.label.code" path="code" width="20%"/>
	<acme:list-column code="auditor.audit.list.label.course" path="course.title" width="50%"/>
	<acme:list-column code="auditor.audit.list.label.mark" path="mark" width="10%"/>
	<acme:list-column code="auditor.audit.list.label.draftMode" path="draftMode" width="20%"/>
</acme:list>

<acme:button code="auditor.audit.button.create" action="/auditor/audit/create"/>