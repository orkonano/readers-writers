/**
 * Created by orko on 08/03/14.
 */

$(function () {


    $(document).on('click',"#id-follow-link",function(e) {
        executeAjaxAction($(this),e);
    });

    $(document).on('click',"#id-leavefollow-link",function(e) {
        executeAjaxAction($(this),e);
    });
});

function executeAjaxAction($element,event){
    event.preventDefault();
    if (!$element.hasClass("ajaxRunning")){
        var url = $element.data("url");
        var authorId = $element.data("author-id");
        var idTemplate = $element.data("template-id-next-action");
        $.ajax({
            type: "POST",
            url: url,
            dateType: 'JSON',
            data: {'id':authorId},
            beforeSend: function(){
                $element.addClass("ajaxRunning");
            },
            success:function(data){
                if (data.success){
                    var template = $('#'+idTemplate).html();
                    var html = Mustache.render(template, null);
                    $("#id-buttons-region").html(html);
                }else{
                    alert("Ocurrió un error");
                }
            },
            complete:function(){
                $element.removeClass("ajaxRunning");
            }
        });
    }
}


