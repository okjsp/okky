$(function() {
    var sending = false,
        _cancelText = ' 취소';

    var scrapClick = function () {
        var data = $(this).data();

        if(data.type == 'unscrap') {
            if(!confirm('취소하시겠습니까?')) {
                return false;
            }
        }

        if (!sending) {
            var $form = $('#article-scrap-form'),
                formParams = $form.serialize(),
                params = $.param({'contentId': data.id});

            sending = true;

            $.ajax({
                url: $form.attr('action') + '.json',
                dataType: 'json',
                type: $form.attr('method'),
                data: formParams + '&' + params,
                success: function (result) {

                    $('#article-scrap-count').html(result.scrapCount);

                    scrapCallback($.extend({},data));

                    sending = false;
                }
            });
        }

        return false;
    };

    var scrapCallback = function(data) {
        var $icon = $('#article-scrap-btn').find('i'),
            oppositeType = data.type == 'scrap' ? 'unscrap' : 'scrap';

        if(data.type == 'scrap') $icon.addClass('note-scrapped');
        else $icon.removeClass('note-scrapped');

        $icon.attr('data-original-title', function (i, value) {
            if (data.type == 'vote')
                return value + _cancelText;
            else
                return value.replace(_cancelText, '');
        }).tooltip('show');

        $icon.parent('a').data('type', oppositeType);
    };


    $('#article-scrap-btn').click(scrapClick);
});