<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
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
<nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
    <div class="container">
        <a href="../../resources/html2/index.html" class="navbar-brand">DigitalJournal</a>
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a href="#home" class="nav-link">Home</a>
                </li>
                <li class="nav-item">
                    <a href="#explore-head-section" class="nav-link">Explore</a>
                </li>
                <li class="nav-item">
                    <a href="#create-head-section" class="nav-link">Create</a>
                </li>
                <li class="nav-item">
                    <a href="#share-head-section" class="nav-link">Share</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

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
                                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Incidunt, quaerat cupiditate
                                quia voluptate voluptatum repellendus?
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
                            <div class="card-body">
                                <!--In here, we need to show different things, depending on the model -->
                                <c:choose>
                                    <c:when test="${status =='sucreg'}">
                                        <h3>Welcome, ${name}! </h3>
                                        <p>We send you an email to your address ${email}. Please click on the link you
                                            can find there within 24 hours.</p>
                                        <input type="submit" value="Create your Journal!" href="#"
                                               class="btn btn-outline-light mr-3 ml-3">
                                    </c:when>

                                    <c:otherwise>
                                        <!-- Register from -->
                                        <div id="register">
                                            <h3>Sign Up Today</h3>
                                            <p>Please fill out this form to register</p>
                                            <form:form method="POST" action="${pageContext.request.contextPath}/addUser"
                                                       modelAttribute="user">
                                                <form>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   type="text" placeholder="Name"
                                                        <form:input
                                                                path="name"/></div>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   type="email" placeholder="Email"
                                                        <form:input
                                                                path="email"/></div>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   type="password"
                                                                                   placeholder="Password"
                                                        <form:input path="password"/></div>
                                                    <div class="form-group"><input class="form-control form-control-lg"
                                                                                   type="password"
                                                                                   placeholder="Password Confirm"
                                                        <form:input path="passwordConfirm"/></div>
                                                    <input type="submit" value="Submit"
                                                           class="btn btn-outline-light btn-block"/>
                                                </form>
                                            </form:form>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>


                            <!-- Login in
                            <div id="login">
                                <h3>Log in</h3>
                                <p>Please fill out this form to start again.</p>
                                <form>
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-lg"
                                               placeholder="Username">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control form-control-lg"
                                               placeholder="Password">
                                    </div>
                                    <input type="button" value="Sign Up" id="btn_log-in"
                                           class="btn btn-outline-light btn-block">
                                    <input type="submit" value="Submit" class="btn btn-outline-light btn-block">
                                </form>
                            </div> -->


                            <!-- <form>
                               <div class="form-group">
                                 <input type="text" id="txt_user" class="form-control form-control-lg" placeholder="Username">
                               </div>
                               <div class="form-group">
                                 <input type="email" id="txt_email" class="form-control form-control-lg" placeholder="Email">
                               </div>
                               <div class="form-group">
                                 <input type="password" id="txt_password" class="form-control form-control-lg" placeholder="Password">
                               </div>
                               <div class="form-group">
                                 <input type="password" id="txt_pw_confirm" class="form-control form-control-lg" placeholder="Confirm Password">
                               </div>
                               <input type="submit" id="btn_register" value="Submit" class="btn btn-outline-light btn-block">
                             </form> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</header>

<c:choose>
    <c:when test="${status =='useduser'}">
        <!-- Modal -->
        <div class="modal fade" id="useduser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Username has already been taken!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        The username ${name} has already been taken. Please choose another one.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#useduser').modal('show');
        </script>
    </c:when>
    <c:when test="${status =='usedmail'}">
        <!-- Modal -->
        <div class="modal fade" id="usedmail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Email Address has already been taken!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        This Email Address ${email} has already been taken. Please choose another one or delete your old
                        acount.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#usedmail').modal('show');
        </script>
    </c:when>
    <c:when test="${status =='emptyform'}">
        <!-- Modal -->
        <div class="modal fade" id="emptyform" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Empty Form</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        To register, you have to fill out this form entirely.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#emptyform').modal('show');
        </script>
    </c:when>
</c:choose>

<!--Modal names
  - mdl_sucreg
  - mdl_useduser
  - mdl_usedemail

  - alt_email
  - alt_pw-->

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
<!--
<div class="modal fade text-dark" id="contactModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="contactModalTitle">
                    Contact Us
                </h5>
            </div>
            <div class="modal-body">
               <!-- <form>
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="message">Message</label>
                        <textarea class="form-control"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btn-block">Submit</button>
            </div>
        </div>
    </div>
</div>
-->
<!--
<script src="../../resources/js/jquery.min.js"></script>
<script src="../../resources/js/popper.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/res/js/main.js"></script>  -->


</body>

</html>