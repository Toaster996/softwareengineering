<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">

<head>
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <!-- <link rel="stylesheet" href="../../resources/css/font-awesome.min.css">
     <link rel="stylesheet" href="../../resources/css/style.css">

     <link rel="stylesheet" href="../../resources/css/bootstrap.css">
     <link rel="stylesheet" href="../../resources/css/style.css"> -->
    <spring:url value="/resources/css/bootstrap.css" var="bootCSS" />
    <spring:url value="/resources/css/style.css" var="styleCSS" />
    <spring:url value="/resources/css/font-awesome.min.css" var="fontCSS" />
    <link href="${styleCSS}" rel="stylesheet" />
    <link href="${bootCSS}" rel="stylesheet" />
    <link href="${styleCSS}" rel="stylesheet" />
    <link href="${fontCSS}" rel="stylesheet" />

    <title>Registration | DJ</title>
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
                        <div class="card bg-primary text-center card-form pb-4">
                            <div class="card-body">
                                <h3>Welcome, ${name}! </h3>
                                <p>We sent you an email to your address ${email}. Please click on the link you can find there within 24 hours.</p>
                                </div>
                                <input type="submit" value="Create your Journal!" href="#" class="btn btn-outline-light mr-3 ml-3">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>


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
                 </form> -->
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary btn-block">Submit</button>
            </div>
        </div>
    </div>
</div>

<!--
<script src="../../resources/js/jquery.min.js"></script>
<script src="../../resources/js/popper.min.js"></script>
<script src="../../resources/js/bootstrap.min.js"></script>
<script src="../../resources/res/js/main.js"></script>  -->

<spring:url value="/resources/js/jquery.js" var="jQuery" />
<spring:url value="/resources/js/popper.min.js" var="popper" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrap" />
<spring:url value="/resources/res/js/main.js" var="main" />
<script src="${jQuery}"></script>
<script src="${popper}"></script>
<script src="${bootstrap}"></script>
<script src="${main}"></script>

</body>

</html>