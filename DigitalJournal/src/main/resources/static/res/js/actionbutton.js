$(function(){
    var fabIsOpen = false;
    $(".fab,.backdrop_gray").click(function(){
        if(fabIsOpen){
            console.log("Not show");

            fabIsOpen = false;
            $(".backdrop_gray").fadeOut(125);
            $(".fab.child")
                .stop()
                .animate({
                    bottom	: $("#masterfab").css("bottom"),
                    opacity	: 0
                },125,function(){
                    $(this).hide();
                });
        }else{
            console.log("Show");
            fabIsOpen = true;
            $(".backdrop_gray").fadeIn(125);
            $(".fab.child").each(function(){
                $(this)
                    .stop()
                    .show()
                    .animate({
                        bottom	: (parseInt($("#masterfab").css("bottom")) + parseInt($("#masterfab").outerHeight()) + 70 * $(this).data("subitem") - $(".fab.child").outerHeight()) + "px",
                        opacity	: 1
                    },125);
            });
        }
    });
});