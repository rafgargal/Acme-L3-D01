<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.instantiationOrUpdateMoment" path="instantiationOrUpdateMoment" width="15%"/>
	<acme:list-column code="administrator.banner.list.label.pictureLink" path="pictureLink" width="25%"/>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="60%"/>
</acme:list>

<acme:button code="administrator.banner.list.button.create" action="/administrator/banner/create"/>
