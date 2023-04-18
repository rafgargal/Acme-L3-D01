<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="any.course.form.label.instantiationMoment" path="instantiationMoment"/>	
	<acme:input-textbox code="any.course.form.label.heading" path="heading"/>
	<acme:input-textbox code="any.course.form.label.summary" path="summary"/>
	<acme:input-moment code="any.course.form.label.availabilityPeriodInit" path="availabilityPeriodInit"/>
	<acme:input-moment code="any.course.form.label.availabilityPeriodFin" path="availabilityPeriodFin"/>
	<acme:input-money code="any.course.form.label.price" path="price"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>
</acme:form>
