<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:if test="${_command == 'create'}">
	<acme:input-moment code="administrator.banner.form.label.instantiationOrUpdateMoment" path="instantiationOrUpdateMoment"/>
	</jstl:if>
	<jstl:if test="${_command != 'create'}">
	<acme:input-moment code="administrator.banner.form.label.instantiationOrUpdateMoment" path="instantiationOrUpdateMoment" readonly="true"/>	
	</jstl:if>
	<acme:input-moment code="administrator.banner.form.label.displayPeriodStart" path="displayPeriodStart"/>
	<acme:input-moment code="administrator.banner.form.label.displayPeriodEnd" path="displayPeriodEnd"/>
	<acme:input-url code="administrator.banner.form.label.pictureLink" path="pictureLink"/>
	<acme:input-textarea code="administrator.banner.form.label.slogan" path="slogan"/>
	<acme:input-url code="administrator.banner.form.label.webDocLink" path="webDocLink"/>
	
	<jstl:choose>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
            <acme:submit code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
            <acme:submit code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>
