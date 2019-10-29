<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		
		<style type="text/css">
            body{
                background: url(./resources/image/loginbg.jpg) no-repeat;
                background-size: cover;
                background-attachment: fixed;
            }
			.huawei-logo{
				width: 150px;
				height: 150px;
				margin: 0 auto;
				padding-bottom: 20px;
			}
			img{
				width: auto;
				height: auto;
				max-width: 100%;
				max-height: 100%;
			}
			.login-container{
				position: absolute;
				width: 100%;
				top: 50%;
				transform: translateY(-100%);
			}
			.login-text{
				opacity: 0.6;
				border-radius: 50px;
			}
			.login-text:focus{
				opacity: 0.8;
			}
			.login-button{
				width: 100%;
				border-radius: 50px;
			}
		</style>
	</head>
	<body>
		<div class="login-container">
			<div class="container-fluid">
				<div class="huawei-logo">
					<img src="./resources/image/huawei.png" alt="" style="huawei-logo">
				</div>
				<div class="row">
					<div class="col-lg-offset-5 col-lg-2">
						<form  method="post" action="${pageContext.request.contextPath }/userlogin">
							<div class="form-group">
								<input type="text" class="form-control login-text" name="student_ID" placeholder="学号">
							</div>
							<div class="form-group">
								<input type="password" class="form-control login-text"  name="password" placeholder="密码">
							</div>
							<div class="checkbox">
								<label style="color: white;">
									<input type="checkbox" name="autoLogin">自动登录
									</label>                 
								
							</div>
							<button type="submit" class="btn btn-warning login-button" id="loginSubmit">登录</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>
	
</html>
