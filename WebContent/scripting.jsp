<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%int i=9; //자바주석%>
<%i++; %>
<%out.print("i변수값은 : " +i); %>
<hr>
지역변수 i변수값은 : <%=i %>
<hr>
<%!int i; %>
멤버변수 i변수값은 : <%=this.i %>
<%--<%!out.print("_jspService()의 외부입니다"); %>--%>
<hr>
<%//자바주석 %>
<!-- html주석 -->
<%-- jsp주석 --%>


</body>
</html>