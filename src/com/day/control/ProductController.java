package com.day.control;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.day.dto.Product;
import com.day.exception.FindException;
import com.day.service.ProductService;

public class ProductController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String servletPath = request.getServletPath();
		String methodName = servletPath.split("/", 2)[1];   //"/login" /를 기준으로 문자를 2개로  잘라서(""+login) 2번째 [1]

		try {

			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class ,HttpServletResponse.class);
			String viewPath = (String) method.invoke(this, request, response);
			return viewPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail.jsp";
		}
	}


	public String productlist(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ProductService service = ProductService.getInstance() ;
		String path;
		try {
			List<Product> list = service.findAll();
			request.setAttribute("productList", list);
			path = "productlist.jsp"; 
		} catch (FindException e) {
			e.printStackTrace();
			path = "fail.jsp";
		}

		return path;
	}

	public String productinfo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String prod_no = request.getParameter("prod_no");
		ProductService service = ProductService.getInstance() ;

		String path="";
		try {
			Product p = service.findByNo(prod_no);
			request.setAttribute("p", p);
			path = "productinfo.jsp";

		} catch (FindException e) {
			e.printStackTrace();
			path = "fail.jsp";
		}
		return path;
	}


}
