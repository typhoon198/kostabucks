package com.day.control;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.day.dto.Customer;
import com.day.exception.FindException;
import com.day.service.CustomerService;

public class CustomerController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		System.out.println("contextPath:" + contextPath +", servletPath:" + servletPath);
		String methodName = servletPath.split("/", 2)[1];   //"/login" /를 기준으로 문자를 2개로  잘라서(""+login) 2번째 [1]
		//split 특정구분자로 문자열으 쪼갤수있음
		//if("login".equals(servletPath)) {
		//	login(request, response);
		//}		
		//메서드 이름"login"으로 메서드호출하기
		try {
			//클래스가 가지고있는 메서드를 반환  인자 getMethod("메서드이름","매개변수들"....)
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			//method.invoke(this, request, response); //매서드호출
			String viewPath = (String) method.invoke(this, request, response);
			return viewPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail.jsp";
		}

	}
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		response.addHeader("Access-Control-Allow-Origin", "*");
		//1. 요청전달데이터 얻기
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");

		//		ServletContext sc = getServletContext();
		//		CustomerService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		CustomerService service = CustomerService.getInstance();
		//2.비지니스로직 호출
		String path ="";
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");


		try {
			Customer loginInfo = service.login(id, pwd);
			//HttpSession session = request.getSession();

			session.setAttribute("loginInfo", loginInfo);
			//			//3.
			//			path = "success";
			path = "success.jsp";

		} catch (FindException e) {
			e.printStackTrace();
			//4.
			//			path = "fail";
			path = "fail.jsp";

		}

		//5.페이지 이동x 
		//RequestDispatcher rd = request.getRequestDispatcher(path);
		//rd.forward(request, response);
		return path;
	}
	public String logout(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		HttpSession session = request.getSession();
		session.invalidate();//세션제거


		
		String path = "success.jsp";
		return path;


	}
}



