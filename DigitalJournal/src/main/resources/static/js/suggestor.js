$(function () {
  var token = $("meta[name='_csrf']").attr("content");
  var header = $("meta[name='_csrf_header']").attr("content");
  $(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
  });
});

function fire_ajax_submit() {

  var search = $("#friend_name").val();

  console.log("ajajja");

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
          result += json[i] + "<br>";
        }

        result += "<br>";

        $('#feedback').html(result);
      },
      error: function (e) {
        var result = "";
        var jsonText = e.responseText;
        var json = JSON.parse(jsonText);



        json.forEach(function(obj) { result+=obj.valu; });

        $('#feedback').html(result);
      }
    });
  }

}
