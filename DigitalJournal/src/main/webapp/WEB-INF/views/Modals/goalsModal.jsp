<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 11/04/2018
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
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
                                       placeholder="Goal" <form:input path="name"/>
                            </div>
                            <div class="form-group">
                                <input type="date" id="lbl_date"
                                       class="form-control form-control-lg"
                                       placeholder="Date" <form:input
                                    path="date"/>
                            </div>
                            <input type="submit" value="Log In"
                                   class="btn btn-outline-light btn-block">


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
</c:choose>