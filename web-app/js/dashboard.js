/**
 * Created by orko on 3/15/14.
 */

$(function () {

   loadTellingLike();
   loadAuthorsFollowed();
});

function loadTellingLike(){
    loadElementDashboard("template-telling-like", "historias-like-id", "likeTelling");
}

function loadAuthorsFollowed(){
    loadElementDashboard("template-author-followed", "autores-seguidos-id", "authorsFollowed");
}

function loadElementDashboard(idTemplate, idElementUpdate,action){
    $.ajax({
        type: "POST",
        url: relativeUrl+'panel/'+action,
        dateType: 'JSON',
        beforeSend: function(){
        },
        success:function(data){
            if (data.success){
                var template = $('#'+idTemplate).html();
                for (var i = 0; i< data.model.elements.length; i++){
                    var html = Mustache.render(template, data.model.elements[i]);
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