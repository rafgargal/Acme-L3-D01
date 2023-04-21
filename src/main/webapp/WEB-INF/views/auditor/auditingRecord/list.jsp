<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>	

<acme:list>
	<acme:list-column code="auditor.auditingRecord.list.label.subject" path="subject" width="20%"/>
	<acme:list-column code="auditor.auditingRecord.list.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.auditingRecord.list.label.auditPeriodInicial" path="auditPeriodInicial" width="20%"/>
	<acme:list-column code="auditor.auditingRecord.list.label.auditPeriodFinal" path="auditPeriodFinal" width="20%"/>
	<acme:list-column code="auditor.auditingRecord.list.label.draftMode" path="draftMode" width="20%"/>
</acme:list>