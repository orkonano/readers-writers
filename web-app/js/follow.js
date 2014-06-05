/**
 * Created by orko on 08/03/14.
 */

$(function () {

    $(document).on('click',"#id-follow-link",function(e) {
        executeAjaxAction($(this),e);
    });

    $(document).on('click',"#id-leavefollow-link",function(e) {
        executeAjaxAction($(this), e);
    });
});




