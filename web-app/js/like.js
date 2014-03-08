/**
 * Created by orko on 08/03/14.
 */

$(function () {

    $(document).on('click',"#id-like-link",function(e) {
        executeAjaxAction($(this),e);
    });

    $(document).on('click',"#id-stop-like-link",function(e) {
        executeAjaxAction($(this),e);
    });
});