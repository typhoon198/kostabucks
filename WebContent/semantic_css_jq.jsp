<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>KOSTABUCK</title>
    <link rel="stylesheet" href="./css/basic.css" />
    <style>
      /* * {
        box-sizing: border-box;
      } */
      body {
      
        font-size: 100%;
      }
      header {
        background-color: yellow;
        color: #1E3932;
        background-color: #F6F5EF;
        font-size: 2em;
        font-weight: bold;
        width: 100%;
        height: 100px;
        margin: 0px auto;
        line-height: 100px;
        text-align: center;
      }

      header > a.logo {
        background-image: url('./images/logo.png');
        height: 75px;
        width: 75px;
        margin: 16px 0px;
        display: block;
        float: left;
      }
      nav {
        width: 100%;
        height: 60px;
        line-height: 60px;
		margin-left: auto;
        margin-right: auto;
        background-color: #1E3932;
      }
      nav > ol {
           list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
      }
      nav > ol > li {
        float: left;
      }

      nav > ol > li > a {
              text-align: center;
            color: white;
            font-size: 1.1em;
            text-decoration: none;
            display: block;
            padding-left: 15px;
            padding-right: 15px;
      }

      nav > ol > li > a:hover {
            background-color: #2C2A29;
      }
      section {
        clear: both;
        width: 75%;
        height: 500px;
        float: left;
        overflow: auto;
      }
      
              #art1 {
            background-color: #E3E5F1;

        }

        #art2 {
            background-color: #E8CAA7;

        }

        #art1>h3 {
            color: #015863;
        }

        #art2>h3 {
            color: #815932;
        }
        
        
      article {
        width: 95%;
        height: 45%;
        padding: 10px;
        
      }
      #aside-img {
            width: 100%;
        }
        
      aside {
            background-color: #048ABF;
      
        width: 25%;
        height: 500px;
        float: right;
      }
      footer {
		font-size: 1em;
		color: white;
            background-color: #2C2A29;
		text-align: center;
            
        width: 100%;
        height: 80px;
        margin: 0px auto;
        clear: both;
      }
      div#outer{
         	width :500px;
        	
   
      }
      
      div#inner{
     
        position: absolute;
  		top: 50%;
  		left: 35%;
  		transform: translate(-50%,-35%);
      
      }
      
      table.orderlist {
      width:95%;
      
        border: 1px solid;
        border-collapse: collapse;
      }
      table.orderlist tr > td, 
      table.orderlist tr > th {
        width:11%;
        margin: 5px;
        border: 1px solid;
        height: 30px;
      }
      
      table.orderlist tr > th {
      background-color: #f1f1f1;
      }
      
     table.viewcart {
      width:85%;
        border: 1px solid;
        border-collapse: collapse;
      }
      table.viewcart tr > td, 
      table.viewcart tr > th {
      width:20%;
      height:10%;
        margin: 5px;
        border: 1px solid;
        height: 30px;
      }
      
      table.viewcart tr > th {
      background-color: #f1f1f1;
      }
    </style>
    
    
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
      $(function () {
        //DOM트리에서 nav>ol>li>a객체들 찾기
        var $menuObj = $('nav>ol>li>a');
        
        //DOM트리에서 section객체 찾기
        var $section = $('section');
        
        $menuObj.click(function () {
          //클릭된현재객체의 href속성값 얻기 : .attr('href');
          var href = $(this).attr('href');
          switch (href) {
            case './login.html':
            case './signup.html':
            case './productlist':
            case './viewcart':
            case './orderlist':
            	$section.load(href, function(responseTxt, statusTxt, xhr){
            	      if(statusTxt == "error")
            	        alert("Error: " + xhr.status + ": " + xhr.statusText);
            	    });
            	break;
            case './logout':            	
            	$section.load(href, function(responseTxt, statusTxt, xhr){
          	      if(statusTxt == "error")
          	        alert("Error: " + xhr.status + ": " + xhr.statusText);
          	    });
            	location.href="./semantic_css_jq.jsp";
            	break;
           }
          return false;
        });
      });
    </script>
  </head>
  <body>
    <header>
      <a class="logo" href="#"></a>
      KOSTABUCKS
    </header>
    <nav>
      <ol>
      <c:choose>
        <c:when test="${empty sessionScope.loginInfo}">
          <li><a href="./login.html">로그인</a></li>
          <li><a href="./signup.html">가입</a></li>
        </c:when>
        <c:otherwise>
          <li><a>${loginInfo.id}님 반갑습니다.</a></li>
          <li> <a href="./logout">로그아웃</a></li>
        </c:otherwise>
      </c:choose>
      
        <li><a href="./productlist">상품목록</a></li>
        <li><a href="./viewcart">장바구니보기</a></li>
        
      <c:if test="${!empty sessionScope.loginInfo}">
        <li><a href="./orderlist">주문목록</a></li>
      </c:if>  
      </ol>
    </nav>
    <section>
        <article id="art1">
                <h3>파푸아 뉴 기니 블론드 로스트</h3>
            <p> 파푸아 뉴 기니 섬의 화산토와 열대기후, 높은고도 등 커피재배에 최적화 된 조건은 완성된 높은 커피가 되어 우리에게 찾아왔습니다. 패키지는 이 파푸아 뉴 기니 커피의 플로럴한 풍미와
                파푸아 뉴
                기니 섬만의 커피 스토리와 아름다운 떼루아를 그림으로 표현한 것 입니다. 아시아/태평양지역의 특징을 가지고 있으면서도 파푸아 뉴 기니 만의 자스민과 같은 풍미와 복숭아류의 과일에서 느낄
                수 있는
                수분 가득한 쥬시한 풍미가 특징입니다.
      </article>
        <article id="art2">
   <h3>스타벅스 리저브™에콰도르 로하</h3>
            <p>적도 바로 아래에 위치한 에콰도르 로하 지역은 다양한 동식물이 공존할 뿐만 아니라 영양이 풍부한 화산토양, 건기와 우기가 뚜렷한 열대 기후까지 커피 재배를 위한 완벽한 환경을 갖추고
                있습니다.
                에콰도르 로하의 엽서 디자인은 이러한 환경을 표현할 뿐만 아니라 이 커피가 가진 독특한 매력을 그려내고 있습니다. 들판에 핀 야생화를 닮은 커피의 아로마, 싱그러운 허브에서 느껴지는
                은은한
                산미와 달콤한 풍미, 야생화와 들풀속에 숨어있는 재규어를 연상시키는 카카오닙스의 쌉쌀한 피니쉬까지. 무더운 여름, 아이스 커피로도 더할 나위 없는 매력을 가진 에콰도르 로하를 만나보세요.
      </article>
    </section>

    <aside>
                <img src="./images/event.jpg" alt="event" id="aside-img">

            </a>
        </div>
        <P id="aside-text">
            [연회비]<br>
            -연회비국내전용 30,000원<br>
            -국내외겸용(VISA/MasterCard) 30,000원<br>
            </aside>
    <footer>
      사업자등록번호 : 201-81-21515 (주)스타벅스커피 코리아 대표이사 : 송 데이비드 호섭<br/> TEL :
      1522-3232 개인정보 책임자 : 하익성 ⓒ 2021 Starbucks Coffee Company. All Rights Reserved.
    </footer>
  </body>
</html>
