$(function() {

    var currentEditingNoteId,
        sending = false;

    $('#note-create').focus(function() {
        var $editor = $(this);
        $(this).summernote({lang: 'ko-KR',
            focus: true,
            onInit: function() {
                if($(window).height() > 400)
                    $('#note-create-cancel-btn').show();
                    $('#btn-create-btn').prop('disabled', false);
                    $editor.find('.note-editable').css('max-height', $(window).height()-100);
            },
            onImageUpload: $.onImageUpload($editor)
        });
    });

    $('.note-create-form').submit(function() {

        if(sending) return false;

        var $form = $(this),
            $textarea = $form.find('textarea[name="note.text"]'),
            code = $('#note-create').code();

        $textarea.val(code);

        if(code == '' || code == '<p><br></p>') {
            alert('내용을 입력해 주세요.');
            $('#note-create').summernote({focus:true});
            return false;
        }

        sending = true;

        return true;
    });

    $('#note-create-cancel-btn').click(function() {
        var code = $('#note-create').code();
        if(code == '' || code == '<p><br></p>'
            || confirm('작성중 이던 내용을 취소하시겠습니까?')) {
            closeNoteCreate();
        }
    });

    var closeNoteCreate = function(id) {
        $('#note-create-cancel-btn').hide();
        $('#btn-create-btn').prop('disabled', true);
        $('#note-create').destroy().val('');
    };

    var closeNoteEdit = function(id) {
        $('#note-edit-'+id).destroy().detach();
        $('#note-text-'+id).show();
        $('#content-function-cog-'+id)
            .find('.buttons').hide()
            .end().find('.dropdown').show();
    };

    var closeCurrentNoteEdit = function() {
        if(confirm('수정중 이던 내용을 취소하시겠습니까?')) {
            closeNoteEdit(currentEditingNoteId);
            currentEditingNoteId = null;
        }
    };

    $('.note-edit-cancel-btn').click(closeCurrentNoteEdit);

    $('.note-edit-btn').click(function() {

        if(sending) return false;

        var id = $(this).data('id');

        if(currentEditingNoteId)
            closeCurrentNoteEdit();

        currentEditingNoteId = id;

        $('#content-function-cog-'+id)
            .find('.dropdown .fa').addClass('fa-spin');

        sending = true;

        $.ajax({
            url:contextPath+'/content/edit/'+id+'.json?_='+(new Date().getTime()),
            dataType: 'json',
            type: 'get',
            success: function(result) {
                $('#note-text-' + id).hide();
                $('#content-function-cog-' + id)
                    .find('.buttons').show()
                    .end().find('.dropdown').hide()
                    .find('.fa').removeClass('fa-spin');

                $('<textarea id="note-edit-' + id + '" name="text"></textarea>')
                    .val(result.text)
                    .insertAfter('#note-text-' + id)
                    .summernote({lang: 'ko-KR',
                        focus: true,
                        oninit: function () {
                            if ($(window).height() > 400)
                                $('.note-editable').css('max-height', $(window).height() - 100);
                        }
                    }
                );
            },
            complete: function() {
                sending = false;
            }
        });
    });

    $('.note-update-form').submit(function() {

        if(sending) return false;

        var id = $(this).data('id'),
            $form = $(this);

        $form.find('textarea[name="text"]').val($('#note-edit-'+id).code());

        sending = true;

        $.ajax({
            url: $form.attr('action') + '.json',
            dataType: 'json',
            type: $form.attr('method'),
            data: $form.serialize(),
            success : function(result) {
                $('#note-text-'+id).html(result.text)
                    .find('code').each(function(i ,el) {
                        Prism.highlightElement(el);
                    });
                closeNoteEdit(id);
                currentEditingNoteId = null;
            },
            complete: function() {
                sending = false;
            }
        });

        return false;
    });

    $('.note-delete-btn').click(function() {

        if(sending) return false;

        if(confirm('정말로 삭제 하시겠습니까?')) {

            var id = $(this).data('id'),
                $form = $('#note-delete-form-'+id);

            sending = true;

            $.ajax({
                url: $form.attr('action') + '.json',
                dataType: 'json',
                type: $form.attr('method'),
                data: $form.serialize(),
                success : function(result) {
                    $('#note-'+id).detach();
                    $('#note-count').text(function(i, count) {
                        return parseInt(count, 10)-1;
                    });
                },
                complete: function() {
                    sending = false;
                }
            });
        }
    });
});