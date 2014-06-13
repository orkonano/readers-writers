/**
 * Created by orko on 08/03/14.
 */

$(function () {

    $(document).on('click',"#id-follow-link",function(e) {
        e.preventDefault();
        e.stopPropagation();
        var idTemplate = $(this).data("template-id-next-action");
        $.rwJsonPostAjax({
            url: $(this).attr("href"),
            $element: $(this),
            success: function(data){
                successSwitch.apply(this,[data, idTemplate]);
            }
        });
    });

    $(document).on('click',"#id-leavefollow-link",function(e) {
        e.preventDefault();
        e.stopPropagation();
        var idTemplate = $(this).data("template-id-next-action");
        $.rwJsonDeleteAjax({
            url: $(this).attr("href"),
            $element: $(this),
            success:  function(data){
                successSwitch.apply(this,[data, idTemplate]);
            }
        });
    });
});

