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
                    $("#confirm-delete").remove();
                    $("#id-publish-boton").remove();
                }
            }
        });
    });

    $(document).on('click', '#id-boton-delete-confirm', function(e){
        e.preventDefault();
        e.stopPropagation();
        $.rwJsonDeleteAjax({
            url: $(this).attr("href"),
            $element: $(this),
            success: function(data){
                if (data.success){
                    $("#id-editar-boton").remove();
                    $("#confirm-delete").remove();
                    $("#id-publish-boton").remove();
                    $('#id-modal-confirm-delete').modal('hide');
                    $("#id-title-telling").addClass("alert").addClass("alert-danger").text($("#id-title-telling").text()+" BORRADO");
                }
            }
        });
    });

    $('#confirm-delete').click(function(e) {
        e.preventDefault();
        var tituloTelling = $("#id-title-telling").text();
        $('#id-modal-confirm-delete').confirmModal({
            title: "Confirmación de eliminación",
            body: "<p>¿Está seguro que desea eliminar la historia <b>'"+tituloTelling+ "'</b> ?</p>",
            'id-boton-confirm': "id-boton-delete-confirm"
        });
    });

});