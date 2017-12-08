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

    <title>EDIT | DigitalJournal</title>
</head>

<body id="home" class="">
<jsp:include page="navbar.jsp"/>

<main role="main">
    <div class="container journal_conatainer">

        <h2 class="display-1">Edit Journals</h2>
        <div class="row">
            <div class="col-md-9">
                <form:form method="POST" action="${pageContext.request.contextPath}/editjournal"
                           modelAttribute="journal">
                    <form>
                        <div class="form-group">
                            <label id="txt_journalname" class="control-label">Name</label>
                            <input class="form-control form-control-lg" type="text" placeholder=""
                            <form:input path="journalName"/>
                        </div>
                        <div class="form-group">
                            <label id="txt_journalname" class="control-label">Content</label>
                            <textarea class="form-control form-control-lg" rows="15" type="text" placeholder=""
                            <form:textarea path="content"/>
                        </div>
                        <!-- <input type="submit" value="Submit" class="btn btn-outline-light btn-block"/>
                         <!--Alerts -->
                        <jsp:include page="Forms/alerts.jsp"/>

                        <div class="form-group">
                            <div class="text-right">
                                <a href="/delete" class="btn btn-outline-danger">Delete</a>
                                <button type="submit" id="btn_submitjournal" class="btn btn-primary">Save</button>
                            </div>
                        </div>
                    </form>
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

            <div class="col-md-3 text-dark">
                <!-- <img src="res/img/profile.jpg" alt="generic profile" class="img-thumbnail img-circles"> -->
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

</main>

<c:choose>
    <c:when test="${delete == 'true'}">
        <!-- Modal -->
        <div class="modal fade" id="delete" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Delete Journal </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Do you really want to delete this journal?
                    </div>
                    <div class="modal-footer">
                        <a href="/deleteconfirm" class="btn btn-danger">Delete</a>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#delete').modal('show');
        </script>
    </c:when>
</c:choose>

<jsp:include page="footer.jsp"/>

<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>

</html>
