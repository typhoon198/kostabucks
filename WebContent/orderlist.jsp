<%@page import="com.day.dto.Product"%>
<%@page import="com.day.dto.OrderLine"%>
<%@page import="java.util.Date"%>
<%@page import="com.day.dto.OrderInfo"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	Integer status = (Integer)request.getAttribute("status");
	if(status != null && status == 0){
%>로그인 하세요
<%}else{
	List<OrderInfo> infos = (List)request.getAttribute("infos");

%><table class="orderlist">
<tr><th>주문번호</th><th>주문일자</th><th>상품번호</th><th>상품명</th><th>가격</th><th>수량</th></tr>
<%	for(OrderInfo info : infos){
%>
<tr>	
<%		int order_no = info.getOrder_no();//주문번호
		Date order_dt = info.getOrder_dt();//주문일자
		List<OrderLine> lines = info.getLines();
		int lineSize = lines.size();
		
%>
	<td rowspan ="<%=lineSize%>"><%=order_no %></td>
	<td rowspan ="<%=lineSize%>"><%=order_dt%></td>
<%		for(int i=0; i<lineSize; i++){//주문상세
			OrderLine line = lines.get(i);
			Product p = line.getOrder_p();//주문상품
			int quantity = line.getOrder_quantity();//주문수량
			if(i>0){//주문상품이 여러개인경우%>
				</tr><tr>
<%
			}
%>
	<td><%=p.getProd_no() %></td>
	<td><%=p.getProd_name() %></td>
	<td><%=p.getProd_price() %></td>
	<td><%=quantity %></td>		
<%		}//end for line

%></tr>		
<%	
	}//end for info
%></table>
<%
}//end of else
%>
