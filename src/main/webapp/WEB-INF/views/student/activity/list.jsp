<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
    <acme:list-column code="student.activity.list.label.title" path="title"/>
    <acme:list-column code="student.activity.list.label.summary" path="summary"/>
    <acme:list-column code="student.activity.list.label.activityType" path="activityType"/>
    <acme:list-column code="student.activity.list.label.moreInfo" path="moreInfo"/>    
</acme:list>
 <acme:button code="student.activity.list.button.create" action="/student/activity/create?enrolmentId=${enrolmentId}"/>

   
