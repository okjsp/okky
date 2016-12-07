<%@ page import="net.okjsp.Article" %>
<%@ page import="net.okjsp.Content" %>
<%@ page import="net.okjsp.ContentTextType" %>
<%@ page import="net.okjsp.JobType" %>
<%@ page import="net.okjsp.JobPayType" %>
<%@ page import="net.okjsp.JobPositionType" %>

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
        <div class="col-xs-6">
          <a href="?jobType=FULLTIME" class="btn btn-block btn-success">${message(code: 'recruit.jobType.FULLTIME', default: '정규직')}</a>
        </div>
        <div class="col-xs-6">
          <a href="?jobType=CONTRACT" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT', default: '계약직(프리랜서)')}</a>
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
    </div>
</div>

<g:each in="${recruit.jobPositions}" var="jobPosition" status="index">

    <hr/>

    <label><g:message code="jobPosition.jobPositionType.label" default="직급"/></label>
    <div class="form-group">
        <div class="row">
            <div class="col col-sm-6">
                <select id="jobPositionType" name="jobPosition.jobPositionType" class="form-control">
                    <option value=""><g:message code="jobPosition.jobPositionType.label" default="직무" /></option>
                    <option value="JUNIOR" <g:if test="${jobPosition.jobPositionType == JobPositionType.valueOf('JUNIOR')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPositionType.JUNIOR', default: 'JUNIOR')}</option>
                    <option value="SENIOR" <g:if test="${jobPosition.jobPositionType == JobPositionType.valueOf('SENIOR')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPositionType.SENIOR', default: 'SENIOR')}</option>
                    <option value="MANAGER" <g:if test="${jobPosition.jobPositionType == JobPositionType.valueOf('MANAGER')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPositionType.MANAGER', default: 'MANAGER')}</option>
                </select>
            </div>
            <div class="col col-sm-6">
                <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
                <select id="jobPayType" name="jobPosition.jobPayType" class="form-control">
                    <option value=""><g:message code="jobPosition.jobPayType.label" default="연봉" /></option>
                    <option value="Y_25_30" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_25_30')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_25_30', default: '2500만 ~ 3000만')}</option>
                    <option value="Y_30_35" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_30_35')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_30_35', default: '3000만 ~ 3500만')}</option>
                    <option value="Y_35_40" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_35_40')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_35_40', default: '3500만 ~ 4000만')}</option>
                    <option value="Y_40_45" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_40_45')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_40_45', default: '4000만 ~ 4500만')}</option>
                    <option value="Y_45_50" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_45_50')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_45_50', default: '4500만 ~ 5000만')}</option>
                    <option value="Y_50_55" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_50_55')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_50_55', default: '5000만 ~ 5500만')}</option>
                    <option value="Y_55_60" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_55_60')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_55_60', default: '5500만 ~ 6000만')}</option>
                    <option value="Y_60_70" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_60_70')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_60_70', default: '6000만 ~ 7000만')}</option>
                    <option value="Y_70_80" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_70_80')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_70_80', default: '7000만 ~ 8000만')}</option>
                    <option value="Y_80_90" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_80_90')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_80_90', default: '8000만 ~ 9000만')}</option>
                    <option value="Y_90_OVER" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('Y_90_OVER')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.Y_90_OVER', default: '9000만 이상')}</option>
                </select>
                </g:if>
                <g:if test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
                <select id="jobPayType" name="jobPosition.jobPayType" class="form-control">
                    <option value=""><g:message code="jobPosition.jobPayType.label" default="급여" /></option>
                    <option value="M_20_30" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_20_30')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_20_30', default: '200만 ~ 300만')}</option>
                    <option value="M_30_40" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_30_40')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_30_40', default: '300만 ~ 400만')}</option>
                    <option value="M_40_50" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_40_50')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_40_50', default: '400만 ~ 500만')}</option>
                    <option value="M_50_60" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_50_60')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_50_60', default: '500만 ~ 600만')}</option>
                    <option value="M_60_70" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_60_70')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_60_70', default: '600만 ~ 700만')}</option>
                    <option value="M_70_80" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_70_80')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_70_80', default: '700만 ~ 800만')}</option>
                    <option value="M_80_90" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_80_90')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_80_90', default: '800만 ~ 900만')}</option>
                    <option value="M_90_OVER" <g:if test="${jobPosition.jobPayType == JobPayType.valueOf('M_90_OVER')}">selected="selected"</g:if>>${message(code: 'jobPosition.jobPayType.M_90_OVER', default: '900만 이상')}</option>
                </select>
                </g:if>
            </div>
        </div>
    </div>

    <div class="form-group ${hasErrors(bean: article, field: 'tagString', 'has-error')} has-feedback">
        <div>
            <g:textField name="jobPositions.tagString" value="${jobPositions?.tagString}" placeholder="Skill Tags," data-role="tagsinput" class="form-control"/>
        </div>
    </div>
</g:each>

<div id="jobPositionForm">

</div>

<g:if test="${recruit.jobPositions?.size() < 2}">
<div class="form-group ${hasErrors(bean: article, field: 'tagString', 'has-error')} has-feedback">
    <buttom type="button" id="addJobPositionFormButton" class="btn btn-sm btn-default btn-block"><i class="fa fa-plus-square-o"></i> 직급 추가</buttom>
</div>
</g:if>

<hr/>

<g:if test="${recruit.jobType == JobType.valueOf('CONTRACT')}">

    <hr/>

    <div class="form-group has-feedback">
        <div class="row">
            <div class="col col-sm-6">
                <div id="datepicker" class="input-group date">
                    <input type="text" id="startDate" name="startDate" class="form-control" placeholder="투입시기"><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                </div>
            </div>
            <div class="col col-sm-6">
                <select id="workingMonth" name="workingMonth" class="form-control form-inline">
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
            <select id="city" name="city" class="form-control">
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
            <select id="district" name="district" class="form-control">
                <option value=""><g:message code="recruit.district.label" default="구/군" /></option>
            </select>
        </div>
    </div>
</div>



<div class="form-group ${hasErrors(bean: article.content, field: 'text', 'has-error')} has-feedback">
    <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
        <label><g:message code="recruit.content.label" default="직무 정보"/></label>
    </g:if>
    <g:elseif test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
        <label><g:message code="recruit.content.label" default="프로젝트 정보"/></label>
    </g:elseif>
    <g:else>
        <label><g:message code="recruit.content.label" default="프로젝트 정보"/></label>
    </g:else>
    <g:if test="${article?.content?.textType == ContentTextType.MD}">
        <g:textArea name="content.text" id="summernote" value="${markdown.renderHtml([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:if>
    <g:elseif test="${article?.content?.textType == ContentTextType.HTML}">
        <g:textArea name="content.text" id="summernote" value="${filterHtml([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:elseif>
    <g:else>
        <g:textArea name="content.text" id="summernote" value="${lineToBr([text: article?.content?.text])}" rows="20" class="form-control input-block-level" />
    </g:else>

</div>

<script id="jobPositionFormTemplate" type="script/mustache-template">

    <hr/>

    <label><g:message code="jobPosition.jobPositionType.label" default="직급"/></label>
    <div class="form-group">
        <div class="row">
            <div class="col col-sm-6">
                <select id="jobPositionType" name="jobPosition.jobPositionType" class="form-control">
                    <option value=""><g:message code="recruit.jobPositionType.label" default="직무" /></option>
                    <option value="JUNIOR">${message(code: 'recruit.jobPositionType.JUNIOR', default: 'JUNIOR')}</option>
                    <option value="SENIOR">${message(code: 'recruit.jobPositionType.SENIOR', default: 'SENIOR')}</option>
                    <option value="MANAGER">${message(code: 'recruit.jobPositionType.MANAGER', default: 'MANAGER')}</option>
                </select>
            </div>
            <div class="col col-sm-6">
                <g:if test="${recruit.jobType == JobType.valueOf('FULLTIME')}">
                    <select id="jobPayType" name="jobPosition.jobPayType" class="form-control">
                        <option value=""><g:message code="jobPosition.jobPayType.label" default="연봉" /></option>
                        <option value="Y_25_30">${message(code: 'jobPosition.jobPayType.Y_25_30', default: '2500만 ~ 3000만')}</option>
                        <option value="Y_30_35">${message(code: 'jobPosition.jobPayType.Y_30_35', default: '3000만 ~ 3500만')}</option>
                        <option value="Y_35_40">${message(code: 'jobPosition.jobPayType.Y_35_40', default: '3500만 ~ 4000만')}</option>
                        <option value="Y_40_45">${message(code: 'jobPosition.jobPayType.Y_40_45', default: '4000만 ~ 4500만')}</option>
                        <option value="Y_45_50">${message(code: 'jobPosition.jobPayType.Y_45_50', default: '4500만 ~ 5000만')}</option>
                        <option value="Y_50_55">${message(code: 'jobPosition.jobPayType.Y_50_55', default: '5000만 ~ 5500만')}</option>
                        <option value="Y_55_60">${message(code: 'jobPosition.jobPayType.Y_55_60', default: '5500만 ~ 6000만')}</option>
                        <option value="Y_60_70">${message(code: 'jobPosition.jobPayType.Y_60_70', default: '6000만 ~ 7000만')}</option>
                        <option value="Y_70_80">${message(code: 'jobPosition.jobPayType.Y_70_80', default: '7000만 ~ 8000만')}</option>
                        <option value="Y_80_90">${message(code: 'jobPosition.jobPayType.Y_80_90', default: '8000만 ~ 9000만')}</option>
                        <option value="Y_90_OVER">${message(code: 'jobPosition.jobPayType.Y_90_OVER', default: '9000만 이상')}</option>
                    </select>
                </g:if>
                <g:if test="${recruit.jobType == JobType.valueOf('CONTRACT')}">
                    <select id="jobPayType" name="jobPosition.jobPayType" class="form-control">
                        <option value=""><g:message code="jobPosition.jobPayType.label" default="급여" /></option>
                        <option value="M_20_30">${message(code: 'recruit.jobPayType.M_20_30', default: '200만 ~ 300만')}</option>
                        <option value="M_30_40">${message(code: 'recruit.jobPayType.M_30_40', default: '300만 ~ 400만')}</option>
                        <option value="M_40_50">${message(code: 'recruit.jobPayType.M_40_50', default: '400만 ~ 500만')}</option>
                        <option value="M_50_60">${message(code: 'recruit.jobPayType.M_50_60', default: '500만 ~ 600만')}</option>
                        <option value="M_60_70">${message(code: 'recruit.jobPayType.M_60_70', default: '600만 ~ 700만')}</option>
                        <option value="M_70_80">${message(code: 'recruit.jobPayType.M_70_80', default: '700만 ~ 800만')}</option>
                        <option value="M_80_90">${message(code: 'recruit.jobPayType.M_80_90', default: '800만 ~ 900만')}</option>
                        <option value="M_90_OVER">${message(code: 'recruit.jobPayType.M_90_OVER', default: '900만 이상')}</option>
                    </select>
                </g:if>
            </div>
        </div>
    </div>

    <div class="form-group has-feedback">
        <div>
            <g:textField name="jobPosition.tagString" value="${jobPositions?.tagString}" placeholder="Skill Tags," data-role="tagsinput" class="form-control tag-input"/>
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

      if(city) {

        var districts = districtsInCity[city];

        console.log(districts);

        $.each(districts, function(i, d){
          $district.append('<option value="'+d+'" class="district">'+d+'</option>');
        });
      }
    });

    $('#datepicker').datepicker({
        format: "yyyy/mm",
        startView: 1,
        minViewMode: 1,
        language: "kr",
        autoclose: true
    });

    var template = $('#jobPositionFormTemplate').html();
    Mustache.parse(template);

    function addJobPositionForm() {
        jobPositionCount++;
        var rendered = Mustache.render(template);

        var $rendered = $(rendered);

        $rendered.find('.tag-input').tagsinput();

        $rendered.appendTo('#jobPositionForm');

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

</asset:script>
