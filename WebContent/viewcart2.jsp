<%@page import="java.text.DecimalFormat"%>
<%@page import="com.day.dto.Product"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--[세션에서 카드불러오기]
<%Map<String,Integer> cart = (Map)session.getAttribute("cart");
if(cart==null || cart.size()==0){%>
장바구니가 비었습니다.
<%
return;
//}else{
}
%><table>
<%Set<String> prod_nos = cart.keySet();
for(String prod_no: prod_nos){
	Integer quantity = cart.get(prod_no);
%><tr><td><%=prod_no%></td><td><%=quantity%></td></tr>		
<%}
%>
</table>
--%>
<%
Map<Product,Integer>result = (Map)request.getAttribute("result");
if(result==null || result.size()==0){
%>
장바구니가 비었습니다.
<%
return;
//}else{
}
%>
<%DecimalFormat df = new DecimalFormat("#,##0");%>
<h3>장바구니</h3>
<table>
<tr>
<th>상품번호</th><th>상품명</th><th>가격</th><th>수량</th><th>금액</th>
</tr>
<%Set<Product> prod_nos = result.keySet();
for(Product p: prod_nos){
	Integer quantity = result.get(p);
%><tr><td><%=p.getProd_no()%></td>
	<td><%=p.getProd_name()%></td>
	<td><%=p.getProd_price()%></td>
	<td><%=quantity%></td>
	<td><%=df.format(p.getProd_price()*quantity)%>	</td>
	</tr>
<%}
%>
</table>
<button>주문하기</button>