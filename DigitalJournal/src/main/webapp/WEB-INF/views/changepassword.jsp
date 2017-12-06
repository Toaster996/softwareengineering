<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 29/11/2017
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <spring:url value="/resources/css/bootstrap.css" var="bootCSS"/>
    <spring:url value="/resources/css/style.css" var="styleCSS"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontCSS"/>
    <spring:url value="/resources/res/css/journal.css" var="journalCSS"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${bootCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${fontCSS}" rel="stylesheet"/>
    <link href="${journalCSS}" rel="stylesheet"/>


    <spring:url value="/resources/js/jquery.min.js" var="jQuery"/>
    <spring:url value="/resources/js/popper.min.js" var="popper"/>
    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrap"/>
    <spring:url value="/resources/res/js/main.js" var="main"/>
    <script src="${jQuery}"></script>
    <script src="${popper}"></script>
    <script src="${bootstrap}"></script>
    <script src="${main}"></script>

    <title>CHANGE PASSWORD | DigitalJournal</title>
</head>

<body id="home" class="">
<jsp:include page="navbar.jsp"/>

<main role="main">
    <div class="container journal_conatainer">

        <h2>Reset Password</h2>
        <div class="row">
            <form:form method="POST" action="${pageContext.request.contextPath}/changepassword">

                <div class="form-group">
                    <label id="txt_journalname" class="control-label">Name: ${username}</label>
                </div>
                <div class="form-group">
                    <label id="txt_journalname" class="control-label">Email: ${email}</label>
                </div>
                <div class="form-group">
                    <label id="txt_journalname" type="password" class="control-label">Password</label>
                    <input class="form-control form-control-lg" type="password" placeholder="" name="password"/>
                </div>
                <div class="form-group">
                    <label id="txt_journalname" type="password" class="control-label">Password Confirm</label>
                    <input class="form-control form-control-lg" type="password" placeholder="" name="password_confirm"/>
                </div>
                <!-- <input type="submit" value="Submit" class="btn btn-outline-light btn-block"/>
                <!--Alerts -->
                <jsp:include page="Forms/alerts.jsp"/>

                <div class="form-group">
                    <div class="text-right">
                        <button type="submit" id="btn_submitjournal" class="btn btn-primary">Reset</button>
                    </div>
                </div>
            </form:form>
            <!--  <form>
                  <div class="form-group">
                      <label>Name</label>
                      <input type="text" class="form-control">
                  </div>
                  <div class="form-group">
                      <label>Content</label>
                      <textarea cols="50" rows="10" class="form-control"></textarea>
                  </div>
                  <button class="btn btn-primary btn-block mb-5">Submit</button>
              </form> -->
        </div>

    </div>

</main>

<!--< j s p : include page="footer.jsp"/> -->

<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>

</html>
