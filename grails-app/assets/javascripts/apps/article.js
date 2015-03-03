//= require libs/prism
//= require apps/note
//= require apps/vote
//= require apps/scrap
//= require_self

$(function() {
    $('#article-delete-btn').click(function () {
        if (confirm('정말로 삭제하시겠습니까?')) {
            $('#article-delete-form').get(0).submit();
        }
    });

    $('.btn-facebook-share').click(function(e) {
        var w = 800,
            h = 500,
            left = (window.screen.width / 2) - ((w / 2)),
            top = (window.screen.height / 2) - ((h / 2) + 50);

        window.open(this.href, 'fbshare_pop',"width="+w+",height="+h+",left="+left+",top="+top+",scrollbars=no,resizable=no");
        e.preventDefault();
    });
    
    $('article.content-text, article.note-text').each(function() {
        autoLinker(this);
    });
});