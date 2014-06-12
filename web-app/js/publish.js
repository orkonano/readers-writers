/**
 * Created by orko on 6/11/14.
 */

$(function () {

    $("#id-publish-boton").click(function(e){
        e.preventDefault();
        e.stopPropagation();
        $.rwJsonPostAjax({
            url: $(this).attr("href"),
            $element: $(this),
            success: function(data){
                if (data.success){
                    $("#id-editar-boton").remove();
                    $("#id-eliminar-boton").remove();
                    $("#id-publish-boton").remove();
                }
            }
        });
    });

});