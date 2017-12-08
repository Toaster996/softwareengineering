<%--
  Created by IntelliJ IDEA.
  User: jonas
  Date: 08/12/2017
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${contactStatus =='mdl_email_invalid'}">
        <!-- Modal -->
        <div class="modal fade" id="email_invalid" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Email invalid!</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <strong>Email invalid!</strong> Please retry with an valid Email.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#mdl_email_invalid').modal('show');
        </script>
    </c:when>

    <c:when test="${contactStatus =='emptyform'}">
        <!-- GeModal -->

        <div class="modal fade" id="emptyform" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Empty Form</h5>
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


    <c:when test="${contactStatus =='requestSent'}">
        <!-- Modal -->
        <div class="modal fade" id="mdl_email_sent" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">Successful request</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <strong>Your request has been submited</strong> We are looking forward reading your questions.
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#mdl_email_sent').modal('show');
        </script>
    </c:when>

    <c:when test="${contactStatus =='temp_modal'}">
        <!-- Modal -->
        <div class="modal fade" id="${modal_gen_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content text-black">
                    <div class="modal-header">
                        <h5 class="modal-title">${temp_modal_header}</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                            ${temp_modal_body}
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $('#${modal_gen_id}').modal('show');
        </script>
    </c:when>
</c:choose>
