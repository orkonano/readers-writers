/**
 * Created by orko on 08/03/14.
 */

function executeAjaxAction($element,event){
    event.preventDefault();
    if (!$element.hasClass("ajaxRunning")){
        var url = $element.data("url");
        var objectId = $element.data("object-id");
        url += "/" + objectId;
        var idTemplate = $element.data("template-id-next-action");
        $.ajax({
            type: "POST",
            url: url,
            dateType: 'JSON',
            beforeSend: function(){
                $element.addClass("ajaxRunning");
            },
            success:function(data){
                if (data.success){
                    if (typeof data.errors == 'undefined'){
                        var template = $('#'+idTemplate).html();
                        var html = Mustache.render(template, null);
                        $("#id-buttons-region").html(html);
                    }else{
                        alert(data.errors);
                    }
                }
            },
            complete:function(){
                $element.removeClass("ajaxRunning");
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