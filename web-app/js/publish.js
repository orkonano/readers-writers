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

    $(".boton-confirm").on('click', function(e){
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
        var id = $(this).data('id');
        $('#id-modal-confirm-delete').find(".modal-header").find("h3").text("Confirmación eliminación");
        var tituloTelling = $("#id-title-telling").text();
        $('#id-modal-confirm-delete').find(".modal-body").html("<p>¿Está seguro que desea eliminar la historia <b>'"+tituloTelling+ "'</b> ?</p>");
        $('#id-modal-confirm-delete').modal('show');
    });

});