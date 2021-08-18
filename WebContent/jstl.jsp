<%@page import="com.day.dto.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="lang" value="ko"></c:set>
<c:if test="${lang=='ko'}"><%--EL기반이므로 ${}필요 --%>
안녕하세요
</c:if>

<c:choose>
<c:when test="${lang=='ko'}">안녕하세요</c:when>
<c:when test="${lang=='fr'}">Bonjour</c:when>
<c:otherwise>HEELO</c:otherwise>  
</c:choose>
<%--SQL과 문법과 비슷 --%>
<%--SQL에서 반목문,조건문을 못써서 PLSQL --%>
<%--EL에서 반목문,조건문을 못써서 JSLT --%>

<c:forEach begin="1" end="5" step="2" var="i">
${i}
</c:forEach>
<hr>
<%//서블릿에서 할일
List<Product> list = new ArrayList<>();
list.add(new Product("C0001","A",10));
list.add(new Product("C0002","B",10));
list.add(new Product("C0003","C",10));
request.setAttribute("list",list);
//이 JSP로 forward 됐다
%>

<c:set var="products" value="${requestScope.list }"/>
<c:forEach var="p" items="${prodcuts}" varStatus="statusObj">
${statusObj.index }-- ${p.prod_no}:${p.prod_name}:${p.prod_price} <br>
</c:forEach>

<%--향상된 for문 --%>
	








</body>
</html>