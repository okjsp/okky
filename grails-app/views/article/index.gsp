
<%@ page import="net.okjsp.Article" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main_with_banner">
		<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
		<title><g:message code="${category.labelCode}" default="${category.defaultLabel}" /></title>
	</head>
	<body>
        <g:sidebar category="${category}"/>
		<div id="list-article" class="content scaffold-list" role="main">
            <div class="nav" role="navigation">
                <g:link class="create btn btn-success btn-wide pull-right" uri="/articles/${params.code}/create"><i class="fa fa-pencil"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
                
                <h4><g:message code="${category.labelCode}" default="${category.defaultLabel}" /></h4>
                <g:form name="category-filter-form" method="get" uri="/articles/${category.code}">
                <div class="category-filter-wrapper">
                        <div class="category-filter-query pull-right">
                            <div class="input-group input-group-sm">
                                <input type="search" name="query" id="search-field" class="form-control" placeholder="검색어" value="${params.query}" />
                                <span class="input-group-btn">
                                    <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                                    <g:if test="${params.query}">
                                        <g:link uri="/articles/${category.code}" class="btn btn-warning"><i class="fa fa-times-circle"></i> clear</g:link>
                                    </g:if>
                                </span>
                            </div>
                        </div>
                        <ul class="list-sort pull-left">
                            <li><g:link uri="/articles/${category.code}" params="[sort:'id', order:'desc']" data-sort="id" data-order="desc" class="category-sort-link ${params.sort == 'id' ? 'active':''}">최신순</g:link></li>
                            <li><g:link uri="/articles/${category.code}" params="[sort:'voteCount', order:'desc']" data-sort="voteCount" data-order="desc" class="category-sort-link ${params.sort == 'voteCount' ? 'active':''}">추천순</g:link></li>
                            <li><g:link uri="/articles/${category.code}" params="[sort:'noteCount', order:'desc']" data-sort="noteCount" data-order="desc" class="category-sort-link ${params.sort == 'noteCount' ? 'active':''}">댓글순</g:link></li>
                            <li><g:link uri="/articles/${category.code}" params="[sort:'scrapCount', order:'desc']" data-sort="scrapCount" data-order="desc" class="category-sort-link ${params.sort == 'scrapCount' ? 'active':''}">스크랩순</g:link></li>
                            <li><g:link uri="/articles/${category.code}" params="[sort:'viewCount', order:'desc']" data-sort="viewCount" data-order="desc" class="category-sort-link ${params.sort == 'viewCount' ? 'active':''}">조회순</g:link></li>
                        </ul>
                        <input type="hidden" name="sort" id="category-sort-input" value="${params.sort}"/>
                        <input type="hidden" name="order" id="category-order-input" value="${params.order}"/>
                </div>

                <div id="job-filter" class="job-filter-wrapper" <g:if test="${params['filter.act'] != 'Y'}">style="display: none;"</g:if>>
                    <input type="hidden" name="filter.act" value="Y" />
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="job-filter-form">
                                <div class="row">
                                    <div class="col col-sm-2"><label>계약 형태</label></div>
                                    <div class="col col-sm-10">
                                        <label class="label label-success label"><input type="checkbox" name="filter.jobType" value="${net.okjsp.JobType.FULLTIME}" <g:if test="${params.list('filter.jobType').contains(net.okjsp.JobType.FULLTIME.name())}">checked</g:if>> 정규직</label>
                                        <label class="label label-primary"><input type="checkbox" name="filter.jobType" value="${net.okjsp.JobType.CONTRACT}" <g:if test="${params.list('filter.jobType').contains(net.okjsp.JobType.CONTRACT.name())}">checked</g:if>/> 계약직</label>
                                    </div>
                                </div>
                                <hr>
                                <div class="row">
                                    <div class="col col-sm-2"><label>직무</label></div>
                                    <div class="col col-sm-10">
                                        <ul id="filter-job-group" class="nav nav-tabs">
                                            <input type="hidden" name="filter.group" id="filter-group-input" value="" />
                                            <g:each in="${net.okjsp.JobPositionGroup.findAll().sort { a, b -> a.id <=> b.id}}" var="group" status="index">
                                            <li role="presentation" data-id="${group.id}" <g:if test="${params['filter.group'] == group.id as String || (!params['filter.group'] && index == 0)}">class="active"</g:if>><a href="javascript://">${group.name}</a></li>
                                            </g:each>
                                        </ul>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col col-sm-2"><label></label></div>
                                    <g:each in="${net.okjsp.JobPositionGroup.findAll().sort { a, b -> a.id <=> b.id}}" var="group" status="index">
                                    <div id="filter-duty-${group.id}" class="col col-sm-10 job-filter-input filter-duty" <g:if test="${params['filter.group'] != group.id as String || (!params['filter.group'] && index != 0)}">style="display: none;" </g:if>>
                                        <g:each in="${group.duties.sort { a, b -> a.id <=> b.id}}" var="duty" status="index2">
                                            <label><input type="checkbox" name="filter.jobDuty" value="${duty.id}" <g:if test="${params.list('filter.jobDuty').contains(duty.id as String)}">checked</g:if>/> ${duty.name}</label>
                                        </g:each>
                                    </div>
                                    </g:each>
                                </div>

                                <hr>
                                <div class="row">
                                    <div class="col col-sm-2"><label>지역</label></div>
                                    <div class="col col-sm-10 job-filter-input">
                                        <label><input type="checkbox" name="filter.city" value="서울" <g:if test="${params.list('filter.city').contains("서울")}">checked</g:if>/> 서울</label>
                                        <label><input type="checkbox" name="filter.city" value="부산" <g:if test="${params.list('filter.city').contains("부산")}">checked</g:if>/> 부산</label>
                                        <label><input type="checkbox" name="filter.city" value="대구" <g:if test="${params.list('filter.city').contains("대구")}">checked</g:if>/> 대구</label>
                                        <label><input type="checkbox" name="filter.city" value="인천" <g:if test="${params.list('filter.city').contains("인천")}">checked</g:if>/> 인천</label>
                                        <label><input type="checkbox" name="filter.city" value="광주" <g:if test="${params.list('filter.city').contains("광주")}">checked</g:if>/> 광주</label>
                                        <label><input type="checkbox" name="filter.city" value="대전" <g:if test="${params.list('filter.city').contains("대전")}">checked</g:if>/> 대전</label>
                                        <label><input type="checkbox" name="filter.city" value="울산" <g:if test="${params.list('filter.city').contains("울산")}">checked</g:if>/> 울산</label>
                                        <label><input type="checkbox" name="filter.city" value="세종" <g:if test="${params.list('filter.city').contains("세종")}">checked</g:if>/> 세종</label>
                                        <label><input type="checkbox" name="filter.city" value="강원" <g:if test="${params.list('filter.city').contains("강원")}">checked</g:if>/> 강원</label>
                                        <label><input type="checkbox" name="filter.city" value="경기" <g:if test="${params.list('filter.city').contains("경기")}">checked</g:if>/> 경기</label>
                                        <label><input type="checkbox" name="filter.city" value="경남" <g:if test="${params.list('filter.city').contains("경남")}">checked</g:if>/> 경남</label>
                                        <label><input type="checkbox" name="filter.city" value="경북" <g:if test="${params.list('filter.city').contains("경북")}">checked</g:if>/> 경북</label>
                                        <label><input type="checkbox" name="filter.city" value="전남" <g:if test="${params.list('filter.city').contains("전남")}">checked</g:if>/> 전남</label>
                                        <label><input type="checkbox" name="filter.city" value="전북" <g:if test="${params.list('filter.city').contains("전북")}">checked</g:if>/> 전북</label>
                                        <label><input type="checkbox" name="filter.city" value="충남" <g:if test="${params.list('filter.city').contains("충남")}">checked</g:if>/> 충남</label>
                                        <label><input type="checkbox" name="filter.city" value="충북" <g:if test="${params.list('filter.city').contains("충북")}">checked</g:if>/> 충북</label>
                                        <label><input type="checkbox" name="filter.city" value="제주" <g:if test="${params.list('filter.city').contains("제주")}">checked</g:if>/> 제주</label>
                                    </div>
                                </div>

                                <hr/>

                                <div class="row">
                                    <div class="col col-sm-2"><label>경력</label></div>
                                    <div class="col col-sm-10 job-filter-input form-inline">
                                        <select name="filter.minCareer" class="form-control form-control-inline-half form-dynamic">
                                            <option value=""><g:message code="jobPosition.minCareer.label" default="최소 경력" /></option>
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
                                        <select name="filter.maxCareer" class="form-control form-control-inline-half form-dynamic">
                                            <option value=""><g:message code="jobPosition.maxCareer.label" default="최대 경력" /></option>
                                            <option value="99">${message(code: 'jobPosition.maxCareer.99')}</option>
                                            <option value="2">${message(code: 'jobPosition.maxCareer.2')}</option>
                                            <option value="3">${message(code: 'jobPosition.maxCareer.3')}</option>
                                            <option value="4">${message(code: 'jobPosition.maxCareer.4')}</option>
                                            <option value="5">${message(code: 'jobPosition.maxCareer.5')}</option>
                                            <option value="6">${message(code: 'jobPosition.maxCareer.6')}</option>
                                            <option value="7">${message(code: 'jobPosition.maxCareer.7')}</option>
                                            <option value="8">${message(code: 'jobPosition.maxCareer.8')}</option>
                                            <option value="9">${message(code: 'jobPosition.maxCareer.9')}</option>
                                            <option value="10">${message(code: 'jobPosition.maxCareer.10')}</option>
                                            <option value="11">${message(code: 'jobPosition.maxCareer.11')}</option>
                                            <option value="12">${message(code: 'jobPosition.maxCareer.12')}</option>
                                            <option value="13">${message(code: 'jobPosition.maxCareer.13')}</option>
                                            <option value="14">${message(code: 'jobPosition.maxCareer.14')}</option>
                                            <option value="15">${message(code: 'jobPosition.maxCareer.15')}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <hr/>
                            <div class="pull-left" style="padding-top: 15px;">
                                <g:link uri="/articles/recruit">필터 초기화</g:link>
                            </div>
                            <div class="pull-right">
                                <button type="submit" class="btn btn-primary"><i class="fa fa-search"></i> 검색</button>
                                <button type="button" id="job-filter-close" class="btn btn-default">닫기</button>
                            </div>
                        </div>
                    </div>
                </div>
                </g:form>

            </div>


        <g:if test="${choiceJobs && choiceJobs?.size() > 0}">
            <div class="okkys-choice">
                <div class="panel panel-default">

                    <!-- Table -->

                    <ul class="list-group">

                        <g:each in="${choiceJobs}" status="i" var="article">

                            <g:set var="evaluateClass" value="no-note" />

                            <g:if test="${article.selectedNote}">
                                <g:set var="evaluateClass" value="success" />
                            </g:if>
                            <g:elseif test="${article.noteCount > 0}">
                                <g:set var="evaluateClass" value="has-note" />
                            </g:elseif>

                            <li class="list-group-item ${category?.useEvaluate ? 'list-group-item-question':''} list-group-${evaluateClass} clearfix">

                                <div class="list-title-wrapper clearfix">
                                    <div class="list-tag clearfix">
                                        <span class="list-group-item-text article-id">#${article.id}</span>
                                        <a href="${request.contextPath}/articles/tagged/${tag}" class="list-group-item-text item-tag label label-success">Editor's Choice</a>
                                        <g:categoryLabel category="${article.category}" />
                                        <g:tags tags="${article.tagString}" />
                                    </div>

                                    <h5 class="list-group-item-heading ${category?.useEvaluate ? 'list-group-item-evaluate' : ''}">
                                        <g:link controller="article" action="show" id="${article.id}">
                                            <g:if test="${!article.enabled}">
                                                <span class="fa fa-ban" style="color:red;"></span>
                                            </g:if>
                                            ${fieldValue(bean: article, field: "title")}
                                        </g:link>
                                    </h5>
                                </div>

                                <div class="list-summary-wrapper clearfix">
                                    <g:if test="${category?.useEvaluate}">
                                        <div class="item-evaluate-wrapper pull-right clearfix">
                                            <div class="item-evaluate">
                                                <div class="item-evaluate-icon">
                                                    <i class="item-icon fa fa-thumbs-o-up"></i>
                                                </div>
                                                <div class="item-evaluate-count">
                                                    <span><g:shorten number="${article.voteCount}" />
                                                </div>
                                            </div>
                                            <div class="item-evaluate item-evaluate-${evaluateClass}">
                                                <div class="item-evaluate-icon">
                                                    <g:if test="${evaluateClass == 'no-note'}">
                                                        <i class="item-icon fa fa-question-circle"></i>
                                                    </g:if>
                                                    <g:elseif test="${evaluateClass == 'has-note'}">
                                                        <i class="item-icon fa fa-exclamation-circle"></i>
                                                    </g:elseif>
                                                    <g:elseif test="${evaluateClass == 'success'}">
                                                        <i class="item-icon fa fa-check-circle"></i>
                                                    </g:elseif>
                                                </div>
                                                <div class="item-evaluate-count">
                                                    <g:shorten number="${article.noteCount}" />
                                                </div>
                                            </div>
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <div class="list-group-item-summary clearfix">
                                            <ul>
                                                <li class="${article.noteCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-comment "></i> <g:shorten number="${article.noteCount}" /></li>
                                                <li class="${article.voteCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-thumbs-up"></i> <g:shorten number="${article.voteCount}" /></li>
                                                <li class="${article.viewCount == 0 ? 'item-icon-disabled' : ''}"><i class="item-icon fa fa-eye"></i> <g:shorten number="${article.viewCount}" /></li>
                                            </ul>
                                        </div>
                                    </g:else>
                                </div>

                                <div class="list-group-item-author clearfix">
                                    <g:avatar avatar="${article.displayAuthor}" size="list" dateCreated="${article.dateCreated}" />
                                </div>
                            </li>
                        </g:each>
                    </ul>
                </div>
            </div>
            </g:if>
            <div class="panel panel-default">

                <!-- Table -->

                <ul class="list-group">

                    <g:if test="${articlesCount == 0}">
                        <li class="list-group-item clearfix">
                            <div class="panel-body text-center">
                                글이 없습니다.
                            </div>
                        </li>
                    </g:if>

                    <g:each in="${articleList}" status="i" var="article">
                        <g:if test="${article.isRecruit}">
                            <g:render template="recruit" model="[article : article]"/>
                        </g:if>
                        <g:else>
                            <g:render template="article" model="[article : article]"/>
                        </g:else>
                    </g:each>
                </ul>
            </div>
            <div class="text-center">
                <g:if test="${articlesCount > 0}">
                    <g:paginate uri="/articles/${category.code}" class="pagination-sm" total="${articlesCount ?: 0}" />
                </g:if>
            </div>
		</div>
        <content tag="script">
            <script>
            $(function() {
                $('.category-sort-link').click(function(e) {
                    $('#category-sort-input').val($(this).data('sort'));
                    $('#category-order-input').val($(this).data('order'));
                    e.preventDefault();
                    $('#category-filter-form')[0].submit();
                });

                $('#search-field').focus(function() {
                    $('#job-filter').show();
                });

                $('#job-filter-close').focus(function() {
                    $('#job-filter').hide();
                });

                $('#filter-job-group li').click(function() {
                  $('#filter-job-group li').removeClass('active');
                  $(this).addClass('active');

                  var id = $(this).data('id');

                  $('.filter-duty').hide();
//                  $('.filter-duty input').prop('checked', false);
                  $('#filter-duty-'+id).show();
                  $('#filter-group-input').val(id);
                });
            });
            </script>
        </content>
	</body>
</html>
