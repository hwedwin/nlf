<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>自动分页</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/paging.css" />
<style type="text/css">
table.list {
  width: 100%;
  margin-left: auto;
  margin-right: auto;
  border-collapse: collapse;
  empty-cells: show;
  border: 0;
  border-top: 1px solid #DBDCE2;
  border-bottom: 1px solid #DBDCE2;
}

table.list thead th {
  height: 35px;
  font-weight: normal;
  letter-spacing: 2px;
  color: #666;
  padding-left: 10px;
  background-color: #F2F3F6;
  border: 0;
  border-top: 1px solid #FFF;
  border-bottom: 1px solid #DBDCE2;
  border-right: 1px solid #DBDCE2;
  text-align: left;
  white-space: nowrap;
}

table.list tbody td {
  background-color: #FFF;
  padding-left: 10px;
  padding-top:5px;
  padding-bottom:5px;
  border: 0;
  text-align: left;
  color: #333;
  border-bottom: 1px solid #EBECEF
}

table.list tbody tr.current td {
  background-color: #FEFBF2;
  padding-left: 10px;
  border: 0;
  text-align: left;
  color: #333;
  border-bottom: 1px solid #DEE0E9
}

table.list tbody tr.current td:first-child {
  border-left: 4px solid #5F97FB
}

table.list tfoot td {
  background-color: #F2F3F6;
  height: 40px;
  border: 0;
}
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
<table class="list">
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
      <td>${o.NAME}</td>
      <td>${o.AGE}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</body>
</html>