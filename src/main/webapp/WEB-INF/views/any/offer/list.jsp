<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.offer.list.label.heading" path="heading"/>
	<acme:list-column code="any.offer.list.label.summary" path="summary"/>
</acme:list>