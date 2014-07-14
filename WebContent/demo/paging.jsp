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
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<table>
  <thead>
    <tr>
      <th>序号</th>
      <th>三字码</th>
    </tr>
  </thead>
  <tfoot>
  <tr>
    <td colspan="2"><nlft:page /></td>
  </tr>
  </tfoot>
  <tbody>
    <c:forEach items="${nlfPagingData.data}" var="o" varStatus="index">
    <tr>
      <td>${index.count}</td>
      <td>${nlfe:bean(o,'C_CODE')}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
</body>
</html>