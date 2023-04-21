<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.tAbstract" path="tAbstract"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.code" path="code"/>
	<acme:input-double code="authenticated.tutorial.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.goals" path="goals"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.course" path="course.title"/>
	<label style="display: block;">
		<acme:message code="authenticated.tutorial.form.message.assistant"/>
	</label>
	<acme:input-textbox code="authenticated.tutorial.form.label.supervisor" path="assistant.supervisor"/>
	<acme:input-textbox code="authenticated.tutorial.form.label.resume" path="assistant.resume"/>
	<acme:input-textarea code="authenticated.tutorial.form.label.expertise-fields" path="assistant.expertiseFields"/>
	
	<acme:button code="authenticated.tutorial.form.button.course" action="/any/course/show?id=${course.id}"/>
</acme:form>
