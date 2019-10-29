<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>华为活动室预约系统</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/laydate/laydate.js"></script>
		<script type="text/javascript">
		
		
		function onedetail(day,start,end,subject,content){
			
			
			 $("#allAppTime").html(day+" "+start+":00----"+day+" "+end+":00");
			 $("#allAppSubject").html(subject);
			 $("#allAppContent").html(content);
	
		}
		
		function cancel(day,start,end){

			$.post(
					"${pageContext.request.contextPath}/cancelreserve",
					{"day":day,"start":start,"end":end },
					function(data){
						alert("取消成功");
					},
					"json"
				);		
			
			 
	
		}
	
	$(function(){
		 	
		//为输入框绑定事件
		$("#appSubject").focus(function(){
			//聚焦时获得输入框的内容
			var dayInput = $("#appTime").val();

			$.post(
					"${pageContext.request.contextPath}/reserveinfo",
					{"day":dayInput},
					function(reserveInfo){
					},
					"json"
				);			
			
		})
	});
		
</script>
		<style type="text/css">
            .navbar-default {
                background-color: #c4e5ff;
            }

            .navbar-nav>.active a {
                background-color: #7dd9f5 !important;
            }

            html {
                position: relative;
                min-height: 100%;
            }

            body {
                margin-bottom: 60px;
            }

            .footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                background-color: #868686;
            }

            .foot-block {
                float: left;
                padding-right: 50px;
                padding-top: 10px;
            }

            .foot-title {
                color: red;
                font-size: 1vw;
                margin-bottom: 10px;
                font-weight: bold;
            }

            .foot-content {}

            .foot-ul {
                float: left;
                list-style-type: none;
            }

            .foot-a {
                text-decoration: none;
                color: white;
                font-size: 0.5vw;
                margin-bottom: 40px;
            }

            .foot-a:hover,
            .foot-a:active,
            .foot-a:visited {
                text-decoration: none;
                color: white;
            }

            .foot-ul li {
                margin-bottom: 10px;
            }

            .reservation-nav {
                /* float: left; */

            }

            .reservation-content {
                /* float: right; */
            }

            td {
                padding: 5px;
            }

            textarea {
                resize: none;
            }

            ol {
                font-size: 25px;
            }
		</style>
	</head>
	<body style="background: url(resources/image/bg.jpg) no-repeat; background-size: cover;">
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					 aria-expanded="false">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">西安电子科技大学华为俱乐部</a>
				</div>

				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li><a href="#">华俱概述</a></li>
						<li><a href="#">面试流程</a></li>
						<li class="active"><a href="#">活动室预约</a></li>
						<li><a href="#">图书借阅</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<!-- 用户没有登录 -->
			<c:if test="${empty user}">
				<li><a href="./login.jsp">登录</a></li>
			</c:if>
			<!-- 用户已经登录 -->
			<c:if test="${!empty user}">
				<li><p class="navbar-text">${user.username }</p></li>
				<li><a href="${pageContext.request.contextPath }/loginout">退出</a></li>
			</c:if>

					</ul>
				</div><!-- /.navbar-collapse -->
			</div><!-- /.container-fluid -->
		</nav>

		<div class="container">
			<div class="row">
				<div class="col-lg-3 hidden-xs hidden-sm hidden-md">
					<div class="reservation-nav" style="margin-top: 50px;">
						<ul class="nav nav-pills nav-stacked">
							<li role="presentation" class="active"><a href="#reservationTab" data-toggle="tab">我要预约</a></li>
							
							<li role="presentation"><a href="#myReservationTab" data-toggle="tab">我的预约</a></li>
							<li role="presentation"><a href="#rulesTab" data-toggle="tab">预约细则</a></li>
						</ul>
					</div>
				</div>
                <div class="col-lg-3 hidden-lg">
                    <div class="reservation-nav" style="margin-top: 50px;">
                        <ul class="nav nav-pills">
                            <li role="presentation" class="active"><a href="#reservationTab" data-toggle="tab">我要预约</a></li>
                            <li role="presentation"><a href="#myReservationTab" data-toggle="tab">我的预约</a></li>
                            <li role="presentation"><a href="#rulesTab" data-toggle="tab">预约细则</a></li>
                        </ul>
                    </div>
                </div>
				<div class="col-lg-9">
					<div class="reservation-content tab-content">
						<div class="tab-pane fade in active" id="reservationTab">
							<div><%=request.getAttribute("returnInfo")==null?"":request.getAttribute("returnInfo")%></div>
							<div>
								<form method="post" action="${pageContext.request.contextPath }/reserveroom">
									<table style="width: 100%;">
							<tr>
								<td><label for="allAppName">申请者姓名</label></td>
								<td><input type="text" class="form-control" name="name" id="appName"  value=${user.username}></td>
								<td><label for="allAppDepartment">部门</label></td>
								<td><input type="text" class="form-control" name="department" id="appDepartment"  value=${user.department}></td>
							</tr>
							<tr>
								<td><label for="allAppPhone">手机号</label></td>
								<td><input type="text" class="form-control" name="phone" id="appPhone"  value=${user.phonenumber}></td>
								<td><label for="allAppWechat">微信号</label></td>
								<td><input type="text" class="form-control" name="wechat" id="appWechat"  value=${user.wechat}></td>
							</tr>
							<tr>
											<td><label for="appTime">预约时间</label></td>
											<td colspan="3"><input type="text" class="form-control" name="time" id="appTime"></td>
										</tr>
										<tr>
											<td><label for="appSubject">活动主题</label></td>
											<td colspan="3"><input type="text" class="form-control" name="subject" id="appSubject"></td>
										</tr>
										<tr>
											<td><label for="exampleInputEmail1">活动内容</label></td>
											<td colspan="3"><textarea class="form-control" name="content" id="appContent" rows="3"></textarea></td>
										</tr>
						
										<tr>
											<td colspan="4" style="text-align: center;"><button type="submit" class="btn btn-default">提交申请</button></td>
										</tr>
									</table>

								</form>
							</div>
						</div>
					
						<div class="tab-pane fade" id="myReservationTab">
							<h3>我的预约</h3>
							<table class="table table-striped">
								<thead>
									<tr>
										<td>序号</td>
										<td>预约时间</td>
										<td>申请人</td>
										<td>查看申请表</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pagedetails.details }" var="details" varStatus="status">
						        <tr>
										<td>${status.count}</td>
										<td>${details.day} ${details.start}:00----${details.day} ${details.end}:00</td>
										<td>${user.username}</td>
										<td>
											<button class="btn btn-success btn-xs" onclick=
											"onedetail('${details.day}','${details.start}','${details.end}','${details.subject}','${details.content}')"  
											data-toggle="modal" data-target="#allReservationModal">查看申请表</button>
											<button class="btn btn-danger btn-xs" onclick="cancel('${details.day}','${details.start}','${details.end}')">取消预约</button>
										</td>
									</tr>
						
						</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="tab-pane fade" id="rulesTab">
							<h3>预约细则</h3>
							<ol>
								<li>华俱活动室为97号楼正门（门口朝东）进去，正对楼梯口的房间。</li>
								<li>组长可以预约当下时刻到未来14天的时间段。每次不超过6小时。</li>
								<li>
									组员可以预约当下时刻到未来48小时的时间段；<br>
									组员去之前找各自组组长要活动室钥匙；<br>
									活动室一般不作为自习室，组队打游戏使用；<br>
									组员每7天最多使用3次活动室，7天总时长不超过6小时。
								</li>
							</ol>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="allReservationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="appTableModalTitle">申请表</h4>
					</div>
					<div class="modal-body">
						<table style="width: 100%;">
							<tr>
								<td><label for="allAppName">申请者姓名</label></td>
								<td><input type="text" class="form-control" name="name" id="allAppName" disabled value=${user.username}></td>
								<td><label for="allAppDepartment">部门</label></td>
								<td><input type="text" class="form-control" name="department" id="allAppDepartment" disabled value=${user.department}></td>
							</tr>
							<tr>
								<td><label for="allAppPhone">手机号</label></td>
								<td><input type="text" class="form-control" name="phone" id="allAppPhone" disabled value=${user.phonenumber}></td>
								<td><label for="allAppWechat">微信号</label></td>
								<td><input type="text" class="form-control" name="wechat" id="allAppWechat" disabled value=${user.wechat}></td>
							</tr>
							<tr>
								<td><label for="allAppTime">预约时间</label></td>
								<td colspan="3"><text class="form-control" name="time" id="allAppTime" disabled ></text></td>
							</tr>
							<tr>
								<td><label for="allAppSubject">活动主题</label></td>
								<td colspan="3"><text class="form-control" name="subject" id="allAppSubject" disabled ></text></td>
							</tr>
							<tr>
								<td><label for="allAppContent">活动内容</label></td>
								<td colspan="3"><textarea class="form-control" name="content" id="allAppContent" rows="3" disabled></textarea></td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<footer class="footer hidden-xs hidden-sm">
			<div class="container-fluid">
				<div class="foot-block">
					<div class="foot-title">关于我们</div>
					<div class="foot-content">
						<ul class="foot-ul">
							<li><a href="#" class="foot-a">西电华为创新俱乐部</a></li>
						</ul>
					</div>
				</div>
				<div class="foot-block">
					<div class="foot-title">部门介绍</div>
					<div class="foot-content">
						<ul class="foot-ul">
							<li><a href="#" class="foot-a">无线通信一组</a></li>
							<li><a href="#" class="foot-a">无线通信二组</a></li>
							<li><a href="#" class="foot-a">固定带宽组</a></li>
						</ul>
						<ul class="foot-ul">
							<li><a href="#" class="foot-a">云计算与海量数据组</a></li>
							<li><a href="#" class="foot-a">网络媒体宣传组</a></li>
							<li><a href="#" class="foot-a">新媒体中心</a></li>
						</ul>
						<ul class="foot-ul">
							<li><a href="#" class="foot-a">秘书处</a></li>
							<li><a href="#" class="foot-a">火种部</a></li>
							<li><a href="#" class="foot-a">活力工作室</a></li>
						</ul>
					</div>
				</div>
				<div class="foot-block">
					<div class="foot-title">友情链接</div>
					<div class="foot-content">
						<ul class="foot-ul">
							<li><a href="#" class="foot-a">西电图书馆</a></li>
						</ul>
					</div>
				</div>
				<div class="qr-block">
					<img src="" alt="">
				</div>
			</div>
		</footer>
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
			laydate.render({
				elem: '#appTime',
				type: 'datetime',
				range: true
			});
		});
	</script>
</html>
