if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

$.rwJsonPostAjax = function(settings){
    var data = typeof settings.dataFunction != 'undefined' ?  settings.dataFunction.apply() : {};
    var $element = typeof settings.$element != 'undefined' ? settings.$element : null;
    if (!$element.hasClass('ajaxRunning') || $element == null){
        $.ajax({
            type: "POST",
            url: settings.url,
            dateType: 'JSON',
            data: data,
            beforeSend: function(){
                if ($element != null){
                    $element.addClass("ajaxRunning");
                }
                if (typeof settings.beforeSend != 'undefined'){
                    settings.beforeSend.apply();
                }
            },
            success: settings.success,
            complete:function(){
                if ($element != null){
                    $element.removeClass("ajaxRunning");
                }
                if (typeof settings.beforeSend != 'undefined'){
                    settings.complete.apply();
                }
            },
            error: function(jqXHR, textStatus, errorThrown ){
                alert(textStatus);
            },
            statusCode: {
                404: function() {
                    alert( "page not found" );
                },
                401: function(){
                    showLogin();
                }
            }
        });
    }
}