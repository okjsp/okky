<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="net.okjsp.AvatarPictureType; net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>
<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Category" %>
<g:set var="category" value="${Category.get('event')}"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main_with_banner">
    <title>행사 안내</title>
</head>
<body>

<g:sidebar category="${category}" />

<div id="article" class="content" role="main">
    <div class="nav" role="navigation">
        <h4>OKKY 행사</h4>
    </div>

    <div class="panel panel-default clearfix fa-">
        <div class="panel-heading clearfix">
            <div class="avatar avatar-medium clearfix pull-left"><a href="/user/info/45597" class="avatar-photo"><img src="//www.gravatar.com/avatar/b66da5ef6099211f5db8f5f7a3b4c36b?d=identicon&amp;s=40"></a> <div class="avatar-info"><a class="nickname" href="/user/info/45597" title="OKKY">OKKY</a> <div class="activity"><span class="fa fa-flash"></span> 960</div><div class="date-created"><span class="timeago" title="2018-09-04 12:37:42.0">2018-09-04 12:37:42</span> 작성</div> </div></div>
            <div class="content-identity pull-right">
                <div class="content-identity-count"><i class="fa fa-comment"></i> 21</div>
                <div class="content-identity-count"><i class="fa fa-eye"></i> 3297</div>
            </div>
        </div>
        <div class="content-container clearfix">
            <div class="panel-body">
                <div class="content-tags">
                    <span class="list-group-item-text article-id">#499051</span>
                    <a href="/articles/event" class="list-group-item-text item-tag label label-info"><i class="fa fa-comments"></i> IT 행사</a>
                    <a href="/articles/tagged/OKKYCON" class="list-group-item-text item-tag label label-gray">OKKYCON</a> <a href="/articles/tagged/Conference" class="list-group-item-text item-tag label label-gray">Conference</a> <a href="/articles/tagged/TDD" class="list-group-item-text item-tag label label-gray">TDD</a>
                </div>
                <h2 class="panel-title">

                    [OKKYCON: 2018] The Real TDD 컨퍼런스 개최합니다!
                </h2>
                <hr>
                <div>
                    <div class="row">
                        <h4>신청자 정보</h4>
                        <div class="col col-sm-6">
                            <div class="form-group">
                                <label for="email">이메일 <span class="required-indicator">*</span></label>
                                <input type="email" id="email" class="form-control" placeholder="이메일 주소를 입력해 주세요." />
                            </div>
                        </div>
                        <div class="col col-sm-6">
                            <div class="form-group">
                                <label for="tel">연락처 <span class="required-indicator">*</span></label>
                                <input type="tel" id="tel" class="form-control" placeholder="연락처를 입력해 주세요." />
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div class="row">
                        <h4>결제 정보</h4>
                        <div class="col col-sm-6">
                            <div class="form-group">
                                <label for="email">총 결제 금액</label>
                                <p class="form-control-static"><h3 class="text-primary" style="margin-top: 0">59,000원</h3></p>
                            </div>
                        </div>
                        <div class="col col-sm-6">
                            <div class="form-group">
                                <label>결제방식 <span class="required-indicator">*</span></label>
                                <div class="radio">
                                    <label><input type="radio" name="pay-method" /> 신용카드</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <label><input type="radio" name="pay-method" /> 실시간 계좌이체</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <label><input type="radio" name="pay-method" /> 무통장 입금</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div>
                        <h4>개인정보의 수집 항목 및 이용 목적</h4>
                        <p>수집 항목 : 연락처(휴대폰), 이메일 주소 / 이용 목적 : 행사 참석 안내 및 정보 전달을 위한 연락처 확인</p><br/>
                        <label><input type="checkbox" /> (필수) 개인정보 활용 동의</label>

                        <hr/>

                        <h4>이용약관</h4>
                        <p>접수일로부터 3일 이내에 참가비를 납부 하셔야 합니다.<br/>
                        추가 접수의 경우 관리자가 지정한 기일까지 참가비를 납부하셔야 합니다.<br/>
                        결제는 온라인상의 카드결제 및 가상계좌결제가 가능하며 당일 현금 결제는 불가 합니다.<br/>
                        예약 시 작성하신 휴대폰 번호와 이메일 주소로 승인여부를 안내해드립니다.<br/><br/>
                        행사 참가비 결제 후,<br/> 영업일 기준 3일 이내에 행사 참석 안내 메일을 받지 못한 경우 반드시
                        02-6933-5070으로 연락주시기 바랍니다.</p><br/>
                        <label><input type="checkbox" /> (필수) 이용약관 동의</label>

                        <hr/>

                        <h4>취소/환불약관</h4>
                        <p>1)  취소/ 환불 규정 안내<br/>
                        행사 3일 전 : 등록 결제 금액의 100% 환불<br/>
                        행사 2일 전 : 등록 결제 금액의 50% 환불<br/>
                        행사 1일 전 - 행사 당일 : 환불 불가</p><br/>
                        <label><input type="checkbox" /> (필수) 취소/환불약관 동의</label>

                        <hr/>

                        <h4>이메일 수신</h4>
                        <p>단, 모임 참여와 관련된 정보는 수신동의 관계없이 발송됩니다.</p><br/>
                        <label><input type="checkbox" /> (선택) 이메일 수신 동의</label>

                    </div>
                    <hr/>

                    <div class="nav" role="navigation">
                        <fieldset class="buttons">
                            <g:link uri="/event" class="btn btn-default btn-wide" onclick="return confirm('정말로 취소하시겠습니까?')">취소</g:link>
                            <button type="button" name="create" class="create btn btn-success btn-wide pull-right" value="결제하기" onclick="window.open('${request.contextPath}/INIpayStdSample/popup');" />
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
