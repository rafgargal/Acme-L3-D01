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

	<acme:input-select code="assistant.tutorial.form.label.course" path="course" choices="${courses}"/>
	
	<jstl:choose>
		<jstl:when test="${ published }">
			<div class="box published">
				<acme:message code="assistant.tutorial.form.message.published"/>
			</div>
		</jstl:when>
		<jstl:otherwise>
			<div class="box not-published">
				<acme:message code="assistant.tutorial.form.message.not-published"/>
			</div>
		</jstl:otherwise>
	</jstl:choose>
	
	<jstl:if test="${ !canPublish && !published }">
		<div><acme:message code="assistant.tutorial.form.message.cannotPublish"/></div>
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