<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer id="main-footer" class="bg-dark">
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <div class="py-4">
                    <h1 class="h3">DigitalJournal</h1>
                    <p>Copyright &copy; 2017</p>
                    <button class="btn btn-primary" data-toggle="modal" data-target="#contactModal">Contact Us</button>
                </div>
            </div>
        </div>
    </div>
</footer>

<!-- CONTACT MODAL -->

<div class="modal fade text-dark" id="contactModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <form:form action="${pageContext.request.contextPath}/contactRequest" method="POST" modelAttribute="contactRequest">
            <div class="modal-header">
                <h5 class="modal-title" id="contactModalTitle">
                    Contact Us
                </h5>
            </div>
            <div class="modal-body">

                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" <form:input path="name"/>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control" <form:input path="email" />
                    </div>
                    <div class="form-group">
                        <label>Message</label>
                        <form:textarea class="form-control" path="message"/>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-primary btn-block">Submit</button>
            </div>
            </form:form>
        </div>
    </div>
</div>
