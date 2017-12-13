<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <fav></fav>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta content="width=device-width, initial-scale=1.0"/>
    <!-- <link rel="stylesheet" href="../../resources/css/font-awesome.min.css">
     <link rel="stylesheet" href="../../resources/css/style.css">

     <link rel="stylesheet" href="../../resources/css/bootstrap.css">
     <link rel="stylesheet" href="../../resources/css/style.css"> -->
    <spring:url value="/resources/css/bootstrap.css" var="bootCSS"/>
    <spring:url value="/resources/css/style.css" var="styleCSS"/>
    <spring:url value="/resources/css/font-awesome.min.css" var="fontCSS"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${bootCSS}" rel="stylesheet"/>
    <link href="${styleCSS}" rel="stylesheet"/>
    <link href="${fontCSS}" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Raleway" rel="stylesheet">

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

<body id="home">
<jsp:include page="navbar.jsp"/>

<!-- HOME SECTION -->
<header id="home-section">
    <div class="dark-overlay">
        <div class="home-inner">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 d-none d-lg-block">
                        <h1 class="display-4">Create
                            <strong>personal & secure</strong> journals and keep them forever.</h1>
                        <div class="d-flex flex-row">
                            <div class="p-4 align-self-start">
                                <i class="fa fa-check"></i>
                            </div>
                            <div class="p-4 align-self-end">
                                Create your own journal to keep memories forever for yourself. You can trust our
                                encryption.
                            </div>
                        </div>
                        <div class="d-flex flex-row">
                            <div class="p-4 align-self-start">
                                <i class="fa fa-check"></i>
                            </div>
                            <div class="p-4 align-self-end">
                                Share your memories not with everyone, but with your best friends.
                            </div>
                        </div>
                        <div class="d-flex flex-row">
                            <div class="p-4 align-self-start">
                                <i class="fa fa-check"></i>
                            </div>
                            <div class="p-4 align-self-end">
                                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate
                                quia voluptate voluptatum repellendus?
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="card bg-primary text-center card-form">
                            <!-- !!!! Card inner !!! -->
                            <div class="card-body">
                                <!--In here, we need to show different things, depending on the model -->
                                <c:choose>
                                    <c:when test="${status =='sucreg'}">
                                        <div id="sucreg">
                                            <h3>Welcome, ${username}! </h3>
                                            <p>We sent you an email to your address ${email}. Please click on the link
                                                you
                                                can find there within 24 hours.</p>
                                            <input type="submit" value="Create your Journal!" href="/journal"
                                                   class="btn btn-outline-light mr-3 ml-3">
                                        </div>
                                    </c:when>
                                    <c:otherwise> <!--In the otherwise case, we have to show forms-->
                                        <c:choose>
                                            <c:when test="${status == 'new'}">
                                                <div id="login">
                                                    <h3>Log in</h3>
                                                    <p>Please fill out this form to log in.</p>
                                                    <form:form action="${pageContext.request.contextPath}/login"
                                                               method="POST" modelAttribute="loginUser">
                                                        <div class="form-group">
                                                            <input type="text" id="lbl_username"
                                                                   class="form-control form-control-lg"
                                                                   placeholder="Username" <form:input path="loginName"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <input type="password" id="lbl_password"
                                                                   class="form-control form-control-lg"
                                                                   placeholder="Password" <form:input
                                                                path="loginPassword"/>
                                                        </div>
                                                        <input type="submit" value="Log In"
                                                               class="btn btn-outline-light btn-block">
                                                        <input type="button" value="Sign Up" id="btn_log-in"
                                                               class="btn btn-outline-light btn-block">
                                                        <div class="form-group text-right">
                                                            <button type="button" class="btn btn-link" id="btn_recover"
                                                                    data-toggle="modal" data-target="#recoverModal">
                                                                Forgot Password
                                                            </button>
                                                        </div>
                                                        <jsp:include page="Forms/alerts.jsp"/>
                                                    </form:form>
                                                </div>
                                                <script>
                                                    $(function () {
                                                        $('#register').hide();
                                                    });
                                                </script>
                                            </c:when>
                                        </c:choose>
                                        <!-- Register from -->
                                        <div id="register">
                                            <h3>Sign Up Today</h3>
                                            <p>Please fill out this form to register</p>
                                            <form:form method="POST" action="${pageContext.request.contextPath}/addUser"
                                                       modelAttribute="registrationUser">
                                                <form>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   id="lbl_reg_name"
                                                                                   type="text" placeholder="Name"
                                                        <form:input path="name"/></div>
                                                    <div class="form-group">
                                                        <input class="form-control form-control-lg" type="email"
                                                               id="lbl_reg_email"
                                                               placeholder="Email"
                                                        <form:input path="email"/>
                                                    </div>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   id="lbl_reg_password"
                                                                                   type="password"
                                                                                   placeholder="Password"
                                                        <form:input path="password"/></div>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   id="lbl_reg_passwordconf"
                                                                                   type="password"
                                                                                   placeholder="Password Confirm"
                                                        <form:input path="passwordConfirm"/></div>
                                                    <input type="submit" value="Submit" id="btn_reg_submit"
                                                           class="btn btn-outline-light btn-block"/>
                                                    <!--Alerts -->
                                                    <jsp:include page="Forms/alerts.jsp"/>
                                                </form>
                                            </form:form>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</header>


<jsp:include page="Forms/modals.jsp"/>
<jsp:include page="Forms/modalContact.jsp"/>

<!-- EXPLORE HEAD -->
<section id="explore-head-section">
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <div class="p-5">
                    <h1 class="display-4">Explore</h1>
                    <p class="lead">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Atque ut perferendis
                        nostrum, nulla facere quam modi
                        eos vero magni consectetur.</p>
                    <a href="#" class="btn btn-outline-secondary">Find Out More</a>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- EXPLORE SECTION -->
<section id="explore-section" class="bg-light text-muted py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img src="../../resources/res/img/generic3.jpg" alt="" class="img-fluid mb-3 rounded-circle">
            </div>
            <div class="col-md-6">
                <h3>Explore & Connect</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur qui molestiae optio animi
                    architecto, labore
                    voluptatem dolor, excepturi vero doloribus quo quam reprehenderit aperiam temporibus eveniet
                    consequuntur quidem
                    ipsam ipsa.</p>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- CREATE HEAD -->
<section id="create-head-section" class="bg-primary">
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <div class="p-5">
                    <h1 class="display-4">Create</h1>
                    <p class="lead">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Atque ut perferendis
                        nostrum, nulla facere quam modi
                        eos vero magni consectetur.</p>
                    <a href="#" class="btn btn-outline-light">Find Out More</a>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- CREATE SECTION -->
<section id="create-section" class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>Create Your Passion</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur qui molestiae optio animi
                    architecto, labore
                    voluptatem dolor, excepturi vero doloribus quo quam reprehenderit aperiam temporibus eveniet
                    consequuntur quidem
                    ipsam ipsa.</p>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <img src="../../resources/res/img/generic4.jpg" alt="" class="img-fluid mb-3 rounded-circle">
            </div>
        </div>
    </div>
</section>

<!-- SHARE HEAD -->
<section id="share-head-section" class="bg-primary">
    <div class="container">
        <div class="row">
            <div class="col text-center">
                <div class="p-5">
                    <h1 class="display-4">Share</h1>
                    <p class="lead">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Atque ut perferendis
                        nostrum, nulla facere quam modi
                        eos vero magni consectetur.</p>
                    <a href="#" class="btn btn-outline-light">Find Out More</a>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- SHARE SECTION -->
<section id="share-section" class="bg-light text-muted py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img src="../../resources/img/share-section1.jpg" alt="" class="img-fluid mb-3 rounded-circle">
            </div>
            <div class="col-md-6">
                <h3>Share what you create</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consectetur qui molestiae optio animi
                    architecto, labore
                    voluptatem dolor, excepturi vero doloribus quo quam reprehenderit aperiam temporibus eveniet
                    consequuntur quidem
                    ipsam ipsa.</p>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
                <div class="d-flex flex-row">
                    <div class="p-4 align-self-start">
                        <i class="fa fa-check"></i>
                    </div>
                    <div class="p-4 align-self-end">
                        Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate quia
                        voluptate voluptatum repellendus?
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- MAIN FOOTER -->
<jsp:include page="footer.jsp"/>
<!-- RECOVER MODAL -->
<div class="modal fade text-dark" id="recoverModal">
    <form:form action="${pageContext.request.contextPath}/recoverpassword" method="POST">
        <form>
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="contactModalTitle">
                            Recover your Password!
                        </h5>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="form-group">
                                <label type="email">Email</label>
                                <input type="email" class="form-control" name="email"/>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary btn-block" type="submit">Submit</button>
                    </div>
                </div>
            </div>
        </form>
    </form:form>
</div>

<c:choose>
    <c:when test="${recover == 'true' || recover == 'false'}">
        <!-- Modal -->
        <div class="modal fade" id="recover" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">We've send you an email!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        You received an recovery email at ${email}. Please click on the link in this email within 1
                        hour.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#recover').modal('show');
        </script>
    </c:when>
    <c:when test="${recover == 'invalidEmail'}">
        <!-- Modal -->
        <div class="modal fade" id="recovermail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Invalid Email!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        This Email Address is invalid. Please retry.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#recovermail').modal('show');
        </script>
    </c:when>
    <c:when test="${recover == 'pwchangesuccess'}">
        <!-- Modal -->
        <div class="modal fade" id="recoverdone" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Password resetted!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        You Password has been resetted. Check out your Journals.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#recoverdone').modal('show');
        </script>
    </c:when>
</c:choose>


<!--
<script src="../../resources/js/jquery.min.js"></script>
<script src="../../resources/js/popper.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/res/js/main.js"></script>  -->


</body>

</html>