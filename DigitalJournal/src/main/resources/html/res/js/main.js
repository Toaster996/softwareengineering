$(function(){

    $('#form_login').hide();

    $('#btn_log').click(function(){
        $('#form_login').show();
        $('#div_btns').hide();
    });

    function animate (element, animation){
        $(element).addClass('animated ' + animation);
    }
});
