<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>

	<acme:input-textbox code="auditor.audit.label.code" path="code"/>
	
	<acme:input-textarea code="auditor.audit.form.label.conclusion" path="conclusion"/>
	
	<acme:input-textarea code="auditor.audit.form.label.weakPoints" path="weakPoints"/>
	
	<acme:input-textarea code="auditor.audit.form.label.strongPoints" path="strongPoints"/>
	
	<acme:input-textbox code="auditor.audit.form.label.mark" path="mark" readonly="true"/>
	
	<acme:input-select code="auditor.audit.form.label.course" path="course" choices="${courses}"/>
	
	<acme:input-textbox code="auditor.audit.form.label.draftMode" path="draftMode" readonly="true"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="auditor.audit.button.create" action="/auditor/audit/create"/>		
	</jstl:if>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="auditor.audit.button.update" action="/auditor/audit/update"/>
		<acme:submit code="auditor.audit.button.delete" action="/auditor/audit/delete"/>		
		<acme:submit code="auditor.audit.button.publish" action="/auditor/audit/publish"/>		
	</jstl:if>
	
	
	
</acme:form>
