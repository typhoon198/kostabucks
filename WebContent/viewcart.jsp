<%@page import="java.text.DecimalFormat"%>
<%@page import="com.day.dto.Product"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--  <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
-->
<script>
	$(function() {
		$('div.viewcart>button.addorder').click(function() {
			$.ajax({
				url : './addorder',
				method : 'get',
				success : function(responseData) {
					alert(responseData.trim());
					//상품목록보기메뉴에 click이벤트를 강제 발생
					//$('body > nav > ol > li > a[href="./productlist"]').trigger('click');
				}
			});
		});
	});
</script>
<c:set var="result" value="${requestScope.result}" />
<c:choose>
	<c:when test="${empty result}">
		<!-- null을 자동으로 빈문자열 빈맵으로 result.size()==0 에서 바꾸심 -->
		<!-- EL empty연산자 null이거나 비어있는 경우 true반환 -->
장바구니가 비었습니다.
</c:when>
	<c:otherwise>
	<div class="viewcart">
		<h3>장바구니</h3>
		<table class="viewcart">
			<tr>
				<th>상품번호</th>
				<th>상품명</th>
				<th>가격</th>
				<th>수량</th>
				<th>금액</th>
			</tr>
			<c:forEach var="kv" items="${result}">
				<c:set var="p" value="${kv.key}" />
				<c:set var="quantity" value="${kv.value}" />
				<tr>
					<td>${p.prod_no}</td>
					<td>${p.prod_name}</td>
					<td>${p.prod_price}</td>
					<td>${quantity}</td>
					<td><fmt:formatNumber pattern="#,##0"
							value="${p.prod_price * quantity}" /></td>
				</tr>
			</c:forEach>
		</table>
		<button class="addorder">주문하기</button>
	</div>
	</c:otherwise>
</c:choose>
