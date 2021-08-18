package com.day.control;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.day.dto.Customer;
import com.day.dto.OrderInfo;
import com.day.dto.OrderLine;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.FindException;
import com.day.service.OrderService;
import com.day.service.ProductService;

public class CartController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {


		String servletPath = request.getServletPath();
		String methodName = servletPath.split("/",2)[1];

		try {
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String viewPath = (String) method.invoke(this, request,response);

			return viewPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "fail.jsp";
		}
	}
	public String viewcart(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		ProductService service = ProductService.getInstance();
		Map<String, Integer>cart = (Map)session.getAttribute("cart");
		Map<Product, Integer>result = new HashMap<>();
		if(cart != null && cart.size() > 0) {			
			Set<String>prod_nos = cart.keySet();
			for(String prod_no: prod_nos) {
				Product p ;
				try {
					p = service.findByNo(prod_no);
					result.put(p,cart.get(prod_no));//요청속성으로 사용할 result에 추가
				}catch(FindException e) {
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("result", result);

		String path ="viewcart.jsp";
		return path;
	}

	public String putcart(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Map<String, Integer>cart = (Map)session.getAttribute("cart");
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart",cart);
		}
		String prod_no = request.getParameter("prod_no");
		String quantity = request.getParameter("quantity");
		Integer intQuantity = new Integer(quantity);
		Integer oldQuantity = (Integer)cart.get(prod_no);
		if(oldQuantity!=null) {
			intQuantity +=oldQuantity; //수량 누적하기
		}
		cart.put(prod_no, intQuantity);
		String path ="success.jsp";
		return path;

	}

}
