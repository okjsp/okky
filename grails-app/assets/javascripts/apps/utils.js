//= require libs/urlize
//= require_self

/**
 * URL AUTO LINKER by urlize
 * @param node
 */
var autoLinker = function(node) {
    $(node).contents().each(function() {
        if(this.nodeType == this.TEXT_NODE) {
            $(this).replaceWith(urlize(this.textContent, true, true));
        } else {
            if(!$(this).is('a, pre, code')) autoLinker(this);
        }
    });
};