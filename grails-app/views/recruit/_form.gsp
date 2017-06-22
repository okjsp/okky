<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>
<%@ page import="net.okjsp.JobType" %>
<%@ page import="net.okjsp.JobPayType" %>

<input type="hidden" name="jobType" value="${recruit.jobType}" />

<g:if test="${!recruit.jobType}">
<div class="form-group ${hasErrors(bean: recruit, field: 'jobType', 'has-error')} has-feedback">
    <div class="row">
        <div class="col-xs-12 text-center">
          <div class="content-header">
            <h3>계약 형태를 선택해 주세요.</h3>
          </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-3">
          <a href="?jobType=FULLTIME" class="btn btn-block btn-success">${message(code: 'recruit.jobType.FULLTIME', default: '정규직')}</a>
        </div>
        <div class="col-xs-3">
          <a href="?jobType=CONTRACT" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.DISPATCH', default: '계약직(프리랜서)')}</a>
        </div>
        <div class="col-xs-3">
            <a href="?jobType=CONTRACT" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.INHOUSE', default: '계약직(프리랜서)')}</a>
        </div>
        <div class="col-xs-3">
            <a href="?jobType=CONTRACT" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.REMOTE', default: '계약직(프리랜서)')}</a>
        </div>
    </div>
</div>
</g:if>

<g:if test="${recruit.jobType}">
<sec:ifAllGranted roles="ROLE_ADMIN">

    <div class="form-group ${hasErrors(bean: article, field: 'choice', 'has-error')} has-feedback">
        <div class="checkbox">
            <label>
                <g:checkBox name="choice" value="${article?.choice}"  />
                <g:message code="article.choice.label" default="Editor`s Choice" />
            </label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <label>
                <g:checkBox name="disabled" value="${!article?.enabled}"  />
                <g:message code="article.disabled.label" default="게시물 비공개 (관리자만 접근가능)" />
            </label>
        </div>
    </div>
</sec:ifAllGranted>

<div class="form-group ${hasErrors(bean: article, field: 'title', 'has-error')} has-feedback">
    <div>
      <h4>
          <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
            <div class="label label-success">${message(code: 'recruit.jobType.FULLTIME', default: '정규직')}</div>
          </g:if>
          <g:elseif test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
            <div class="label label-primary">${message(code: 'recruit.jobType.CONTRACT', default: '계약직(프리랜서)')}</div>
          </g:elseif>
      </h4>
    </div>
</div>

<div class="form-group ${hasErrors(bean: article, field: 'title', 'has-error')} has-feedback">
    <div>
        <g:textField name="title" required="" value="${article?.title}" placeholder="제목을 입력해 주세요." class="form-control"/>
    </div><br/>
    <div class="alert alert-info">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <small><strong>제목 예시)</strong><br/>
        - XXX 서비스 안드로이드/서버 개발자 모집<br/>
        - 201X 상반기 공개 채용<br/>
        - Java 신입/경력 개발자 모집<br/>
        - 201X 인턴쉽
        </small>
    </div>
</div>

<g:each in="${recruit.jobPositions}" var="jobPosition" status="index">

    <hr/>

    <label><g:message code="jobPosition.jobPositionType.label" default="직무정보"/></label>

    <div class="form-group">

        <div class="row">
            <div class="col col-sm-6">
                <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                    <option value=""><g:message code="jobPosition.group.label" default="직군" /></option>
                </select>
            </div>
            <div class="col col-sm-6">
                <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                    <option value=""><g:message code="jobPosition.job.label" default="직무" /></option>
                </select>
            </div>
        </div>

    </div>

    <div class="form-group has-feedback">
        <div>
            <g:textField name="recruit.jobPositions.title" value="${jobPosition?.title}" placeholder="업무명을 입력해 주세요." class="form-control form-dynamic"/>
        </div>
        <g:if test="${index == 0}">
        <br/>
        <div class="alert alert-info">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <small><strong>업무명 예시)</strong><br/>
                - 안드로이드/iOS 앱 개발 <br/>
                - Java/Spring 웹서비스 개발/운영 <br/>
                - 온라인 서비스 품질관리 부문 <br/></small>
        </div>
        </g:if>
    </div>

    <div class="form-group">
        <div class="row">
            <div class="col col-sm-6 ${jobPosition.minCareer == 99 || jobPosition.minCareer == 0 || jobPosition.minCareer == 15 || jobPosition.minCareer == '' ? '' : ' form-inline'} ">
                <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                    <option value=""><g:message code="jobPosition.minCareer.label" default="경력" /></option>
                    <option value="99" <g:if test="${jobPosition.minCareer == 99}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.99')}</option>
                    <option value="0" <g:if test="${jobPosition.minCareer == 0}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.0')}</option>
                    <option value="1" <g:if test="${jobPosition.minCareer == 1}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.1')}</option>
                    <option value="2" <g:if test="${jobPosition.minCareer == 2}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.2')}</option>
                    <option value="3" <g:if test="${jobPosition.minCareer == 3}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.3')}</option>
                    <option value="4" <g:if test="${jobPosition.minCareer == 4}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.4')}</option>
                    <option value="5" <g:if test="${jobPosition.minCareer == 5}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.5')}</option>
                    <option value="6" <g:if test="${jobPosition.minCareer == 6}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.6')}</option>
                    <option value="7" <g:if test="${jobPosition.minCareer == 7}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.7')}</option>
                    <option value="8" <g:if test="${jobPosition.minCareer == 8}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.8')}</option>
                    <option value="9" <g:if test="${jobPosition.minCareer == 9}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.9')}</option>
                    <option value="10" <g:if test="${jobPosition.minCareer == 10}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.10')}</option>
                    <option value="11" <g:if test="${jobPosition.minCareer == 11}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.11')}</option>
                    <option value="12" <g:if test="${jobPosition.minCareer == 12}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.12')}</option>
                    <option value="13" <g:if test="${jobPosition.minCareer == 13}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.13')}</option>
                    <option value="14" <g:if test="${jobPosition.minCareer == 14}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.14')}</option>
                    <option value="15" <g:if test="${jobPosition.minCareer == 15}">selected="selected"</g:if>>${message(code: 'jobPosition.minCareer.15')}</option>
                </select>
                <select name="recruit.jobPositions.maxCareer" class="form-control form-control-inline-half" style="display: ${jobPosition.minCareer == 99 || jobPosition.minCareer == 0 || jobPosition.minCareer == 15 || jobPosition.minCareer == '' ? 'none' : 'inline'};">
                    <option value="2" <g:if test="${jobPosition.maxCareer == 2}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.2')}</option>
                    <option value="3" <g:if test="${jobPosition.maxCareer == 3}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.3')}</option>
                    <option value="4" <g:if test="${jobPosition.maxCareer == 4}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.4')}</option>
                    <option value="5" <g:if test="${jobPosition.maxCareer == 5}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.5')}</option>
                    <option value="6" <g:if test="${jobPosition.maxCareer == 6}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.6')}</option>
                    <option value="7" <g:if test="${jobPosition.maxCareer == 7}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.7')}</option>
                    <option value="8" <g:if test="${jobPosition.maxCareer == 8}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.8')}</option>
                    <option value="9" <g:if test="${jobPosition.maxCareer == 9}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.9')}</option>
                    <option value="10" <g:if test="${jobPosition.maxCareer == 10}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.10')}</option>
                    <option value="11" <g:if test="${jobPosition.maxCareer == 11}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.11')}</option>
                    <option value="12" <g:if test="${jobPosition.maxCareer == 12}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.12')}</option>
                    <option value="13" <g:if test="${jobPosition.maxCareer == 13}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.13')}</option>
                    <option value="14" <g:if test="${jobPosition.maxCareer == 14}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.14')}</option>
                    <option value="15" <g:if test="${jobPosition.maxCareer == 15}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.15')}</option>
                    <option value="99" <g:if test="${jobPosition.maxCareer == 99}">selected="selected"</g:if>>${message(code: 'jobPosition.maxCareer.99')}</option>
                </select>
            </div>
            <div class="col col-sm-6">
                <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
                    <div class="form-inline">
                        <select name="recruit.jobPositions.minPay" class="form-control form-dynamic form-control-inline-half">
                            <option value=""><g:message code="jobPosition.minPay.label" default="최소연봉" /></option>
                            <option value="20" <g:if test="${jobPosition.minPay == 20}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.20')}</option>
                            <option value="30" <g:if test="${jobPosition.minPay == 30}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.30')}</option>
                            <option value="40" <g:if test="${jobPosition.minPay == 40}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.40')}</option>
                            <option value="50" <g:if test="${jobPosition.minPay == 50}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.50')}</option>
                            <option value="60" <g:if test="${jobPosition.minPay == 60}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.60')}</option>
                            <option value="70" <g:if test="${jobPosition.minPay == 70}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.70')}</option>
                            <option value="80" <g:if test="${jobPosition.minPay == 80}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.80')}</option>
                            <option value="90" <g:if test="${jobPosition.minPay == 90}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.90')}</option>
                            <option value="100" <g:if test="${jobPosition.minPay == 100}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.year.100')}</option>
                        </select>
                        <select name="recruit.jobPositions.maxPay" class="form-control form-dynamic form-control-inline-half">
                            <option value=""><g:message code="jobPosition.maxPay.label" default="최대연봉" /></option>
                            <option value="30" <g:if test="${jobPosition.maxPay == 30}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.30')}</option>
                            <option value="40" <g:if test="${jobPosition.maxPay == 40}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.40')}</option>
                            <option value="50" <g:if test="${jobPosition.maxPay == 50}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.50')}</option>
                            <option value="60" <g:if test="${jobPosition.maxPay == 60}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.60')}</option>
                            <option value="70" <g:if test="${jobPosition.maxPay == 70}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.70')}</option>
                            <option value="80" <g:if test="${jobPosition.maxPay == 80}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.80')}</option>
                            <option value="90" <g:if test="${jobPosition.maxPay == 90}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.90')}</option>
                            <option value="100" <g:if test="${jobPosition.maxPay == 100}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.100')}</option>
                            <option value="999" <g:if test="${jobPosition.maxPay == 999}">selected="selected"</g:if>>${message(code: 'jobPosition.maxPay.year.999')}</option>
                        </select>
                    </div>
                </g:if>
                <g:if test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
                <select id="minPay" name="recruit.jobPositions.minPay" class="form-control form-dynamic">
                    <option value=""><g:message code="jobPosition.minPay.label" default="급여" /></option>
                    <option value="20" <g:if test="${jobPosition.minPay == 20}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.20')}</option>
                    <option value="30" <g:if test="${jobPosition.minPay == 30}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.30')}</option>
                    <option value="40" <g:if test="${jobPosition.minPay == 40}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.40')}</option>
                    <option value="50" <g:if test="${jobPosition.minPay == 50}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.50')}</option>
                    <option value="60" <g:if test="${jobPosition.minPay == 60}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.60')}</option>
                    <option value="70" <g:if test="${jobPosition.minPay == 70}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.70')}</option>
                    <option value="80" <g:if test="${jobPosition.minPay == 80}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.80')}</option>
                    <option value="90" <g:if test="${jobPosition.minPay == 90}">selected="selected"</g:if>>${message(code: 'jobPosition.minPay.month.90')}</option>
                </select>
                </g:if>
            </div>
        </div>
    </div>

    <div class="form-group ${hasErrors(bean: article, field: 'tagString', 'has-error')} has-feedback">
        <div>
            <g:textField name="recruit.jobPositions.tagString" value="${jobPosition?.tagString}" placeholder="Skill tag 를 입력하면 검색시 정확도가 향상됩니다." data-role="tagsinput" class="form-control form-dynamic"/>
            <p class="form-control-static input-guide">- Skill tag 를 입력하면 검색시 정확도가 향상됩니다.</p>
        </div>
    </div>
    <div class="form-group has-feedback">
        <g:textArea name="recruit.jobPositions.description" value="${lineToBr([text: jobPosition?.description])}" rows="5" placeholder="직무에 대한 상세 정보를 입력해 주세요." class="form-control input-block-level form-dynamic" />
    </div>

    <hr/>
</g:each>

<div id="jobPositionForm">

</div>

<g:if test="${recruit.jobPositions?.size() < 3}">
<div class="form-group ${hasErrors(bean: article, field: 'tagString', 'has-error')} has-feedback">
    <buttom type="button" id="addJobPositionFormButton" class="btn btn-sm btn-default btn-block"><i class="fa fa-plus-square-o"></i> 업무정보 추가</buttom>
</div>
</g:if>


<g:if test="${recruit.jobType == JobType.valueOf('CONTRACT')}">

    <hr/>

    <div class="form-group has-feedback">
        <div class="row">
            <div class="col col-sm-6">
                <div id="datepicker" class="input-group date">
                    <input type="text" id="startDate" required="required" name="recruit.startDate" class="form-control" placeholder="투입시기"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                </div>
            </div>
            <div class="col col-sm-6">
                <select id="workingMonth" name="recruit.workingMonth" required="required" class="form-control form-inline">
                    <option value="">투입기간</option>
                    <option value="1">1개월</option>
                    <option value="2">2개월</option>
                    <option value="3">3개월</option>
                    <option value="4">4개월</option>
                    <option value="5">5개월</option>
                    <option value="6">6개월</option>
                    <option value="7">7개월</option>
                    <option value="8">8개월</option>
                    <option value="9">9개월</option>
                    <option value="10">10개월</option>
                    <option value="11">11개월</option>
                    <option value="12">12개월</option>
                    <option value="99">13개월 이상</option>
                </select>
            </div>
        </div>
    </div>
</g:if>

<label><g:message code="recruit.jobArea.label" default="근무지역"/></label>
<div class="form-group ${hasErrors(bean: recruit, field: 'city', 'has-error')} has-feedback">
    <div class="row">
        <div class="col col-sm-6">
            <select id="city" name="recruit.city" class="form-control" required="required">
                <option value=""><g:message code="recruit.city.label" default="시/도" /></option>
                <option value="서울" <g:if test="${recruit.city == '서울'}">selected="selected"</g:if>>서울</option>
                <option value="부산" <g:if test="${recruit.city == '부산'}">selected="selected"</g:if>>부산</option>
                <option value="대구" <g:if test="${recruit.city == '대구'}">selected="selected"</g:if>>대구</option>
                <option value="인천" <g:if test="${recruit.city == '인천'}">selected="selected"</g:if>>인천</option>
                <option value="광주" <g:if test="${recruit.city == '광주'}">selected="selected"</g:if>>광주</option>
                <option value="대전" <g:if test="${recruit.city == '대전'}">selected="selected"</g:if>>대전</option>
                <option value="울산" <g:if test="${recruit.city == '울산'}">selected="selected"</g:if>>울산</option>
                <option value="강원" <g:if test="${recruit.city == '강원'}">selected="selected"</g:if>>강원</option>
                <option value="경기" <g:if test="${recruit.city == '경기'}">selected="selected"</g:if>>경기</option>
                <option value="경남" <g:if test="${recruit.city == '경남'}">selected="selected"</g:if>>경남</option>
                <option value="경북" <g:if test="${recruit.city == '경북'}">selected="selected"</g:if>>경북</option>
                <option value="전남" <g:if test="${recruit.city == '전남'}">selected="selected"</g:if>>전남</option>
                <option value="전북" <g:if test="${recruit.city == '전북'}">selected="selected"</g:if>>전북</option>
                <option value="제주" <g:if test="${recruit.city == '제주'}">selected="selected"</g:if>>제주</option>
                <option value="충남" <g:if test="${recruit.city == '충남'}">selected="selected"</g:if>>충남</option>
                <option value="충북" <g:if test="${recruit.city == '충북'}">selected="selected"</g:if>>충북</option>
            </select>
        </div>
        <div class="col col-sm-6">
            <select id="district" name="recruit.district" class="form-control" required="required" data-value="${recruit.district}">
                <option value=""><g:message code="recruit.district.label" default="구/군" /></option>
            </select>
        </div>
    </div>
</div>


<div class="form-group ${hasErrors(bean: article.content, field: 'text', 'has-error')} has-feedback">
    <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
        <label><g:message code="recruit.content.label" default="기타 정보"/></label>
    </g:if>
    <g:elseif test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
        <label><g:message code="recruit.content.label" default="프로젝트 정보"/></label>
    </g:elseif>

    <g:textArea name="content.text" required="required" id="summernote" value="${filterHtml([text: article?.content?.text])}" rows="20" placeholder="기타 계약정보, 특이사항, 회사소개, 복리후생 등 상세한 정보를 입력해 주세요." class="form-control input-block-level" />


</div>

<div class="form-group ${hasErrors(bean: recruit, field: 'tel', 'has-error')} has-feedback">
    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: recruit, field: 'tel', 'error')} required">
                <label>담당자 연락처</label>
                <input type="tel" name="recruit.tel" value="${recruit?.tel}" required="" class="form-control" placeholder="000-0000-0000"/>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: recruit, field: 'email', 'error')} required">
                <label>담당자 이메일</label>
                <input type="email" name="recruit.email" value="${recruit?.email}" required="" class="form-control" placeholder="이메일주소를 입력해 주세요."/>
            </div>
        </div>
    </div>
</div>

<script id="jobPositionFormTemplate" type="script/mustache-template">
    <div class="position-form">
        <hr/>

        <div class="col">
            <label><g:message code="jobPosition.jobPositionType.label" default="업무정보"/></label>
            <a href="#" class="pull-right delete-position-form" style="display: none;"><i class="fa fa-remove" />삭제</a>
        </div>

        <div class="form-group">

            <div class="row">
                <div class="col col-sm-6">
                    <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                        <option value=""><g:message code="jobPosition.group.label" default="직군" /></option>
                    </select>
                </div>
                <div class="col col-sm-6">
                    <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                        <option value=""><g:message code="jobPosition.job.label" default="직무" /></option>
                    </select>
                </div>
            </div>

        </div>

        <div class="form-group has-feedback">
            <div>
                <input name="recruit.jobPositions.title" value="${jobPositions?.title}" placeholder="업무명을 입력해 주세요." class="form-control form-dynamic"/>
            </div><br/>
            <div class="alert alert-info">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <small><strong>업무명 예시)</strong><br/>
                - 안드로이드/iOS 앱 개발 <br/>
                - Java/Spring 웹서비스 개발/운영 <br/>
                - 온라인 서비스 품질관리<br/></small>
            </div>
        </div>

        <div class="form-group">
            <div class="row">
                <div class="col col-sm-6">
                    <select name="recruit.jobPositions.minCareer" class="form-control form-control-inline-half form-dynamic">
                        <option value=""><g:message code="jobPosition.minCareer.label" default="경력" /></option>
                        <option value="99">${message(code: 'jobPosition.minCareer.99')}</option>
                        <option value="0">${message(code: 'jobPosition.minCareer.0')}</option>
                        <option value="1">${message(code: 'jobPosition.minCareer.1')}</option>
                        <option value="2">${message(code: 'jobPosition.minCareer.2')}</option>
                        <option value="3">${message(code: 'jobPosition.minCareer.3')}</option>
                        <option value="4">${message(code: 'jobPosition.minCareer.4')}</option>
                        <option value="5">${message(code: 'jobPosition.minCareer.5')}</option>
                        <option value="6">${message(code: 'jobPosition.minCareer.6')}</option>
                        <option value="7">${message(code: 'jobPosition.minCareer.7')}</option>
                        <option value="8">${message(code: 'jobPosition.minCareer.8')}</option>
                        <option value="9">${message(code: 'jobPosition.minCareer.9')}</option>
                        <option value="10">${message(code: 'jobPosition.minCareer.10')}</option>
                        <option value="11">${message(code: 'jobPosition.minCareer.11')}</option>
                        <option value="12">${message(code: 'jobPosition.minCareer.12')}</option>
                        <option value="13">${message(code: 'jobPosition.minCareer.13')}</option>
                        <option value="14">${message(code: 'jobPosition.minCareer.14')}</option>
                        <option value="15">${message(code: 'jobPosition.minCareer.15')}</option>
                    </select>
                    <select name="recruit.jobPositions.maxCareer" class="form-control form-control-inline-half" style="display: none;">
                    </select>
                </div>
                <div class="col col-sm-6">
                    <g:if test="${recruit?.jobType == JobType.valueOf('FULLTIME')}">
                        <div class="form-inline">
                            <select name="recruit.jobPositions.minPay" class="form-control form-dynamic form-control-inline-half">
                                <option value=""><g:message code="jobPosition.minPay.label" default="최소연봉" /></option>
                                <option value="20">${message(code: 'jobPosition.minPay.year.20')}</option>
                                <option value="30">${message(code: 'jobPosition.minPay.year.30')}</option>
                                <option value="40">${message(code: 'jobPosition.minPay.year.40')}</option>
                                <option value="50">${message(code: 'jobPosition.minPay.year.50')}</option>
                                <option value="60">${message(code: 'jobPosition.minPay.year.60')}</option>
                                <option value="70">${message(code: 'jobPosition.minPay.year.70')}</option>
                                <option value="80">${message(code: 'jobPosition.minPay.year.80')}</option>
                                <option value="90">${message(code: 'jobPosition.minPay.year.90')}</option>
                                <option value="100">${message(code: 'jobPosition.minPay.year.100')}</option>
                            </select>
                            <select name="recruit.jobPositions.maxPay" class="form-control form-dynamic form-control-inline-half">
                                <option value=""><g:message code="jobPosition.maxPay.label" default="최대연봉" /></option>
                                <option value="30">${message(code: 'jobPosition.maxPay.year.30')}</option>
                                <option value="40">${message(code: 'jobPosition.maxPay.year.40')}</option>
                                <option value="50">${message(code: 'jobPosition.maxPay.year.50')}</option>
                                <option value="60">${message(code: 'jobPosition.maxPay.year.60')}</option>
                                <option value="70">${message(code: 'jobPosition.maxPay.year.70')}</option>
                                <option value="80">${message(code: 'jobPosition.maxPay.year.80')}</option>
                                <option value="90">${message(code: 'jobPosition.maxPay.year.90')}</option>
                                <option value="100">${message(code: 'jobPosition.maxPay.year.100')}</option>
                                <option value="999">${message(code: 'jobPosition.maxPay.year.999')}</option>
                            </select>
                        </div>
                    </g:if>
                    <g:if test="${recruit?.jobType == JobType.valueOf('CONTRACT')}">
                        <select name="recruit.jobPositions.minPay" class="form-control form-dynamic">
                            <option value=""><g:message code="jobPosition.minPay.label" default="급여" /></option>
                            <option value="20">${message(code: 'jobPosition.minPay.month.20')}</option>
                            <option value="30">${message(code: 'jobPosition.minPay.month.30')}</option>
                            <option value="40">${message(code: 'jobPosition.minPay.month.40')}</option>
                            <option value="50">${message(code: 'jobPosition.minPay.month.50')}</option>
                            <option value="60">${message(code: 'jobPosition.minPay.month.60')}</option>
                            <option value="70">${message(code: 'jobPosition.minPay.month.70')}</option>
                            <option value="80">${message(code: 'jobPosition.minPay.month.80')}</option>
                            <option value="90">${message(code: 'jobPosition.minPay.month.90')}</option>
                        </select>
                    </g:if>
                </div>
            </div>
        </div>

        <div class="form-group has-feedback">
            <g:textField name="recruit.jobPositions.tagString" value="${jobPositions?.tagString}" placeholder="Skill Tags," data-role="tagsinput" class="form-control tag-input form-dynamic"/>
            <p class="form-control-static input-guide">- Skill tag 를 입력하면 검색시 정확도가 향상됩니다.</p>
        </div>

        <div class="form-group has-feedback">
            <g:textArea name="recruit.jobPositions.description" value="${lineToBr([text: jobPosition?.description])}" rows="5" placeholder="직무에 대한 상세 정보를 입력해 주세요." class="form-control input-block-level form-dynamic" />
        </div>
    </div>
</script>
</g:if>
<script>
    var jobPositionCount = ${recruit.jobPositions?.size() ?:0};
</script>

<g:hiddenField name="content.textType" value="HTML"/>
<asset:script type="text/javascript">
    $('#summernote').summernote({minHeight: 300, lang: 'ko-KR',
      placeholder: "기타 계약정보, 특이사항, 회사소개, 복리후생 등 상세한 정보를 입력해 주세요.",
      onInit: function() {
        if($(window).height() > 400)
            $('.note-editable').css('max-height', $(window).height()-100);
      },
      onImageUpload: $.onImageUpload($('#summernote'))
    });

    function postForm() {
        $('textarea[name="content.text"]').val($('#summernote').code());
    }

    var districtsInCity ={
    	'서울' : ['강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구'],
    	'부산' : ['강서구','금정구','남구','동구','동래구','부산진구','북구','사상구','사하구','서구','수영구','연제구','영도구','중구','해운대구','기장군'],
    	'대구' : ['남구','달서구','동구','북구','서구','수성구','중구','달성군'],
    	'인천' : ['계양구','남구','남동구','동구','부평구','서구','연수구','중구','강화군','옹진군'],
    	'광주' : ['광산구','남구','동구','북구','서구'],
    	'대전' : ['대덕구','동구','서구','유성구','중구'],
    	'울산' : ['남구','동구','북구','중구','울주군'],
    	'강원' : ['강릉시','동해시','삼척시','속초시','원주시','춘천시','태백시','고성군','양구군','양양군','영월군','인제군','정선군','철원군','평창군','홍천군','화천군','횡성군'],
    	'경기' : ['고양시 덕양구','고양시 일산동구','고양시 일산서구','과천시','광명시','광주시','구리시','군포시','김포시','남양주시','동두천시','부천시 소사구','부천시 오정구','부천시 원미구','성남시 분당구','성남시 수정구','성남시 중원구','수원시 권선구','수원시 영통구','수원시 장안구','수원시 팔달구','시흥시','안산시 단원구','안산시 상록구','안성시','안양시 동안구','안양시 만안구','양주시','오산시','용인시 기흥구','용인시 수지구','용인시 처인구','의왕시','의정부시','이천시','파주시','평택시','포천시','하남시','화성시','가평군','양평군','여주군','연천군'],
    	'경남' : ['거제시','김해시','마산시','밀양시','사천시','양산시','진주시','진해시','창원시','통영시','거창군','고성군','남해군','산청군','의령군','창녕군','하동군','함안군','함양군','합천군'],
    	'경북' : ['경산시','경주시','구미시','김천시','문경시','상주시','안동시','영주시','영천시','포항시 남구','포항시 북구','고령군','군위군','봉화군','성주군','영덕군','영양군','예천군','울릉군','울진군','의성군','청도군','청송군','칠곡군'],
    	'전남' : ['광양시','나주시','목포시','순천시','여수시','강진군','고흥군','곡성군','구례군','담양군','무안군','보성군','신안군','영광군','영암군','완도군','장성군','장흥군','진도군','함평군','해남군','화순군'],
    	'전북' : ['군산시','김제시','남원시','익산시','전주시 덕진구','전주시 완산구','정읍시','고창군','무주군','부안군','순창군','완주군','임실군','장수군','진안군'],
    	'제주' : ['제주시','서귀포시'],
    	'충남' : ['계룡시','공주시','논산시','보령시','서산시','아산시','천안시 동남구','천안시 서북구','금산군','당진군','부여군','서천군','연기군','예산군','청양군','태안군','홍성군'],
    	'충북' : ['제천시','청주시 상당구','청주시 흥덕구','충주시','괴산군','단양군','보은군','영동군','옥천군','음성군','증평군','진천군','청원군']
  	};

    $('#city').change(function() {
      var city = $(this).val();
      var $district = $('#district');

      $district.find('option.district').detach();

      var districtVal = $district.data('value');

      if(city) {
        var districts = districtsInCity[city];
        $.each(districts, function(i, d){
          $district.append('<option value="'+d+'" class="district">'+d+'</option>');
        });

        $district.val(districtVal);
      }
    });

    $('#city').change();

    $('#datepicker').datepicker({
        format: "yyyy/mm",
        startView: 1,
        minViewMode: 1,
        language: "kr",
        autoclose: true
    });

    var validator;

    var template = $('#jobPositionFormTemplate').html();
    Mustache.parse(template);

    function addJobPositionForm() {
        jobPositionCount++;
        var rendered = Mustache.render(template, {index : jobPositionCount});

        var $rendered = $(rendered);

        $rendered.find('.tag-input').tagsinput();

        $rendered.appendTo('#jobPositionForm');

        if(jobPositionCount != 1) {
          $('.delete-position-form').show();
        }

        if(jobPositionCount >= 3) $('#addJobPositionFormButton').hide();
    }


    if(jobPositionCount == 0) {
        addJobPositionForm();
    }

    $('#addJobPositionFormButton').click(function() {
        if(jobPositionCount < 3) {
            addJobPositionForm();
        }
    });

    var maxCareerList = [
        {value : 2, label: '${message(code: 'jobPosition.maxCareer.2')}'},
        {value : 3, label: '${message(code: 'jobPosition.maxCareer.3')}'},
        {value : 4, label: '${message(code: 'jobPosition.maxCareer.4')}'},
        {value : 5, label: '${message(code: 'jobPosition.maxCareer.5')}'},
        {value : 6, label: '${message(code: 'jobPosition.maxCareer.6')}'},
        {value : 7, label: '${message(code: 'jobPosition.maxCareer.7')}'},
        {value : 8, label: '${message(code: 'jobPosition.maxCareer.8')}'},
        {value : 9, label: '${message(code: 'jobPosition.maxCareer.9')}'},
        {value : 10, label: '${message(code: 'jobPosition.maxCareer.10')}'},
        {value : 11, label: '${message(code: 'jobPosition.maxCareer.11')}'},
        {value : 12, label: '${message(code: 'jobPosition.maxCareer.12')}'},
        {value : 13, label: '${message(code: 'jobPosition.maxCareer.13')}'},
        {value : 14, label: '${message(code: 'jobPosition.maxCareer.14')}'},
        {value : 15, label: '${message(code: 'jobPosition.maxCareer.15')}'},
        {value : 99, label: '${message(code: 'jobPosition.maxCareer.99')}'}
    ];

    $('.content').delegate('select[name="recruit.jobPositions.minCareer"]','change', function() {
      var value = $(this).val();

      if(value == '99' || value == '0' || value == '15' || value == '') {
        $(this).parent().removeClass('form-inline');
        $(this).nextAll('select').hide();
      } else {
        $(this).parent().addClass('form-inline');
        var $maxCareerEl = $(this).nextAll('select').css({'display':'inline-block'}).focus();

        $maxCareerEl.find('option').detach();

        $.each(maxCareerList, function(i, career) {
          if(career.value > value) {
            $maxCareerEl.append('<option value="'+career.value+'">'+career.label+'</option>');
          }
        });
      }
    });

    $('.content').delegate('.delete-position-form', 'click', function() {
      if(confirm('해당 직무 정보를 삭제하시겠습니까?'))
        $(this).parents('.position-form').detach();
        jobPositionCount--;

        if(jobPositionCount == 1) {
          $('.delete-position-form').hide();
        }
    });

    validator = $("#article-form").validate({
      errorClass : 'validError',
      submitHandler: function(form) {
        var valid = true;
        $(form).find('.form-dynamic').each(function() {
          if($(this).val() == '') {
            alert('모든 정보를 정확히 입력해 주세요.');
            valid = false;
            $(this).focus();
            return false;
          }
        });

        if(valid)
          form.submit();
      }
    });

</asset:script>
