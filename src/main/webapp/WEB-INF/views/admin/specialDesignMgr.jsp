<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include  page="head.jsp"/>

    <link href="resources/css/lib/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="resources/css/lib/jquery.dataTables.css" rel="stylesheet" type="text/css">
    <link href="resources/css/lib/jquery.toastmessage.css" rel="stylesheet" type="text/css">
    <link href="resources/css/src/main.css" rel="stylesheet" type="text/css">
</head>
<body>

    <jsp:include  page="header.jsp"/>

<div class="left">
    <jsp:include  page="menu.jsp"/>
</div>

<div class="right">
    <div class="main">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h1 class="panel-title">课程设计</h1>
            </div>
            <div class="panel-body" id="opt-body">
                <a class="btn btn-success" href="specialDesign/specialDesignCOR">
                    <span class="glyphicon glyphicon-plus"></span> 新建
                </a>
                <!--<div class="input-group tableSearchContainer col-md-6">
                    <input type="text" id="searchContent" class="form-control" placeholder="内容">
                    <span class="input-group-btn">
                        <button id="searchBtn" class="btn btn-default" type="button">搜索</button>
                    </span>
                </div>-->
                <table id="myTable" class="dataTable">
                    <thead>
                    <tr>
                        <th>标题</th>
                        <th>教师</th>
                        <th>创建时间</th>
                        <th>图钉墙ID</th>
                        <th>精选</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<div class="loading hidden" id="loading">
    <span class="text">Loading...</span>
</div>


<script src="resources/js/lib/jquery-2.0.3.min.js"></script>
<script src="resources/js/lib/bootstrap.min.js"></script>
<script src="resources/js/lib/jquery.dataTables.min.js"></script>
<script src="resources/js/lib/jquery.validate.min.js"></script>
<script src="resources/js/lib/jquery.toastmessage.js"></script>
<script src="resources/js/src/config.js"></script>
<script src="resources/js/src/functions.js"></script>
<script src="resources/js/src/specialDesignMgr.js"></script>

</body>
</html>