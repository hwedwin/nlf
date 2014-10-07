<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>自动分页</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<link type="text/css" rel="stylesheet" href="${PATH}/css/paging.css" />
<style type="text/css">
*{font-size:14px;}
.nlfPagingForm {
  margin: 0;
  padding: 0;
  float: right;
  margin-top: 20px;
  margin-bottom: 20px;
  text-align: center
}
.nlfPagingForm span {
  display:none;
}
.nlfPagingForm input {
  display:none;
}
.nlfPagingForm a {
  margin-left:3px;
  color: #888;
  text-align: center;
  text-decoration: none;
  background-color: #FFFFFF;
  border: 1px solid #E4E4E4;
  border-radius: 3px;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  padding: 5px 5px;
}
.nlfPagingForm a.current,a.ellipsis {
  border:0;
  border-radius:0;
  -webkit-border-radius:0;
  -moz-border-radius:0;
  padding: 5px 5px;
}
.nlfPagingForm a:hover {
  background-color: #F0F0F0;
}
.nlfPagingForm a.current:hover,a.ellipsis:hover {
  background-color: transparent;
}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<table>
  <thead>
    <tr>
      <th>序号</th>
      <th>姓名</th>
      <th>年龄</th>
    </tr>
  </thead>
  <tfoot>
  <tr>
    <td colspan="3"><nlft:page /></td>
  </tr>
  </tfoot>
  <tbody>
    <c:forEach items="${nlfPagingData.data}" var="o" varStatus="index">
    <tr>
      <td>${index.count}</td>
      <td>${nlfe:bean(o,'NAME')}</td>
      <td>${nlfe:bean(o,'AGE')}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</body>
</html>