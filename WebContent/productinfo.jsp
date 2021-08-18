<%@page import="com.day.dto.Product"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
트리거 이벤트 사용할때 주의: section에 추가되었을때 페이지에 이구문 2번
다른 src를 사용하는 script 로 인식
-->
<script>
  $(function(){
	$('div.productinfo>div>ol>li>button').click(function(){
		var prod_no = $(this).parents('ol').find('li>span.prod_no').html();
		console.log(prod_no);
		var quantity = $(this).parents('ol').find('li>input[type=number]').val();
		console.log(quantity);
		$.ajax({
		 	url: './putcart',
		  	method: 'get',
			data: {prod_no: prod_no, quantity: quantity},
			success:function(responseData){
				alert(responseData);
				//div영역보여주기
				$('div.productinfo>div.modal').show();
			}
		});
	  });
	//계속하기 버튼 클릭이벤트처리
	$('div.productinfo>div.modal>button.productlist').click(function(){
		//상품목록보기메뉴에 click이벤트를 강제발생
		console.log("프로덕트");
		$('body > nav > ol > li > a[href="./productlist"]').trigger('click');
	});
	//장바구니보기 버튼 클릭이벤트처리
	$('div.productinfo>div.modal>button.viewcart').click(function(){
		console.log("뷰카트");
		//장바구니메뉴에 click이벤트를 강제발생
		$('body > nav > ol > li > a[href="./viewcart"]').trigger('click');
	});

});
</script>
<c:set var="p" value="${requestScope.p}"/>
<div class="productinfo" style="width:500px">
 <div style="float:left;width:40%">
  <img src="./images/${p.prod_no}.jpg" alt="${p.prod_name}" style="max-width: 100%">
 </div>
 <div style="float:right;width:50%">
  <ol style="list-style-type: none; padding:0xp">
    <li>상품번호:<span class="prod_no">${p.prod_no}</span></li>
    <li>상품명:<span>${p.prod_name}</span></li>
    <li>가격:<span>${p.prod_price}</span></li>
    <li>수량:<input type="number" value="1" max="99"></li>
    <li><button>장바구니 넣기</button></li>
  </ol>
 </div>
 <div class="modal" style="clear:both; display:none;">
	<button class="viewcart">장바구니 보기</button>
	<button class="productlist">계속하기</button>
 </div>
</div>

<!-- 테스트방법
0.톰캣서버 콘솔확인 -> 콘솔예외발생내용
 ->web.xml의 servlet-mapping요소오타
 
 1.상품상세보기 테스트
 http://localhost:8888/myback/productinfo?prod_C0001
 ->404오류 : 서블릿클래스이름, JSP파일이 없는경우
 ->500내부오류 : 서블릿코드, JSP파일이 없는 경우
              톰캣콘솔의 예외확인
 
 2.1이 성공되면 상품목록에서 클릭테스트
 http://localhost:8888/myback/productlist
 
 3.2가 성공되면 메인트스트
  http://localhost:8888/myback/semantic_css_jq.html
 


 -->
