<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 22/11/2017
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="register">
    <h3>Sign Up Today</h3>
    <p>Please fill out this form to register</p>
    <form:form method="POST" action="${pageContext.request.contextPath}/addUser"
               modelAttribute="user">
        <form>
            <div class="form-group"><input class="form-control form-control-lg"
                                           type="text" placeholder="Name"
                <form:input
                        path="username"/></div>
            <div class="form-group"><input class="form-control form-control-lg"
                                           type="email" placeholder="Email"
                <form:input
                        path="email"/></div>
            <div class="form-group"><input class="form-control form-control-lg"
                                           type="password"
                                           placeholder="Password"
                <form:input path="password"/></div>
            <div class="form-group"><input class="form-control form-control-lg"
                                           type="password"
                                           placeholder="Password Confirm"
                <form:input path="passwordConfirm"/></div>
            <input type="submit" value="Submit"
                   class="btn btn-outline-light btn-block"/>
            <!--Alerts -->
            <jsp:include page="Forms/alerts.jsp" />

        </form>
    </form:form>
</div>