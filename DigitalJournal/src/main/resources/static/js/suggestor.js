$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

function fire_ajax_submit() {

  var search = $("#friend_name").val();

  $("#btn-search").prop("disabled", true);

  if(search != null){
    $.ajax({
      type: "POST",
      contentType: "application/text",
      url: "/journal/friends/suggest/"+search,
      cache: false,
      timeout: 600000,
      success: function (data) {
        var result = "";
        var json = data;

        for(var i = 0; i < json.length; i++){
          result += "<form action='/journal/friends/add/" + json[i] + "'>" +
              "<button class='btn btn-link' type='submit'>"  +json[i] + "</button>" +
              "</form>";
        }

        result += "<hr>";
        $('#feedback').html(result);
      },
      error: function (e) {
        //TODO error handling
      }
    });
  }

}
