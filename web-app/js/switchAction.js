/**
 * Created by orko on 08/03/14.
 */

function executeAjaxAction($element, event){
    event.preventDefault();
    if (!$element.hasClass("ajaxRunning")){
        var url = $element.attr('href');
        var idTemplate = $element.data("template-id-next-action");
        var method = $element.data("method");
        var data = {};
        if (typeof method != 'undefined'){
            data = {"_method": method}
        }
        $.ajax({
            type: "POST",
            url: url,
            dateType: 'JSON',
            data: data,
            beforeSend: function(){
                $element.addClass("ajaxRunning");
            },
            success:function(data){
                if (data.success){
                    if (typeof data.errors == 'undefined'){
                        var template = $('#'+idTemplate).html();
                        var html = Mustache.render(template, data.view);
                        $("#id-buttons-region").html(html);
                    }else{
                        alert(data.errors);
                    }
                }
            },
            complete:function(){
                $element.removeClass("ajaxRunning");
            },
            error: function(jqXHR, textStatus, errorThrown ){
                alert(textStatus);
            },
            statusCode: {
                404: function() {
                    alert( "page not found" );
                },
                401: function(){
                    showLogin();
                }
            }
        });
    }
}