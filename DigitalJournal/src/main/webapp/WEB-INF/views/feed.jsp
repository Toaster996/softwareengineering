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
    <spring:url value="/resources/res/css/actionbutton.css" var="fabCSS"/>
    <link href="${journalCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${fabCSS}" rel="stylesheet"/>
    <link href="${fontCSS}" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <spring:url value="/resources/js/jquery.min.js" var="jQuery"/>
    <spring:url value="/resources/js/popper.min.js" var="popper"/>
    <spring:url value="/resources/js/bootstrap.min.js" var="bootstrap"/>
    <spring:url value="/resources/res/js/main.js" var="main"/>
    <spring:url value="/resources/res/js/actionbutton.js" var="fabJS"/>
    <script src="${jQuery}"></script>
    <script src="${popper}"></script>
    <script src="${bootstrap}"></script>
    <script src="${main}"></script>
    <script src="${fabJS}"></script>

    <title>FEED | DigitalJournal</title>
</head>

<body id="home" class="">
<jsp:include page="static elements/navbarLoggedIn.jsp"/>

<main role="main">
    <!-- Floating Action Button -->
    <div class="fab blue child btn-floating" data-tooltip="I am B" data-subitem="1">
        <i class="material-icons">date_range</i>
        <a href="/journal/newgoal" class="btn-floating fab-tip">Create new Goal</a>
    </div>
    <div class="fab blue child btn-floating" data-tooltip="I am A" data-subitem="2" data-toggle="modal"
         data-target=".bd-example-modal-lg">
        <i class="material-icons">mode_edit</i>
        <span class="btn-floating fab-tip">Create new Journal</span>
    </div>
    <div class="fab" id="masterfab">
        <i class="material-icons">add</i>
    </div>

    <div class="container journal_conatainer">
        <h2 class="pb-2">Your Journals</h2>
        <div class="row text-dark">
            <div class="col-md-9">
                <!-- Card Examples -->
                <c:forEach items="${journals}" var="journal">
                    <div class="card journal_entry">
                        <div class="card-block p-3">
                            <h4 class="card-title">${journal.journalName}</h4>
                            <p class="card-text text-muted">${journal.content}</p>
                            <div class="entry_btn">
                                <div class="dropdown">
                                    <button class="btn btn-outline-secondary dropdown-toggle" type="button"
                                            data-toggle="dropdown">More
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="/editjournal/${journal.journalid}" name="${journal.journalid}"
                                               class="dropdown-item">Edit</a>
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
                        <div class="card-footer text-muted"><fmt:formatDate value="${dateValue}"
                                                                            pattern="MM/dd/yyyy HH:mm"/>
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

                <div class="card" style="width: 20rem;">
                    <div class="card-block p-3">
                        <h4 class="card-title">Your Friends</h4>
                        <p class="card-text">Quick access to your best friends.</div>
                    <ul class="list-group list-group-flush text-muted">
                        <li class="list-group-item">Kurt Cobain</li>
                        <li class="list-group-item">Dave Grohl</li>
                        <li class="list-group-item">Krist Novoselic</li>
                    </ul>
                </div>
                <hr>
                <div class="card" style="width: 20rem;">
                    <div class="card-block p-3">
                        <h4 class="card-title">Your Goals</h4>
                        <p class="card-text">Overview of your latest Goals.</div>
                    <ul class="list-group list-group-flush text-muted">
                        <c:forEach items="${goals}" var="goal">
                            <li class="list-group-item">
                                <strong>${goal.date}</strong><a href="/journal/goal/${goal.id}">${goal.description}</a>
                            </li>
                        </c:forEach>

                        <li class="list-group-item">
                            <strong>2018/05/12</strong> buy beer
                        </li>
                        <li class="list-group-item">
                            <strong>2018/05/13</strong> buy juice
                        </li>
                        <li class="list-group-item">
                            <strong>2018/05/14</strong> buy braches
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>


    <!-- Modal to create new Journal -->
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
                                <textarea class="form-control form-control-lg" type="text" rows="10"
                                          placeholder="What is this Journal about?"
                                <form:textarea path="content"/>
                            </div>
                            <!-- <input type="submit" value="Submit" class="btn btn-outline-light btn-block"/>
                             <!--Alerts -->
                            <jsp:include page="Forms/alerts.jsp"/>
                        </div>

                        <div class="modal-footer">
                            <div class="form-group">
                                <div class="text-right">
                                    <button type="submit" id="btn_submitjournal" class="btn btn-primary">Save
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </form:form>
            </div>
        </div>
    </div>

    <c:choose>
        <c:when test="${status =='createGoal'}">
            <div class="modal fade" id="mdl_goal_create" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content text-black">
                        <div class="modal-header">
                            <h5 class="modal-title">Create a new Goal!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form:form action="${pageContext.request.contextPath}/createGoal"
                                       method="POST" modelAttribute="goal">
                                <div class="form-group">
                                    <input type="text" id="lbl_name"
                                           class="form-control form-control-lg"
                                           placeholder="What is your Goal?" <form:input path="name"/>
                                </div>
                                <div class="form-group">
                                    <input type="date" id="lbl_date"
                                           class="form-control form-control-lg"
                                           placeholder="Date" <form:input
                                        path="date"/>
                                </div>
                                <div class="form-group">
                                    <textarea class="form-control form-control-lg" rows="3" type="text" placeholder="Describe your Goal!"
                                    <form:textarea path="description"/>
                                </div>
                                <input type="submit" value="Save"
                                       class="btn btn-primary btn-block">

                                <jsp:include page="Forms/alerts.jsp"/>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                $('#mdl_goal_create').modal('show');
            </script>
        </c:when>

        <c:when test="${status =='showGoal'}">
            <div class="modal fade" id="mdl_goal_show" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content text-black">
                        <div class="modal-header">
                            <h5 class="modal-title">${showGoal.name}!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p class="lead">${showGoal.description}</p>
                            <hr>
                            <p>This goal has to be achieved in ${showGoal.daysLeft} days.</p>
                        </div>
                        <div class="modal-footer">
                            <div class="form-group">
                                <div class="text-right">
                                    <a href="journal/deletegoal/${showGoal.id}" class="btn btn-danger">Delete</a>
                                    <button type="button" class="btn btn-primary" id="btn_close_goal">
                                        Close
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                $('#mdl_goal_show').modal('show');
                $('#btn_close_goal').click(function(){
                    $('#mdl_goal_show').modal('hide');
                })
            </script>
        </c:when>
    </c:choose>

</main>
<jsp:include page="Forms/modals.jsp"/>
<!-- MAIN FOOTER -->
<jsp:include page="footer.jsp"/>
</body>

</html>
