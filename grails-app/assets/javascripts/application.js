// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require bootstrap
//= require libs/bootstrap-tagsinput
//= require libs/summernote
//= require libs/summernote-ko-KR
//= require libs/summernote-ext-video
//= require libs/summernote-ext-fontstyle
//= require libs/summernote-ext-codeblock
//= require libs/spin
//= require libs/jquery.spin
//= require libs/placeholder_polyfill.jquery
//= require libs/mustache
//= require libs/jquery.timeago
//= require libs/jquery.timeago-ko-KR
//= require apps/utils
//= require_self


if (typeof jQuery !== 'undefined') {
	(function($) {
        $('.timeago').timeago();

        $('[data-toggle="tooltip"]').tooltip();

        $.fn.spin.presets = {
            tiny: { lines: 8, length: 1, width: 2, radius: 4, color:'#56595c' },
            small: { lines: 11, length: 0, width: 3, radius: 10, shadow: true, hwaccel: true, color:'#56595c' },
            large: { lines: 10, length: 8, width: 4, radius: 8, color:'#56595c' }
        };

        $.ajaxSetup({
            statusCode: {
                401: function() {
                    location.href = contextPath+'/login/auth?redirectUrl='+encodedURL;
                }
            }
        });
        
        $('.sidebar-header').click(function() {
            if($('.sidebar').is('.open')) {
                $('.sidebar').removeClass('open');
                $('.sidebar-category-nav').removeClass('open');
            } else {
                $('.sidebar').addClass('open');
                $('.sidebar-category-nav').addClass('open');
            }
        });
        
        $('html, body').click(function(e) {
            if(!$(e.target).is('.sidebar-header  *, .nav-main *, .sidebar-category-nav *, #search-google-form *')) {
                $('.sidebar').removeClass('open');
                $('.sidebar-category-nav').removeClass('open');
            }
            
            if(!$(e.target).is('#search-google *, #search-google-popover *')) {
                $('#search-google').popover('hide');
            }
        });
        
        var onImagaUpload = function(files, editor, $editable) {

            var $form = $('.note-image-dialog .note-modal-form');

            $('<iframe src="about:blank"  style="display: none;" name="imageUploadHandlerFrame"></iframe>').appendTo('body');

            $.imageUploaded = function(image) {
                editor.insertImage($editable, image);
            };

            $form.attr({
                enctype: 'multipart/form-data',
                target: 'imageUploadHandlerFrame',
                action: contextPath+'/file/image',
                method: 'post'
            });

            $form[0].submit();
        };

        $.extend($.summernote.options, {
            lang: 'ko-KR',
            height: 300,                  // set editable area's height
            tabsize: 2,                   // size of tab
            placeholder: '내용을 입력해 주세요.', // set editable area's placeholder text
            prettifyHtml: false,
            // toolbar
            toolbar: [
                ['style', ['style']],
                ['font', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                ['color', ['color']],
                ['para', ['ul', 'ol', 'table']],
                ['insert', ['codeblock', 'link', 'picture', 'video', 'hr']],
                ['view', ['fullscreen', 'codeview', 'help']]
            ],
            onImageUpload : onImagaUpload
        });

	})(jQuery);
}