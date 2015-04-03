$(function() {
    
    var title = document.title.replace(/^\([0-9]+\) /, '');

    var notificationText = {
        'NOTED' : '이 <strong>#{{article.id}}</strong> 에 댓글을 남겼습니다.',
        'NOTED_AFTER' : '도 게시물 <strong>#{{article.id}}</strong> 에 댓글을 남겼습니다.',
        'ASSENTED_ARTICLE' : '이 회원님의 게시물 <strong>#{{article.id}}</strong> 를 추천 하였습니다.',
        'ASSENTED_NOTE' : '이 게시물 <strong>#{{article.id}}</strong> 에 작성하신 회원님의 댓글을 추천 하였습니다.'
    };

    $('#user-notification').on('shown.bs.popover', function () {
        $('#notification-popover').spin('small');
        $.ajax({
            url : contextPath+'/notification.json',
            dataType: 'json',
            success : function(data) {
                var notificationsHtml = '';

                toggleNotificationCount();

                $(data.notifications).each(function(i, notification) {
                    var senders = '', date, text = Mustache.render(notificationText[notification.type], notification);

                    /*var sendersCount = notification.sendersCount > 2 ? 2 : notification.sendersCount;

                    for(var i2 = 0; i2 < sendersCount; i2++) {
                        var sender = notification.senders[i2];
                        if(i2 != 0) senders += ', ';
                        senders += '<span class="avatrar-nickname">'+sender.nickname+'</span>';
                    }

                    if(notification.fromAvatars > 2) {
                        senders += '님 외 <strong>'+(notification.sendersCount-2)+'명</strong>';
                    } else {
                        senders += '님';
                    }*/

                    senders += notification.sender.nickname+'님';

                    date = ' <span class="timeago pull-right" title="'+notification.lastUpdated+'">'+notification.lastUpdated+'</span>'

                    var read = new Date(notification.lastUpdated) < new Date(data.lastRead);

                    notificationsHtml += '<div class="notification-link clearfix '+ (read ? 'notification-accepted' : '')+'" data-article="'+notification.article.id+'" data-content="'+notification.content.id+'">'+senders+text+date+'</div>';
                });

                $('#notification-popover').html(notificationsHtml);

                $('#notification-popover .timeago').timeago();
            }
        });
    });

    $('#user-func').popover({
        container: 'body',
        title: '설정',
        placement: whereToPlacePopover,
        template: $('#setting-template').html(),
        html: true
    });

    $('#user-notification').popover({
        container: 'body',
        title: '알림',
        placement: whereToPlacePopover,
        template: $('#notification-template').html(),
        html: true
    });

    $(document).delegate('#notification-popover .notification-link', 'click' , function(e) {
        var data = $(this).data();
        location.href = contextPath+'/article/'+data.article+'?note='+data.content;
    });

    var notificationInterval = setInterval(function() {
        $.ajax({
            url: contextPath+'/notification/count.json',
            dataType: 'json',
            success: function(data) {
                toggleNotificationCount(data.count);
            },
            error: function() {
                clearInterval(notificationInterval);
            }
        });
    }, 1000 * 60 * 10);
    
    var toggleNotificationCount = function(count) {
        if(count) {
            $('#user-notification-count').html(count).show();
            $('#user-notification-icon').hide();
            document.title = '('+count+') ' + title;
        } else {
            $('#user-notification-count').html(0).hide();
            $('#user-notification-icon').show();
            document.title = title;
        }
    };
});