    <div class="form-group ${hasErrors(bean: recruit, field: 'jobType', 'has-error')} has-feedback">
        <p>OKKY 구인 게시판 이용자 여러분, 안녕하세요.<br/>
        OKKY Jobs 운영진입니다 :)<p>

        <p>구인 업체와 구직 회원 모두를 위한 보다 정확한 구직 정보를 제공하고자, 아래와 같이 게시판 운영 규칙을 정하였습니다.<p>

        <hr/>

        <ol>
            <li><strong>하나의 게시글에는 하나의 프로젝트 정보만 등록 가능합니다.</strong> 이를 어길 시 관리자에 의해 별도 통보 없이 삭제될 수 있습니다.</li>

            <li>업체와 개인이 등록한 구인/구직 게시물은 <strong>OKKY의 공식자료가 아닙니다.</strong> 따라서 OKKY는 금전적인 문제 등에 대한 책임이 없습니다.</li>

            <li>게시판 <strong>운영 취지에 맞지 않는 글</strong>은 별도 통보 없이 삭제될 수 있습니다.</li>
        </ol>

        <hr/>

        <p>위 운영 규칙의 시범 운영 기간 (~12월 한 달 간) 동안 운영 규칙에 어긋난 글이 등록될 시,<br/>
        1회 댓글 안내 후  48시간 이내에 수정이 이뤄지지 않으면 삭제 조치하겠습니다.<p>

        <p>감사합니다.<br/><br/>
        OKKY 운영진 드림<p>
        <br/>
        <br/>
    </div>
    <div class="nav" role="navigation">
        <fieldset class="buttons">
            <g:link uri="/recruits" class="btn btn-default btn-wide"><g:message code="default.button.disagree.label" default="동의하지 않습니다."/></g:link>
            <g:link uri="/recruits/create?agree=Y" class="create btn btn-success btn-wide pull-right"><g:message code="default.button.agree.label" default="예, 동의합니다." /></g:link>
        </fieldset>
    </div>
