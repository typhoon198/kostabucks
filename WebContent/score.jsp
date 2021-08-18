	<%@ page contentType="text/html; charset=UTF-8" import= "java.text.DecimalFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%!
int totalScore=0;//총점
double count=0;//별점주기 참여횟수
DecimalFormat df = new DecimalFormat("#.#");
//java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
//import 필요없이 ;
%>
<%String score = request.getParameter("score");
totalScore += Integer.parseInt(score);
++count; 
double avg = totalScore/count;
//double avg = (double)totalScore / cnt; 
%>
<br>
선택하는 별점은 <%=score%>입니다."); 
<br>
별점 총점은 <%=totalScore%>입니다."); 
<br>
평점은 <%=avg%>입니다.");
<br>
평점은 <%=df.format(avg)%> 
<br>
<a href = "./score.html">별점주기</a>

</body>
</html>