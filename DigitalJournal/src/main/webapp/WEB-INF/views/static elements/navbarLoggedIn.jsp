<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 09/04/2018
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
    <div class="container">
        <a href="/" class="navbar-brand">DigitalJournal</a>
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a href="#home" class="nav-link">Home</a>
                </li>

                <c:choose>
                    <c:when test="${loggedInUser != null}">
                        <li class="nav-item">
                            <a href="/logout" class="nav-link">Log Out</a>
                        </li>
                    </c:when>
                </c:choose>

                <li class="nav-item">
                    <a href="#share-head-section" class="nav-link">
                        <i class="material-icons">account_circle</i></a>
                </li>
            </ul>
        </div>
    </div>
</nav>