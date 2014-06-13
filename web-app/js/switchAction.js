/**
 * Created by orko on 08/03/14.
 */

function successSwitch(data, idTemplate){
    if (data.success){
        if (typeof data.errors == 'undefined'){
            var template = $('#'+idTemplate).html();
            var html = Mustache.render(template, data.view);
            $("#id-buttons-region").html(html);
        }else{
            alert(data.errors);
        }
    }
}