<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<style>
	.box {
	  width: 150px;
	  height: auto;
	  padding: 10px;
	  display: flex;
	  align-items: center;
	  justify-content: center;
	  font-size: 15px;
	  font-weight: bold;
	  color: white;
	  margin-bottom: 10px;
	  border-radius: 2px;
	}
	
	.published {
	  background-color: green;
	}
	
	.not-published {
	  background-color: red;
	}
</style>

<acme:form>

	<acme:input-textbox code="assistant.tutorial.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.tutorial.form.label.tAbstract" path="tAbstract"/>
	<acme:input-textbox code="assistant.tutorial.form.label.code" path="code"/>
	<acme:input-double code="assistant.tutorial.form.label.estimatedTotalTime" path="estimatedTotalTime"/>
	<acme:input-textarea code="assistant.tutorial.form.label.goals" path="goals"/>
	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="assistant.tutorial.form.label.published" path="published" readonly="true"/>
	</jstl:if>

	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>
	
	<jstl:if test="${_command != 'create'}">		
		<jstl:if test="${ !canPublish && !published }">
			<div><acme:message code="assistant.tutorial.form.message.cannotPublish"/></div>
		</jstl:if>
	</jstl:if>
	
	<acme:button code="assistant.tutorial.form.button.course" action="/any/course/show?id=${course}"/>
	<acme:button code="assistant.tutorial.form.button.sessions" action="/assistant/session/list?tutorialId=${id}"/>

<acme:hidden-data path="published"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="assistant.tutorial.form.submit.save" action="/assistant/tutorial/create"/>
	</jstl:if>
	<jstl:if test="${ !published && canPublish}">
		<acme:submit code="assistant.tutorial.form.submit.publish" action="/assistant/tutorial/publish"/>
	</jstl:if>
	<jstl:if test="${_command != 'create' && !published}">
		<acme:submit code="assistant.tutorial.form.submit.delete" action="/assistant/tutorial/delete"/>
		<acme:submit code="assistant.tutorial.form.submit.update" action="/assistant/tutorial/update"/>
	</jstl:if>
</acme:form>