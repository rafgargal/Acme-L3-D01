<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.peep.list.label.title" path="title" width="60%"/>
	<acme:list-column code="any.peep.list.label.moment" path="moment" width="40%"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="any.peep.list.button.create" action="/any/peep/create"/>
</jstl:if>