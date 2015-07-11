(function (factory) {
  /* global define */
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define(['jquery'], factory);
  } else {
    // Browser globals: jQuery
    factory(window.jQuery);
  }
}(function ($) {
  // template, editor
  var tmpl = $.summernote.renderer.getTemplate();

  // core functions: range, dom
  var range = $.summernote.core.range;
  var dom = $.summernote.core.dom;

  /**
   * createCodeblockNode
   *
   * @member plugin.codeblock
   * @private
   * @param {String} langType
   * @param {String} isShowLineNumbers
   * @param {String} sourceCode
   * @return {Node}
   */
  var createCodeblockNode = function (langType, isShowLineNumbers, sourceCode) {
    var $sourceCode = $('<div/>').text(sourceCode);
    var $block = $('<pre class="' + (isShowLineNumbers ? 'line-numbers':'') + '">' +
        '<code class="language-' + langType + '">' + $sourceCode.html() + '</code></pre>');
    return $block[0];
  };


  var getCodeBlock = function ($editable) {
    $editable.focus();

    var rng = range.create().expand(dom.isCodeBlock);

    // Get the first anchor on range(for edit).
    var $pre = $(rng.nodes(dom.isPre)[0]);
    var $code = $(rng.nodes(dom.isCode)[0]);

    return {
      range: rng,
      text: rng.toString(),
      isShowLineNumbers: $pre.length ? $pre.attr('class') === 'line-numbers' : true,
      langType: $code.length ? $code.attr('class') : '',
      sourceCode: $code.length ? $code.text() : ''
    };
  };


  /**
   * toggle button status
   *  
   * @member plugin.codeblock
   * @private
   * @param {jQuery} $btn
   * @param {Boolean} isEnable
   */
  var toggleBtn = function ($btn, isEnable) {
    $btn.toggleClass('disabled', !isEnable);
    $btn.attr('disabled', !isEnable);
  };

  /**
   * Show codeblock dialog and set event handlers on dialog controls.
   *
   * @member plugin.codeblock
   * @private
   * @param {jQuery} $editable
   * @param {jQuery} $dialog
   * @param {Object} codeBlock
   * @return {Promise}
   */
  var showCodeblockDialog = function ($editable, $dialog, codeBlock) {
    return $.Deferred(function (deferred) {
      var $codeBlockDialog = $dialog.find('.note-codeblock-dialog');
      var $langType = $codeBlockDialog.find('.note-codeblock-type'),
          $showLineNumbers = $codeBlockDialog.find('.note-codeblock-numbers'),
          $sourceCode = $codeBlockDialog.find('.note-codeblock-source'),
          $codeBlockBtn = $codeBlockDialog.find('.note-codeblock-btn');

      $codeBlockDialog.one('shown.bs.modal', function () {
        $langType.val(codeBlock.langType).trigger('keyup').trigger('focus');

        $sourceCode.val(codeBlock.sourceCode).on('input', function () {
          toggleBtn($codeBlockBtn, $sourceCode.val());
        });

        $codeBlockBtn.click(function (event) {
          event.preventDefault();
          deferred.resolve($langType.val(), $showLineNumbers.is(':checked'), $sourceCode.val());
          $codeBlockDialog.modal('hide');
        });
      }).one('hidden.bs.modal', function () {
        $langType.off('keyup');
        $codeBlockBtn.off('click');

        if (deferred.state() === 'pending') {
          deferred.reject();
        }
      }).modal('show');
    });
  };


  /**
   * @class plugin.codeblock
   *
   * Codeblock Plugin
   *
   * Codeblock plugin is to make code tag with Prism.
   *
   * ### load script
   *
   * ```
   * < script src="plugin/summernote-ext-codeblock.js"></script >
   * ```
   *
   * ### use a plugin in toolbar
   * ```
   *    $("#editor").summernote({
     *    ...
     *    toolbar : [
     *        ['group', [ 'codeblock' ]]
     *    ]
     *    ...
     *    });
   * ```
   */
  $.summernote.addPlugin({
    /** @property {String} name name of plugin */
    name: 'codeblock',
    /**
     * @property {Object} buttons
     * @property {function(object): string} buttons.codeblock
     */
    buttons: {
      codeblock: function (lang) {
        return tmpl.button('<i class="fa fa-file-code-o"></i> ' + lang.codeblock.codeblock, {
          event: 'showCodeblockDialog',
          title: lang.codeblock.codeblock,
          hide: true
        });
      }
    },

    /**
     * @property {Object} dialogs
     * @property {function(object, object): string} dialogs.codeblock
     */
    dialogs: {
      codeblock: function (lang) {

        var body = '<div class="form-group">' +
            '<label>' + lang.codeblock.langType + '</label>' +
            '<select class="note-codeblock-type form-control span12">' +
            '<option selected="selected" value="markup">HTML / Markup</option>' +
            '<option value="css">CSS</option>' +
            '<option value="sass">Sass</option>' +
            '<option value="javascript">JavaScript</option>' +
            '<option value="java">Java</option>' +
            '<option value="python">Python</option>' +
            '<option value="groovy">Groovy</option>' +
            '<option value="scala">Scala</option>' +
            '<option value="php">PHP</option>' +
            '<option value="bash">Bash</option>' +
            '<option value="coffeescript">CoffeesSript</option>' +
            '<option value="go">Go</option>' +
            '<option value="haskell">Haskell</option>' +
            '<option value="go">Go</option>' +
            '<option value="c">C</option>' +
            '<option value="cpp">C++</option>' +
            '<option value="sql">SQL</option>' +
            '<option value="ruby">Ruby</option>' +
            '<option value="aspnet">ASP.NET</option>' +
            '<option value="csharp">C#</option>' +
            '<option value="swift">Swift</option>' +
            '<option value="objectivec">Objective-C</option>' +
            '</select>' +
            '</div>' +
            '<div class="form-group">' +
            '<label><input type="checkbox" class="note-codeblock-numbers"> ' +
             lang.codeblock.showLineNumbers + '</label>' +
            '</div>' +
            '<div class="form-group">' +
            '<label>' + lang.codeblock.sourceCode + '</label>' +
            '<textarea rows="10" class="form-control note-codeblock-source"></textarea>' +
            '</div>';

        var footer = '<button href="javascript://" ' +
            'class="btn btn-primary note-codeblock-btn disabled" disabled>' + lang.codeblock.insert + '</button>';
        return tmpl.dialog('note-codeblock-dialog', lang.codeblock.insert, body, footer);
      }
    },
    /**
     * @property {Object} events
     * @property {Function} events.showCodeblockDialog
     */
    events: {
      showCodeblockDialog: function (e, editor, layoutInfo) {
        var $dialog = layoutInfo.dialog(),
            $editable = layoutInfo.editable(),
            codeBlock = getCodeBlock($editable);

        // save current range
        editor.saveRange($editable);

        showCodeblockDialog($editable, $dialog, codeBlock).then(function (langType, isShowLineNumbers, sourceCode) {
          // when ok button clicked

          // restore range
          editor.restoreRange($editable);

          // insert video node
          editor.insertNode($editable, createCodeblockNode(langType, isShowLineNumbers, sourceCode));
        }).fail(function () {
          // when cancel button clicked
          editor.restoreRange($editable);
        });
      }
    },

    // define language
    langs: {
      'en-US': {
        codeblock: {
          codeblock: 'Code',
          langType: 'Language type',
          showLineNumbers: 'Show line numbers',
          sourceCode: 'Source Code',
          insert: 'Insert code block'
        }
      },
      'ko-KR': {
        codeblock: {
          codeblock: '코드',
          langType: '언어 종류',
          showLineNumbers: 'Show line numbers',
          sourceCode: 'Source Code',
          insert: '코드 블록 추가'
        }
      }
    }
  });
}));
