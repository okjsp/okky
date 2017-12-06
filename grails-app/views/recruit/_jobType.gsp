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
                <a href="?jobType=FULLTIME&agree=Y" class="btn btn-block btn-success">${message(code: 'recruit.jobType.FULLTIME', default: '정규직')}</a>
            </div>
            <div class="col-xs-3">
                <a href="?jobType=CONTRACT&agree=Y" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.DISPATCH', default: '계약직(프리랜서)')}</a>
            </div>
            <div class="col-xs-3">
                <a href="?jobType=CONTRACT&agree=Y" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.INHOUSE', default: '계약직(프리랜서)')}</a>
            </div>
            <div class="col-xs-3">
                <a href="?jobType=CONTRACT&agree=Y" class="btn btn-block btn-primary">${message(code: 'recruit.jobType.CONTRACT.REMOTE', default: '계약직(프리랜서)')}</a>
            </div>
        </div>
    </div>
