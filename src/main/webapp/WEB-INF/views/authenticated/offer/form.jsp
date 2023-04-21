


<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
		
	<acme:input-textbox code="authenticated.offer.form.label.heading" path="heading"/>
	<acme:input-textbox code="authenticated.offer.form.label.summary" path="summary"/>
	<acme:input-moment code="authenticated.offer.form.label.availabilityPeriodInit" path="availabilityPeriodInit"/>
	<acme:input-moment code="authenticated.offer.form.label.availabilityPeriodFin" path="availabilityPeriodFin"/>
	<acme:input-money code="authenticated.offer.form.label.price" path="price"/>
	<acme:input-url code="authenticated.offer.form.label.link" path="link"/>	
	
	<jstl:choose>	 
  
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:input-moment code="authenticated.offer.form.label.instantiationMoment" path="instantiationMoment"/>
			<sec:authorize access="hasRole('Administrator')">
			<acme:submit code="authenticated.offer.form.button.delete" action="/authenticated/offer/delete"/>
			<acme:submit code="authenticated.offer.form.button.update" action="/authenticated/offer/update"/>
			</sec:authorize>
		
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'create')}">
		<acme:submit code="authenticated.offer.form.button.create" action="/authenticated/offer/create"/>
		</jstl:when>
	</jstl:choose>
	
	

</acme:form>
