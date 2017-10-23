var DualAuth = (function(app,$){
		app.progressCollector = [];
		app.progressCollectorClear = function() {
			for (var i=0; i < DualAuth.progressCollector.length; i++) {
				clearInterval(DualAuth.progressCollector[i]);	
			}
		}
		return app;
	})(DualAuth||{},jQuery);
(function ($) {
    $.fn.progressTimer = function (options) {
		var settings = $.extend({}, $.fn.progressTimer.defaults, options);

        this.each(function () {
        	var obj = $(this);
            $(this).empty();
            var barContainer = $("<div>").addClass("progress active");
            var bar = $("<div>").addClass("progress-bar").addClass(settings.baseStyle)
                .attr("role", "progressbar")
                .attr("aria-valuenow", "0")
                .attr("aria-valuemin", "100")
				.attr("style", "width:100%")
                .attr("aria-valuemax", settings.timeLimit);

            bar.appendTo(barContainer);
            barContainer.appendTo($(this));
            var start = ((!settings.startDate) ? new Date() : settings.startDate); 
            var limit = settings.timeLimit * 1000;
             
            var interval = window.setInterval(function () {            
                var elapsed = new Date() - start;
                bar.width((((limit - elapsed) / limit) * 100) + "%");

                if (limit - elapsed <= 5000)
                    bar.removeClass(settings.baseStyle)
                       .removeClass(settings.completeStyle)
                       .addClass(settings.warningStyle);

                if (elapsed >= limit) {
                    window.clearInterval(interval);
                	
                    bar.removeClass(settings.baseStyle)
                       .removeClass(settings.warningStyle)
                       .addClass(settings.completeStyle);

                    settings.onFinish.call(this);
                }
               
            }, 250);
            DualAuth.progressCollector.push(interval);
            
        });

        return this;
    };

    $.fn.progressTimer.defaults = {
        timeLimit: 60,  //total number of seconds
        warningThreshold: 5,  //seconds remaining triggering switch to warning color
        onFinish: function () {},  //invoked once the timer expires
		baseStyle: '',  //bootstrap progress bar style at the beginning of the timer
        warningStyle: 'progress-bar-danger',  //bootstrap progress bar style in the warning phase
        completeStyle: 'progress-bar-success'  //bootstrap progress bar style at completion of timer
    };
}(jQuery));
