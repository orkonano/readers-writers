/**
 * Created by orko on 05/02/14.
 */
$(function() {

    $("[data-action='logout']").click(function(e){
        e.preventDefault();
        if (!$(this).hasClass("ajaxRunning")){
            var url = $(this).data("url-logout");
            var redirect = $(this).data("url-redirect");
            $.ajax({
                type: "POST",
                url: url,
                beforeSend: function(){
                    $(this).addClass("ajaxRunning");
                },
                success:function(data){
                    window.location.assign(redirect);
                },
                complete:function(){
                    $(this).removeClass("ajaxRunning");
                }
            });
        }
    });
});