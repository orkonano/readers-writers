/**
 * Created by orko on 05/02/14.
 */
$(function() {

    $("#id-auth-button").click(function(e){
        e.preventDefault();
        showLogin();
    });

    $("#id-login-button").click(function(e){
        e.preventDefault();
        var $form = $("#ajaxLoginForm");
        var url = $(this).data("url-login");
        var redirect = $(this).data("url-redirect");
        $.ajax({
            type: "POST",
            url: url,
            data: $form.serialize(),
            beforeSend: function(){
                $(this).addClass("ajaxRunning");
            },
            success:function(data){
                if (data.success){
                    window.location.assign(redirect);
                }else{
                    $("#id-login-error").html(data.error);
                    $("#id-login-error").show();
                }
            },
            error: function(jqXHR, textStatus, errorThrown){
              alert("error");
            },
            complete:function(){
                $(this).removeClass("ajaxRunning");
            }
        });
    });


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

function showLogin(){
    $('#auth-form').find(".form-control").val("");
    $('#auth-form').modal('show');
    $("#id-login-error").hide();
}

