package com.day.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.day.service.CustomerService;
import com.day.service.OrderService;
import com.day.service.ProductService;

@WebServlet(urlPatterns={"/"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DispatcherServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("DispatcherServlet이 요청됨");
		ServletContext sc = getServletContext();
		CustomerService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		ProductService.envProp = sc.getRealPath(sc.getInitParameter("env"));
		OrderService.envProp = sc.getRealPath(sc.getInitParameter("env"));

		//요청servletpath에 따라 사용될 Controller와 method가 달라짐
		String servletPath = request.getServletPath();		
		Controller controller;
		//controller.prop파일 로드
		//ServletContext sc = getServletContext();
	    //String realPath = sc.getRealPath("/WEB-INF/controller.prop");//프로퍼티파일의 실제 경로 찾기
		String realPath =sc.getRealPath(sc.getInitParameter("env.controller"));//프로퍼티파일의 실제 경로 찾기
		Properties env = new Properties();
		env.load(new FileInputStream(realPath));
		
		//요청된 servletpath에 해당하는 클래스이름 찾기
		String controllerClassName = env.getProperty(servletPath);

		try {
			//[확장]컨트롤러들도 싱글톤으로 만들어놓고 getIntstance
			//클래스이름에 해당하는 클래스로드
			Class clazz = Class.forName(controllerClassName);
			//로드된 클래스를 이용한 객체생성
			Object obj = clazz.newInstance();
			//Method getInstanceMethod = clazz.getDeclareMethod("getInstancse",null)
			//getInstanceMethod.invoke....
			
			controller = (Controller)obj;
			//execute메서드 호출
			String viewPath = controller.execute(request, response);
			
			//viewPath : success.jsp 권장     ./success.jsp(x)  
			//forward방식으로 이동할때  지금 사용중인 webcontent 바로 밑이라는 의미를 포함하고있음
			RequestDispatcher rd = request.getRequestDispatcher(viewPath);
			rd.forward(request, response);
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}