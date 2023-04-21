<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.note.form.label.message" path="message"/>
	<acme:input-url code="authenticated.note.form.label.furtherInfo" path="furtherInfo"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:input-checkbox code="authenticated.note.list.button.check" path="check"/>
			<acme:submit code="authenticated.note.form.button.create" action="/authenticated/note/create"/>	 
		</jstl:when>
		
		<jstl:when test="${_command == 'show'}">
			<acme:input-moment code="authenticated.note.form.label.moment" path="moment"/>
			<acme:input-textbox code="authenticated.note.form.label.author" path="author"/>
			<acme:input-textbox code="authenticated.note.form.label.email" path="email"/>
			
		</jstl:when>
	</jstl:choose>

</acme:form>