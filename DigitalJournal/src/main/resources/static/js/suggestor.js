$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

function completeSuggestion(suggestion) {
    $('#friend_name').val(suggestion);
    $('#btn_add_friend').click();
}

function fire_ajax_submit() {
  var search = $("#friend_name").val();
  $("#btn-search").prop("disabled", true);
  if(search != null && search !== ""){
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
          result += "<form>" +
              "<button class='btn btn-link btn_sug text-muted' onclick='completeSuggestion(\""+json[i]+"\")' type='button' id='btn_sug_" + i + "'>"  +json[i] + "</button>" +
              "</form>";
        }

        if(json.length > 0)
          result += "<hr>";

        $('#feedback').html(result);
      },
      error: function (e) {
        //TODO error handling
      }
    });
  }else{
      $('#feedback').html("");
  }
}
