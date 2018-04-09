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

    <title>DigitalJournal</title>
</head>

<body id="home" class="">
<jsp:include page="navbar.jsp"/>

<main role="main">
    <div class="container journal_conatainer">
                <h2 class="pb-2">Your Journals</h2>
                <div class="row text-dark">
                    <div class="col-md-9">
                        <!-- Card Examples -->
                        <c:forEach items="${journals}" var="journal">
                            <div class="card journal_entry">
                                <div class="card-block p-3">
                                    <h4 class="card-title" >${journal.journalName}</h4>
                                    <p class="card-text text-muted">${journal.content}</p>
                                    <div class="entry_btn">
                                        <div class="dropdown">
                                            <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown">More
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li>
                                                    <a href="/editjournal/${journal.journalid}" name="${journal.journalid}" class="dropdown-item">Edit</a>
                                                </li>

                                                <li>
                                                    <a href="/" class="dropdown-item">Share</a>
                                                </li>

                                                <li>
                                                    <a class="dropdown-item" href="#">Delete</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <jsp:useBean id="dateValue" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue" property="time" value="${journal.date}"/>
                                <div class="card-footer text-muted"> <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/>
                                </div>
                            </div>

                        </c:forEach>
                        <c:choose>
                            <c:when test="${empty journals}">
                                <div class="text-light mt-3 text-center">
                                    No Journals found!
                                </div>
                            </c:when>

                        </c:choose>

                    </div>

                    <!-- ASIDE -->

                    <div class="col-md-3">
                        <!-- <img src="../../webapp/resources/res/img/profile.jpg" alt="generic profile" class="img-thumbnail img-circles">
                         <hr> -->
                        <button class="btn btn-primary btn-block journal_entry" id="btn_newjournal" data-toggle="modal"
                                data-target=".bd-example-modal-lg">Create new Journal
                        </button>
                        <div class="card" style="width: 20rem;">
                            <!-- <img class="card-img-top" src="res/img/generic_friends.jpg" alt="Card image cap"> -->
                            <div class="card-block p-3">
                                <h4 class="card-title">Your Friends</h4>
                                <p class="card-text">Quick access to your best friends.</div>
                            <ul class="list-group list-group-flush text-muted">
                                <li class="list-group-item">Kurt Cobain</li>
                                <li class="list-group-item">Dave Grohl</li>
                                <li class="list-group-item">Krist Novoselic</li>
                            </ul>
                            <!-- <div class="card-block">
                            <a href="#" class="card-link">Card link</a>
                            <a href="#" class="card-link">Another link</a>
                        </div> -->
                        </div>
                    </div>
                </div>
    </div>

    <!-- new Journal Feed -->
    <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content text-dark">
                <div class="modal-header">
                    <h5 class="modal-title btn-block" id="exampleModalLabel">Create your new Journal.</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <form:form method="POST" action="${pageContext.request.contextPath}/newjournal"
                           modelAttribute="journal">

                    <form>
                        <div class="modal-body">
                            <div class="form-group">
                                <label id="txt_journalname" class="control-label">Name</label>
                                <input class="form-control form-control-lg" type="text" placeholder="Name"
                                <form:input path="journalName"/>
                                <label id="txt_journalname" class="control-label">Content</label>
                                <textarea class="form-control form-control-lg" type="text" rows="10" placeholder="What is this Journal about?"
                                <form:textarea path="content"/>
                            </div>
                            <!-- <input type="submit" value="Submit" class="btn btn-outline-light btn-block"/>
                             <!--Alerts -->
                            <jsp:include page="Forms/alerts.jsp"/>
                        </div>

                        <div class="modal-footer">
                            <div class="form-group">
                                <div class="text-right">
                                    <button type="submit" id="btn_submitjournal" class="btn btn-primary">Save</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </form:form>
            </div>
        </div>
    </div>
</main>

<jsp:include page="Forms/modals.jsp"/>
<!-- MAIN FOOTER -->
<jsp:include page="footer.jsp"/>
</body>

</html>
