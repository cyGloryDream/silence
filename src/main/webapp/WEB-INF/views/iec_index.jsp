<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Mosaddek">
    <meta name="keyword" content="FlatLab, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <link rel="shortcut icon" href="${ctx}/resources/img/favicon.png">

    <title>第三版IEC载荷表提取</title>
	<c:set var="ctx" value="${pageContext.request.contextPath}" />
    <!-- Bootstrap core CSS -->
    <link href="${ctx}/resources/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/resources/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${ctx}/resources/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!--<link href="css/navbar-fixed-top.css" rel="stylesheet">-->
    <!-- Custom styles for this template -->
    <link href="${ctx}/resources/css/style.css" rel="stylesheet">
    <link href="${ctx}/resources/css/style-responsive.css" rel="stylesheet" />
    <link href="${ctx}/resources/css/owl.carousel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/assets/bootstrap-fileupload/bootstrap-fileupload.css" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
			.btn-ablue{
			    font-weight: bold;
			    color: #fff;
			    text-align: center;
			    white-space: nowrap;
			    background-color: #59ace2;
			}    	
			.btn-agreen{
			    font-weight: bold;
			    color: #fff;
			    text-align: center;
			    white-space: nowrap;
			    background-color: #A9D86E;
			}
			.btn-apurple{
			    font-weight: bold;
			    color: #fff;
			    text-align: center;
			    white-space: nowrap;
			    background-color: #8175c7;
			}
			.btn-aorange{
			    font-weight: bold;
			    color: #fff;
			    text-align: center;
			    white-space: nowrap;
			    background-color: #FCB322;
			}
			.btn-askyblue{
			    font-weight: bold;
			    color: #fff;
			    text-align: center;
			    white-space: nowrap;
				background-color:#58c9f3
			}
			#calc_modal li a{
				display: inline;
			}
			.hidden{
				display: none;
			}
			#loading{
				background: rgba(84, 83, 78, 0.5);
			    height: 100%;
			    width: 100%;
			    position: fixed;
			    z-index: 1;
			    top: 0px;
			}
			#loading-center{
				width: 100%;
				height: 100%;
				position: relative;
			}
			#loading-center-absolute {
				position: absolute;
			    left: 45%;
			    top: 45%;
			    height: 150px;
			    width: 150px;
			    margin-top: -75px;
			    margin-left: -75px;
			}
			.object{
				width: 20px;
				height: 20px;
				background-color: #FFF;
				float: left;
				margin-right: 20px;
				margin-top: 65px;
				-moz-border-radius: 50% 50% 50% 50%;
				-webkit-border-radius: 50% 50% 50% 50%;
				border-radius: 50% 50% 50% 50%;
			}
			
			#object_one {	
				-webkit-animation: object_one 1.5s infinite;
				animation: object_one 1.5s infinite;
				}
			#object_two {
				-webkit-animation: object_two 1.5s infinite;
				animation: object_two 1.5s infinite;
				-webkit-animation-delay: 0.25s; 
			    animation-delay: 0.25s;
				}
			#object_three {
			    -webkit-animation: object_three 1.5s infinite;
				animation: object_three 1.5s infinite;
				-webkit-animation-delay: 0.5s;
			    animation-delay: 0.5s;
				
			}
			@-webkit-keyframes object_one {
				75% { -webkit-transform: scale(0); }
			}

			@keyframes object_one {

	 			75% { 
	    			transform: scale(0);
	    			-webkit-transform: scale(0);
	  			}

			}

			@-webkit-keyframes object_two {
	 			75% { -webkit-transform: scale(0); }
			}
			@keyframes object_two {
  				75% { 
				    transform: scale(0);
				    -webkit-transform:  scale(0);
				}

			}

			@-webkit-keyframes object_three {
  				75% { 
  					-webkit-transform: scale(0); }
					}
			@keyframes object_three {
				75% { 
    				transform: scale(0);
    				-webkit-transform: scale(0);
  				}
			}
			
			.hidden {
				display: none;
			}
    	
    </style>
  </head>

  <body class="full-width">

  <section id="container" class="">
      <!--header start-->
      <header class="header white-bg">
          <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
              </button>

              <!--logo start-->
              <a href="index.html" class="logo" >金风<span>科技</span></a>
              <a class="logo"><i>&nbsp;</i><font size="4" color="grey">风资源技术中心</font></a>
              <!--logo end-->
              <div class="horizontal-menu navbar-collapse collapse ">
                  <ul class="nav navbar-nav">
                      <li class="active" data-type='classic'><a href="javascript:;">传统</a></li>
                      <li data-type='custom'><a href="javascript:;">定制</a></li>
                  </ul>

              </div>

          </div>

      </header>
      <!--header end-->
      <!--sidebar start-->

      <!--sidebar end-->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">
          	  <!-- <div class="row">
                  <div class="col-lg-12">
                      <div class="alert alert-danger fade in">
                          <button type="button" class="close close-sm" data-dismiss="alert">
                              <i class="icon-remove"></i>
                          </button>
                         当前计算结果存在异常，请使用定制计算！
                      </div>
                  </div>
              </div> -->
              <div class="row">
              		<div class="col-md-12">
              			<div id="myCarousel" class="carousel slide" >
						<!-- 轮播（Carousel）指标 -->
						<ol class="carousel-indicators">
							<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
							<li data-target="#myCarousel" data-slide-to="1"></li>
							<li data-target="#myCarousel" data-slide-to="2"></li>
						</ol>   
						<!-- 轮播（Carousel）项目 -->
						<div class="carousel-inner">
							<div class="item active" style="height:180px;">
								 <img src="${ctx}/resources/img/andrea-boldizsar-183.jpg" alt="First slide">
							</div>
							<div class="item" style="height:180px;">
								<img src="${ctx}/resources/img/karsten-wurth-inf1783-104653.jpg" alt="First slide">
							</div>
							<div class="item" style="height:180px;">
								<img src="${ctx}/resources/img/karsten-wurth-inf1783-55271.jpg" alt="First slide">
							</div>
						</div>
						<!-- 轮播（Carousel）导航 -->
						<a class="carousel-control left" href="#myCarousel" 
						   data-slide="prev">&lsaquo;</a>
						<a class="carousel-control right" href="#myCarousel" 
						   data-slide="next">&rsaquo;</a>
					</div> 
              		
              		 </div>
                </div>
              	
              <div class="row">
                  <div class="col-md-6">
                      <section class="panel" data-type='classic'>
                          <header class="panel-heading">
                            			参数文件上传
                          </header>
                          <div class="panel-body">
                              <form action="#" class="form-horizontal tasi-form">
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>m=1</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-ablue btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="m=1" class="default m" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3">m=4</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-agreen btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="m=4" class="default m" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3">m=8</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-apurple btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="m=8" class="default m" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>m=10</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-aorange btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="m=10" class="default m" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>风频</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-askyblue btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="matrix" class="default" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>湍流矩阵</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-danger btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="matrix_turb" class="default" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <p id="default-buttons-0" class="default-buttons"><a  href="#calc_modal" data-toggle="modal" class="button-next  btn btn-info">确认并计算</a></p>
                                      <p id="default-buttons-0" class="default-buttons hidden" style="margin-left: 5px;"><a style="position: absolute;right: 145px;" id="classic_download"  data-no class="button-next btn btn-danger">下载结果文件</a></p>

                              </form>
                          </div>
                      </section>
                      <section class="panel hidden" data-type='custom'>
                          <header class="panel-heading">
                            			定制版-参数文件上传
                          </header>
                          <div class="panel-body">
                              <form action="#" class="form-horizontal tasi-form">
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>m文件</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-ablue btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="m" class="default m" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>风频</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-askyblue btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="matrix" class="default" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <div class="form-group">
                                          <label class="control-label col-md-3"><span style="color: red">*</span>湍流矩阵</label>
                                          <div class="controls col-md-9">
                                              <div class="fileupload fileupload-new" data-provides="fileupload">
                                                <span class="btn btn-danger btn-file">
                                                <span class="fileupload-new"><i class="icon-paper-clip"></i> 选择文件</span>
                                                <span class="fileupload-exists"><i class="icon-undo"></i>修改</span>
                                                <input type="file" data-type="matrix_turb" class="default" />
                                                </span>
                                                  <span class="fileupload-preview" style="margin-left:5px;"></span>
                                                  <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none; margin-left:5px;"></a>
                                              </div>
                                          </div>
                                      </div>
                                      <p id="default-buttons-0" class="default-buttons"><a  href="#calc_modal" data-toggle="modal" class="button-next  btn btn-info">确认并计算</a></p>
                                      <p id="default-buttons-0" class="default-buttons hidden" style="margin-left: 5px;"><a style="position: absolute;right: 145px;" id="custom_download" data-no class="button-next btn btn-danger">下载结果文件</a></p>

                              </form>
                          </div>
                      </section>
                  </div>
                  
                   <div class="col-lg-6">
                          <section class="panel">
                              <header class="panel-heading">
                               		  教程
                              </header>
                              <div class="panel-body" style="height: 509px">
                              	<img style="width:auto;margin-left: 150px;max-width: 70%;max-height: 100%"  src="${ctx}/resources/img/timg.jpg" />
                              </div>
                          </section>
                      </div>
                    
              </div>
              <div class="row">
              	<div class="col-lg-12">
                      <div class="panel-group m-bot20" id="accordion">
                          <div class="panel panel-default">
                              <div class="panel-heading">
                                  <h4 class="panel-title">
                                      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" style="font-size: 20px;">
                                       			  使用说明:
                                      </a>
                                  </h4>
                              </div>
                              <div id="collapseOne" class="panel-collapse in" style="height: auto;">
                                  <div class="panel-body">
                                  	<ul style="font-size: 17.5px;">
                                  			<li>1.	<span style="color: #F44336;font-weight: 600;">m=1 文件的输出</span>：WT综合时，设置IEC标准为第三版-第一修正版，综合完成后，IEC详细输出，得到m=1的excel文件；（m=4,8,10…….依此类推，需保证上述4次综合设置中仅有m值不同，其他综合设置需完全相同。）</li>
                                  			<li>2. <span style="color: #F44336;font-weight: 600;">风频文件的输出</span>：选择上述4次综合设置中的任何一次综合结果，输出相应的风频矩阵文件。输出方法为：右击综合结果，选择点击“输出矩阵”，选择“风速矩阵”，点击“全部勾选”，输出方式选择为“Excel”,得到风频矩阵。
                                  			<li>3.<span style="color: #F44336;font-weight: 600;">湍流矩阵文件的输出</span>：选择上述4次综合设置中的任何一次综合结果，输出相应的湍流矩阵文件。输出方法为：右击综合结果，选择点击“输出湍流矩阵”，得到湍流矩阵文件。</li>
                                  			<li>4.将相应的文件，上传至本系统的相应位置，点击确认并计算，将“间断后湍流置0:”按钮由“×”状态变为“√”状态，点击计算，则可得到载荷参数表！</li>
                                  	</ul>
                                  </div>
                              </div>
                          </div>
                          <div class="panel panel-default">
                              <div class="panel-heading">
                                  <h4 class="panel-title">
                                      <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" style="font-size: 20px;">
                                         		备注（点击查看）：
                                      </a>
                                  </h4>
                              </div>
                              <div id="collapseFive" class="panel-collapse collapse" style="height: 0px;">
                                  <div class="panel-body">
                                   <ul style="font-size: 17.5px;">
                                   		<li><span style="color: #F44336;font-weight: 600;">1.上述方法目前仅用于ETM异常较大的情况 ，无法对NTM进行订正。</span></li>
                                   		<li>2.若个别机位NTM湍流值异常偏大，现阶段推荐以下方法：</li>
                                   		<li><img src="/resources/img/gongshi.png"></li>
                                   		<li>其中n为扇区数量，m为Wohler参数；</li>
                                   		<li>T为输出湍流矩阵某风速段对应的某个扇区的湍流值；</li>
                                   		<li>F为输出矩阵某风速段对应的某个扇区的风速频率。</li>
                                   </ul>
                                     	
                                     	
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>

          </section>
      </section>
      <!--main content end-->
      <!--footer start-->
      <!--<footer class="site-footer" style="margin-top: calc(30%-50px);">
          <div class="text-center">
              2013 &copy; FlatLab by VectorLab.
              <a href="#" class="go-top">
                  <i class="icon-angle-up"></i>
              </a>
          </div>
      </footer>-->
      <!--footer end-->
  </section>
  <div class="modal fade" id="calc_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header" style="background: #58C9F3;">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title">计算</h4>
              </div>
              <div class="modal-body">
              	<form action="#" class="form-horizontal tasi-form">
	              	  <div class="form-group">
	                    <label class="control-label col-md-3"><i class="icon-dropbox"></i>湍流矩阵置0:</label>
	                    <div class="controls col-md-9">
	                         <div  class="switch switch-square pull-right" data-on-label="<i class=' icon-ok'></i>"
	                          data-off-label="<i class='icon-remove'></i>">
	                                          <input type="checkbox" />
	                         </div>
	                   	</div>
	                </div>
	                <div class="form-group" style="display: none;">
	                    <label class="control-label col-md-3">风频小于</label>
	                    <div class="controls col-md-3" style="width: 17%; margin-left: 58%;">
	                         <input class="form-control" id="var1" type="text" placeholder="风频" value="0.01" />
	                         </div>
	                 </div>
	                 <div class="form-group">
	                    <label class="control-label col-md-3"><i class="icon-code-fork"></i>间断后湍流置0:</label>
	                    <div class="controls col-md-9">
	                         <div class="switch switch-square pull-right" data-on-label="<i class=' icon-ok'></i>"
	                          data-off-label="<i class='icon-remove'></i>">
	                                          <input type="checkbox" checked/>
	                         </div>
	                   	</div>
	                </div>
	              </form>
	             </div>
              <div class="modal-footer">
              		<span id="alert_message" style="color: red"></span>
                   <button class="btn btn-success" id="calc" type="button">计算</button>
                  <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
              </div>
      </div>
  </div>
</div>

<div class="modal fade" id="alert_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
          <div class="modal-content">
              <div class="modal-header" style="background: #ff6c60;">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title">提示</h4>
              </div>
              <div class="modal-body">
              	<p style="font-size: 16px;font-weight: 800;"></p>
	          </div>
              <div class="modal-footer">
                  <button data-dismiss="modal" class="btn btn-default" type="button">确认</button>
              </div>
      </div>
  </div>
</div>

<div id="loading" class="hidden">
	<div id="loading-center">
	<div id="loading-center-absolute">
		<div class="object" id="object_one"></div>
		<div class="object" id="object_two"></div>
		<div class="object" id="object_three"></div>
	</div>
	</div>
</div>
<input id="calc_type" type="hidden" value="classic" >

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="${ctx}/resources/js/jquery.js"></script>
    <script src="${ctx}/resources/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="${ctx}/resources/js/jquery.dcjqaccordion.2.7.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/hover-dropdown.js"></script>
    <script src="${ctx}/resources/js/jquery.scrollTo.min.js"></script>
    <script src="${ctx}/resources/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/respond.min.js" ></script>
    <script src="${ctx}/resources/assets/morris.js-0.4.3/morris.min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/assets/morris.js-0.4.3/raphael-min.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/respond.min.js" ></script>
    <script src="${ctx}/resources/js/bootstrap-switch.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/lib/webuploader/webuploader.js"></script>
    <script type="text/javascript" src="${ctx}/resources/js/owl.carousel.js"></script>
     

    <!--common script for all pages-->
	<script type="text/javascript">
		var root ="${ctx}";
		$(function(){
				$("#owl-demo").owlCarousel({
		              navigation : true,
		              slideSpeed : 300,
		              paginationSpeed : 400,
		              singleItem : true,
					  autoPlay:true
	
		          });
				
				$("#myCarousel").carousel({
		              interval: 3000
	            });
				$(".switch").on("switch-change",function(event,data){
					var inputDiv = $(event.target).parents(".form-group").next();
					data.value?inputDiv.slideDown():inputDiv.slideUp();
				});
				
				$(".default").on("change",inputChange);
				$("#calc").on("click",startCalc);
				$("#classic_download").on("click",checkFile);
				$("#custom_download").on("click",checkFile);
				
				//webuploader
				uploader =  WebUploader.create({
					swf: "../../../swf/Uploader.swf", 
					server: "http://10.32.23.213:8080/upload", 
					resize: false, 
					paste: document.body, 
					disableGlobalDnd: true, 
					thumb: {
						width: 100, 
						height: 100, 
						quality: 70, 
						allowMagnify: true, 
						crop: true
					},
				    compress: false,
					prepareNextFile: true,
					threads: true,
					formData: {},//传递是将此参数进行传递
					fileNumLimit: 20,//允许的文件数
					fileSingleSizeLimit:  10 * 1024 * 1024 * 1024,//超过大小的不加入队列
					duplicate: true,
					auto:true
				});
				
				/* Morris.Area({
        			element: 'hero-area',
		        data: [
		          {period: '2010 Q1', iphone: 2666, ipad: null, itouch: 2647},
		          {period: '2010 Q2', iphone: 2778, ipad: 2294, itouch: 2441},
		          {period: '2010 Q3', iphone: 4912, ipad: 1969, itouch: 2501},
		          {period: '2010 Q4', iphone: 3767, ipad: 3597, itouch: 5689},
		          {period: '2011 Q1', iphone: 6810, ipad: 1914, itouch: 2293},
		          {period: '2011 Q2', iphone: 5670, ipad: 4293, itouch: 1881},
		          {period: '2011 Q3', iphone: 4820, ipad: 3795, itouch: 1588},
		          {period: '2011 Q4', iphone: 15073, ipad: 5967, itouch: 5175},
		          {period: '2012 Q1', iphone: 10687, ipad: 4460, itouch: 2028},
		          {period: '2012 Q2', iphone: 8432, ipad: 5713, itouch: 1791}
		        ],

		          xkey: 'period',
		          ykeys: ['iphone', 'ipad', 'itouch'],
		          labels: ['iPhone', 'iPad', 'iPod Touch'],
		          hideHover: 'auto',
		          lineWidth: 1,
		          pointSize: 5,
		          lineColors: ['#4a8bc2', '#ff6c60', '#a9d86e'],
		          fillOpacity: 0.5,
		          smooth: true
      		}); */
				
				
			uploader.on("uploadStart",function(file){
				uploader.options.formData.fileType = file.source.source.fileType;
			});
			uploader.on("uploadSuccess",function(file,response){
				console.log(response);
				if(response.message == "success"){
					var input = $("input[data-type='"+response.fileType+"']:visible");
					var spans = input.parents("span.btn-file").find("span");
					
					if(spans.eq(0).attr("class")=="fileupload-new"){
						var class_temp = spans.eq(0).attr("class");
						
						spans.eq(0).attr("class",spans.eq(1).attr("class"));
						spans.eq(1).attr("class",class_temp);
					}
					
					input.parents("div.fileupload").find("span.fileupload-preview").text(response.fileName);
				}
			});
			
			$(".navbar-nav li").on("click",function(){
				$(this).siblings().removeClass("active");
				$(this).addClass("active");
				$("section[data-type='"+$(this).attr("data-type")+"']").siblings().addClass("hidden");
				$("section[data-type='"+$(this).attr("data-type")+"']").removeClass("hidden");
				$("#calc_type").val($(this).attr("data-type"));
			});
	});
		
		
	function uuid() {
	    var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "-";
	 
	    var uuid = s.join("");
	    return uuid;
	}
	
	function inputChange(){
		var files=$(this)[0].files;
		
		clone(this);
		
		files[0].fileType = $(this).attr("data-type");
		
		var filePath = $(this).val();
		
		if($(this).attr("data-type").indexOf("m=")!=-1){
			if(!filePath.endsWith(".xlsx")){
				$("#alert_modal .modal-body p").text("当前文件只支持xlsx格式文件!");
				$("#alert_modal").modal("show");
				return;
			}
		}else if($(this).attr("data-type") == "matrix"){
			if(!filePath.endsWith(".xlsx")){
				$("#alert_modal .modal-body p").text("当前文件只支持xlsx格式文件!");
				$("#alert_modal").modal("show");
				return;
			}
		}else if($(this).attr("data-type")=="matrix_turb"){
			if(!filePath.endsWith(".txt")){
				$("#alert_modal .modal-body p").text("当前文件只支持txt格式文件!");
				$("#alert_modal").modal("show");
				return;
			}
		}
		uploader.addFiles(files);
	}
	
	function clone(target){
			var clazz ="";
			if($(target).attr("data-type").indexOf("m=")!=-1){
				clazz =" m"
			}
			var file_input = $("<input type ='file' class='default"+clazz+"' data-type='"+$(target).attr("data-type")+"' />");
			$(target).replaceWith(file_input[0]);
			
			$(".default").off();
			$(".default").on("change",inputChange);
		}
	function startCalc(){
		var fileList="";
		if($("input[data-type='m=1']").parents(".fileupload").find("span.fileupload-preview").text()==""&&$("#calc_type").val()=="classic"){
			$("#alert_modal .modal-body p").text("缺少m=1参数文件!");
			$("#calc_modal").modal("hide");
			$("#alert_modal").modal("show");
			return ;
		}
		
		if($("input[data-type='m=10']").parents(".fileupload").find("span.fileupload-preview").text()==""&&$("#calc_type").val()=="classic"){
			$("#alert_modal .modal-body p").text("缺少m=10参数文件!");
			$("#calc_modal").modal("hide");
			$("#alert_modal").modal("show");
			return ;
		}
		
		
		$(".m").each(function(){
			if($(this).parents(".fileupload").find("span.fileupload-preview").text()!=""){
				var type = $(this).attr("data-type");
				fileList+=fileList==""?type:","+type;				
			}
		});
		
		
		
		var varible1 =-1;
		if($("#calc_modal input[type='checkbox']").eq(0).is(":checked")){
			var reg = new RegExp("([1-9]+[0-9]*|0)(\\.[\\d]+)?");
			if(!reg.test($("#var1").val())){
				$("#alert_message").text("请输入0到1之间的小数!");
				setTimeout(function(){
					$("#alert_message").text("");					
				},3000);
				return;
			}
			if(parseFloat($("#var1").val()) <0 || parseFloat($("#var1").val()) > 1 ){
				$("#alert_message").text("请输入0到1之间的小数!");
				setTimeout(function(){
					$("#alert_message").text("");					
				},3000);
				return;
			}
			varible1 = $("#var1").val();
		} 
		var varible2 = $("#calc_modal input[type='checkbox']").eq(0).is(":checked");
		$("#loading").removeClass("hidden");
		$("#calc_modal").modal("hide");
		var server = $("#calc_type").val()=="classic"?"/calc":"/custom_calc";
		$.ajax({
			type:"POST",
			url:root+server,
			data:{varible1:varible1,varible2:varible2,fileList:fileList},
			dataType:"json",
			success:function(data){
				if(data.message == "success"){
					$("#loading").addClass("hidden");
					$("#"+$("#calc_type").val()+"_download").parent().removeClass("hidden");
				}else{
					$("#loading").addClass("hidden");
					$("#alert_modal .modal-body p").text(data.message);
					$("#alert_modal").modal("show");
				}
			}
		});
		
	}
	
	function checkFile(){
	     $.ajax({
	    	 type:"POST",
	    	 url:root +"/checkFile",
	    	 data:{type:$("#calc_type").val()},
	    	 dataType:"json",
	    	 success:function(data){
	    		 if(data.flag){
	    			 window.location.href =root + "/download?type="+$("#calc_type").val();
	    		 }else{
	    			 $("#alert_modal .modal-body p").text("结果文件不存在!");
	    			 $("#alert_modal").modal("show");
	    		 }
	    	 }
	     })
		
	}
	

		
	</script>
    


  </body>
</html>
