<%@ page import="net.okjsp.Company" %>


<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: company, field: 'name', 'error')} required">
			<label>회사명 <span class="required-indicator">*</span></label>
			<g:textField name="name" value="${company?.name}" placeholder="사업자등록증 상의 회사명을 입력해 주세요." class="form-control"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: company, field: 'registerNumber', 'error')} required">
			<label>사업자등록번호 <span class="required-indicator">*</span></label>
			<g:textField name="registerNumber" value="${company?.registerNumber}" placeholder="숫자만 입력해 주세요." class="form-control"/>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'tel', 'error')} required">
			<label>대표 연락처 <span class="required-indicator">*</span></label>
			<input type="tel" name="companyInfo.tel" value="${companyInfo?.tel}" class="form-control" placeholder="000-0000-0000"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'email', 'error')} required">
			<label>대표 이메일 <span class="required-indicator">*</span></label>
			<input type="email" name="companyInfo.email" value="${companyInfo?.email}" class="form-control" placeholder="이메일주소를 입력해 주세요."/>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'managerTel', 'error')} required">
			<label>담당자 연락처 <span class="required-indicator">*</span></label>
			<input type="tel" name="companyInfo.managerTel" value="${companyInfo?.managerTel}" class="form-control" placeholder="000-0000-0000"/>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'managerEmail', 'error')} required">
			<label>담당자 이메일 <span class="required-indicator">*</span></label>
			<input type="email" name="companyInfo.managerEmail" value="${companyInfo?.managerEmail}" class="form-control" placeholder="이메일주소를 입력해 주세요."/>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'managerName', 'error')} required">
			<label>담당자명 <span class="required-indicator">*</span></label>
			<input type="tel" name="companyInfo.managerName" value="${companyInfo?.managerName}" class="form-control" />
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'employeeNumber', 'error')} required">
			<label>직원 수</label>
			<select class="form-control" name="companyInfo.employeeNumber">
				<option value="5"><g:message code="companyInfo.employeeNumber.value_5"/></option>
				<option value="5"><g:message code="companyInfo.employeeNumber.value_5"/></option>
				<option value="10"><g:message code="companyInfo.employeeNumber.value_10"/></option>
				<option value="20"><g:message code="companyInfo.employeeNumber.value_20"/></option>
				<option value="30"><g:message code="companyInfo.employeeNumber.value_30"/></option>
				<option value="40"><g:message code="companyInfo.employeeNumber.value_40"/></option>
				<option value="50"><g:message code="companyInfo.employeeNumber.value_50"/></option>
				<option value="100"><g:message code="companyInfo.employeeNumber.value_100"/></option>
				<option value="200"><g:message code="companyInfo.employeeNumber.value_200"/></option>
				<option value="999"><g:message code="companyInfo.employeeNumber.value_999"/></option>
			</select>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: companyInfo, field: 'homepageUrl', 'error')} required">
			<label>회사 홈페이지</label>
			<input type="url" name="companyInfo.homepageUrl" value="${companyInfo?.homepageUrl}" class="form-control" placeholder="홈페이지 URL을 입력해 주세요."/>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: company, field: 'logo', 'error')} ">
			<label>회사 로고</label>
			<input type="file" name="logoFile" class="form-control" placeholder="회사로고를 첨부해 주세요."/>
			<p class="form-control-static input-guide">- 1024 X 1024 이하의 정사각형 이미지</p>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group ${hasErrors(bean: company, field: 'logo', 'error')} ">
			<label>사업자등록증</label>
			<input type="file" name="introFile" class="form-control" placeholder="사업자등록증을 첨부해 주세요."/>
		</div>
	</div>
</div>


<div class="form-group ${hasErrors(bean: companyInfo, field: 'name', 'error')} required">
	<label>회사 소개 <span class="required-indicator">*</span></label>
	<g:textArea name="companyInfo.description" rows="20" class="summernote form-control input-block-level"/>
</div>


<asset:script type="text/javascript">
	$('.summernote').summernote({minHeight: 300, lang: 'ko-KR',
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

        $.each(districts, function(i, d){
          $district.append('<option value="'+d+'" class="district">'+d+'</option>');
        });
      }
    });

</asset:script>