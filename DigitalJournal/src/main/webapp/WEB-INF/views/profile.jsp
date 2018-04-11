<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 25/11/2017
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <spring:url value="/resources/css/bootstrap.css" var="bootCSS"/>
    <spring:url value="/resources/css/style.css" var="styleCSS"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontCSS"/>
    <spring:url value="/resources/res/css/journal.css" var="journalCSS"/>
    <spring:url value="/resources/res/css/profile.css" var="proCSS"/>
    <link href="${journalCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${proCSS}" rel="stylesheet"/>
    <link href="${fontCSS}" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <spring:url value="/resources/js/jquery.min.js" var="jQuery"/>
    <spring:url value="/resources/js/popper.min.js" var="popper"/>
    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrap"/>
    <spring:url value="/resources/res/js/main.js" var="main"/>
    <script src="${jQuery}"></script>
    <script src="${popper}"></script>
    <script src="${bootstrap}"></script>
    <script src="${main}"></script>

    <title>PROFILE | DigitalJournal</title>
</head>

<body id="home" class="">
<jsp:include page="static elements/navbarLoggedIn.jsp"/>

<main role="main">
    <div class="container journal_conatainer">
        <h2 id="heading">Your Profile</h2>
        <div class="row text-dark">
            <div class="col-md-4">
                <img class="img-rounded" width="330em" height="330em" src="res/img/examplePic.svg" alt="Profile">
            </div>


            <div class="col-md-8">
                <div class="card text-center card-form card-profile">
                    <div class="card-header bg-primary">
                        <h3 class="text-white">Your personal information</h3>
                    </div>
                    <div class="card-body">
                        <div id="infocard">

                            <form>
                                <div class="form-group text-lg-left">
                                    <p>
                                        <strong>Username:</strong>
                                        <span style="display:inline-block; width: 2.7em;"></span> John Doe
                                    </p>
                                </div>
                                <div class="form-group text-lg-left">
                                    <p>
                                        <strong>Email:</strong>
                                        <span style="display:inline-block; width: 5em;"></span> johndoe@mail.com
                                        <button class="btn btn-link text-muted" type="button">change</button>
                                    </p>
                                </div>

                                <div class="form-group text-lg-left">
                                    <p>
                                        Already created:
                                        <strong>42 journals!</strong>
                                    </p>
                                </div>

                                <input type="button" value="Delete Account" class="btn btn-danger btn-block btn-border-organge">
                                <hr>
                                <div class="form-group text-right">
                                    <button type="button" class="btn btn-dark btn-block btn-border-organge" id="btn_recover" data-toggle="contactModal" data-target="#recoverModal">Change Password</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="Forms/modals.jsp"/>
<jsp:include page="footer.jsp"/>
</body>

</html>
