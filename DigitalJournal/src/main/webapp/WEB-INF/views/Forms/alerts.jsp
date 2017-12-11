<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 22/11/2017
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${status =='pwmissmatch'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="pwmissmatch">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Password mismatch!</strong> Please retry.
        </div
    </c:when>
    <c:when test="${status =='emailinvalid'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="emailinvalid">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Email invalid!</strong> Please retry with an valid Email.
        </div
    </c:when>
    <c:when test="${status =='pwtooshort'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="pwtooshort">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Password too short!</strong> Password length must be greater than 6 chars.
        </div
    </c:when>
    <c:when test="${status =='pwtoolong'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="pwtoolong">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Password too long!</strong> Password length mustn't be longer than 42 chars.
        </div
    </c:when>
    <c:when test="${status =='nametoolong'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="nametoolong">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Name too long!</strong> Password length mustn't be longer than 20 chars.
        </div>
    </c:when>
    <c:when test="${status =='emailtoolong'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="emailtoolong">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Email too long!</strong> Email length mustn't be longer than 100 chars.
        </div>
    </c:when>
    <c:when test="${loginError =='invalidCredentials'}">
        <hr>
        <div class="alert alert-danger alert-dismissable" id="invalidCredentials">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Invalid Credentials!</strong> Please make sure the entered credentials are correct.
        </div>
    </c:when>
</c:choose>