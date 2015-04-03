//= require libs/urlize
//= require_self

/**
 * URL AUTO LINKER by urlize
 * @param node
 */
var autoLinker = function(node) {
    $(node).contents().each(function() {
        var $this = $(this);
        if(this.nodeType == this.TEXT_NODE) {
            $this.replaceWith(urlize(this.textContent, true, true));
        } else {
            if(!$this.is('a, pre, code')) {
                autoLinker(this);
            } else if($this.is('a[href][target!="_blank"]')) {
                
                var contents = $(this).contents();
                console.log(contents);
                
                if(contents.length == 1 
                    && contents[0].nodeType == this.TEXT_NODE) {
                    
                    var prevNode = $('<div/>').append($(this).clone()).html();

                    $(this).replaceWith(prevNode + ' <a href="' + $this.attr('href') + '" target="_blank" title="새창으로 열기">' +
                    '<i class="fa fa-external-link"></i></a>');
                }
            }
        }
    });
};

var whereToPlacePopover = function(){
    if ($(window).width()<768) return 'bottom';
    return 'right';
};

if(!String.prototype.trim) {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/gm,'');
    }
}