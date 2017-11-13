<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 11/11/2017
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Form Example - Register an Employee</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <spring:url value="/resources/main.js" var="mainJs" />

    <script src="${mainJs}"></script>
</head>

<body>
<h3>Welcome, Enter The Employee Details</h3>

<form:form method="POST" action="${pageContext.request.contextPath}/addEmployee" modelAttribute="employee">

    <form>
        <div class="form-group"><input type="text" placeholder="name" <form:input path="name"/></div>
        <div class="form-group"><input type="email" placeholder="email" <form:input path="id"/></div>
        <div class="form-group"><input type="text" placeholder="contractNumber" <form:input path="contractNumber"/></div>
        <input type="submit" value="Submit" class="btn btn-primary"/>
    </form>

</form:form>
</body>
</html>
