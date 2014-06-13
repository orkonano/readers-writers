/**
 * Created by orko on 6/13/14.
 */

$.fn.confirmModal = function(options) {
    var defaultSetting = {
        title: "Confirmar acción",
        body: "Confirme por favor",
        'id-boton-confirm': "id-boton-confirm"
    }
    var settings = $.extend(defaultSetting, options );
    var template = $('#template-confirm-modal').html();
    var html = Mustache.render(template, settings);

    $(this).html(html);
    $(this).addClass("modal").addClass("fade");
    $(this).modal('show');
    return this;
};