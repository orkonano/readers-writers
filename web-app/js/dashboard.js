/**
 * Created by orko on 3/15/14.
 */

$(function () {

   loadTellingLike();

});

function loadTellingLike(){
    loadElementDashboard("template-telling-like", "historias-propias-id", "likeTelling",
    {"renderMethod": function(template, data, index){
        return Mustache.render(template, data.model.tellings[index]);
    }});
}

function loadElementDashboard(idTemplate, idElementUpdate,action,ajaxSettings){
    $.ajax({
        type: "POST",
        url: relativeUrl+'panel/'+action,
        dateType: 'JSON',
        beforeSend: function(){
        },
        success:function(data){
            if (data.success){
                var template = $('#'+idTemplate).html();
                for (var i = 0; i< data.model.tellings.length; i++){
                    var html = ajaxSettings.renderMethod.call(this,template,data,i);
                    $("#"+idElementUpdate).append(html);
                }
            }else{
                alert("Ocurrió un error");
            }
        },
        complete:function(){
        }
    });
}