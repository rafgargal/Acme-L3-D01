<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-textbox code="auditor.auditRecord.form.label.subject" path="subject"/>
	<acme:input-textarea code="auditor.auditRecord.form.label.assessment" path="assessment"/>
	<acme:input-textarea code="auditor.auditRecord.form.label.link" path="link"/>	
	<acme:input-textbox code="auditor.auditRecord.form.label.mark" path="mark"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.auditPeriodInicial" path="auditPeriodInicial"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.auditPeriodFinal" path="auditPeriodFinal"/>
	<acme:input-textbox code="auditor.auditRecord.form.label.draftMode" path="draftMode" readonly="true"/>
	
	
  
</acme:form>