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
        <div class="alert alert-danger alert-dismissable">
              <a href="#" class="close" data-dismiss="alert"
                 aria-label="close">&times;</a>
              <strong>Password mismatch! </strong> Please retry.
        </div
    </c:when>
    <c:when test="${status =='emailinvaild'}">
        <hr>
        <div class="alert alert-danger alert-dismissable">
              <a href="#" class="close" data-dismiss="alert"
                 aria-label="close">&times;</a>
              <strong>Email mismatch! </strong> Please retry with an valid Email.
        </div
    </c:when>
    <c:when test="${status =='pwtooshort'}">
        <hr>
        <div class="alert alert-danger alert-dismissable">
              <a href="#" class="close" data-dismiss="alert"
                 aria-label="close">&times;</a>
              <strong>Password too short! </strong> Password length must be greater than 6 chars.
        </div
    </c:when>
    <c:when test="${status =='pwtoolong'}">
        <hr>
        <div class="alert alert-danger alert-dismissable">
              <a href="#" class="close" data-dismiss="alert"
                 aria-label="close">&times;</a>
              <strong>Password too long! </strong> Password length mustn't be longer than 42 chars.
        </div
    </c:when>
    <c:when test="${status =='nametoolong'}">
        <hr>
        <div class="alert alert-danger alert-dismissable">
              <a href="#" class="close" data-dismiss="alert"
                 aria-label="close">&times;</a>
              <strong>Name too long! </strong> Password length mustn't be longer than 20 chars.
        </div>
    </c:when>
</c:choose>