$(function() {

    var sending = false,
        _cancelText = ' 취소';

    var voteClick = function (e) {
        var data = $(this).data();

        if(data.type == 'unvote') {
            if(!confirm('취소하시겠습니까?')) {
                return false;
            }
        }

        if (data.type != 'disabled') {
            if (!sending) {
                var $form = $('#note-' + data.type + '-form'),
                    formParams = $form.serialize(),
                    params = $.param({'contentId': data.id});

                sending = true;

                $.ajax({
                    url: $form.attr('action') + '.json',
                    dataType: 'json',
                    type: $form.attr('method'),
                    data: formParams + '&' + params,
                    success: function (result) {

                        $('#content-vote-count-' + data.id).html(result.voteCount);

                        if (data.eval) {
                            evaluateCallback($.extend({},data));
                        } else {
                            voteCallback($.extend({},data));
                        }

                        sending = false;
                    }
                });
            }
        }
        return false;
    };

    $('.note-vote-btn').click(voteClick);

    var evaluateCallback = function (data) {
        $(['assent','dissent']).each(function(i, type) {
            var $icon = $('#note-evaluate-' + type + '-' + data.id),
                className = 'note-evaluate-' + type + '-'+type,
                oppositeClassName = 'note-evaluate-' + type + '-unvote';

            if(data.type == 'unvote') {
                oppositeClassName = 'note-evaluate-' + type + '-unvote ' +
                    'note-evaluate-' + type + '-disabled';
            } else if(data.type != type) {
                className = 'note-evaluate-' + type + '-disabled';
                oppositeClassName = 'note-evaluate-' + type +'-'+type;
            }

            $icon.removeClass(data.type == type ? className : oppositeClassName)
                .addClass(data.type == type ? oppositeClassName : className)
                .attr('data-original-title', function (i, value) {
                    if (data.type == type)
                        return value + _cancelText;
                    else if(!value) {}
                    else
                        return value.replace(_cancelText, '');
                });

            if(data.type == type) {
                $icon.parent('a').data('type', 'unvote');
                $icon.tooltip('show');
            } else if(data.type == 'unvote') {
                $icon.parent('a').data('type', type);
                $icon.tooltip('hide');
            } else {
                $icon.parent('a').data('type', 'disabled');
                $icon.tooltip('hide');
            }
        });
    };

    var voteCallback = function (data) {
        var $icon = $('#note-vote-' + data.id),
            oppositeType = data.type == 'vote' ? 'unvote' : 'vote';

        $icon.removeClass('note-' + data.type)
            .addClass('note-' + oppositeType)
            .attr('data-original-title', function (i, value) {
                if (data.type == 'vote')
                    return value + _cancelText;
                else
                    return value.replace(_cancelText, '');
            })
            .tooltip('show');

        $icon.parent('a').data('type', oppositeType);
    };
});