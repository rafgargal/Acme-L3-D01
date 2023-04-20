<%--
- footer.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<jstl:if test="${banner != null }">
	<div style="width:90%; margin: auto; display: flex; flex-wrap: wrap; justify-content: center; align-items: center; gap: 10px; margin-bottom: 20px;">

		<a href="${banner.webDocLink}" style="flex: 0 1 500px; position: relative;">
			<img src="${banner.pictureLink}" alt="${banner.slogan}" style="width:100%"/>
			<div style="position: absolute; bottom: 0; background-color: grey; color: white; padding: 2px; font-size: 12px;">
				<acme:message code="master.welcome.advertisement"/>
			</div>
		</a>

		<div style="flex: 0 1 470px; font-weight: 600;">
			<acme:print value="${banner.slogan}" />
		</div>

	</div>
</jstl:if>

<acme:footer-panel>
	<acme:footer-subpanel code="master.footer.title.about">
		<acme:footer-option icon="fa fa-building" code="master.footer.label.company" action="/master/company"/>
		<acme:footer-option icon="fa fa-file" code="master.footer.label.license" action="/master/license"/>		
	</acme:footer-subpanel>

	<acme:footer-subpanel code="master.footer.title.social">
		<acme:message var="$linkedin$url" code="master.footer.url.linkedin"/>
		<acme:footer-option icon="fab fa-linkedin" code="master.footer.label.linked-in" action="${$linkedin$url}" newTab="true"/>
		<acme:message var="$twitter$url" code="master.footer.url.twitter"/>
		<acme:footer-option icon="fab fa-twitter" code="master.footer.label.twitter" action="${$twitter$url}" newTab="true"/>
	</acme:footer-subpanel>

	<acme:footer-subpanel code="master.footer.title.languages">
		<acme:footer-option icon="fa fa-language" code="master.footer.label.english" action="/?locale=en"/>
		<acme:footer-option icon="fa fa-language" code="master.footer.label.spanish" action="/?locale=es"/>
	</acme:footer-subpanel>

	<acme:footer-logo logo="images/logo.png">
		<acme:footer-copyright code="master.company.name"/>
	</acme:footer-logo>		

</acme:footer-panel>

