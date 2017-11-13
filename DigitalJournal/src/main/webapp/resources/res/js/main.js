$(function(){
    alert('works');
    $('#register').hide();
    $('#btn_log-in').click(function(){
        $('#register').show();
        $('#login').hide();
    })
});