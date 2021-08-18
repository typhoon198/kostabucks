package com.day.control;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

public class OrderController implements Controller{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String servletPath = request.getServletPath();
		String methodName = servletPath.split("/",2)[1];
		
		try {
			
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String viewPath = (String) method.invoke(this,request, response);
			
			return viewPath;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return "fail.jsp";
		}

		
	}
	public String addorder(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");

		if(c==null) {
			//로그인 안된 사용자 status:0  장바구니 없음 status:-1,추가 실패 :status:-2, / msg:실패이유, 
			//정상처리 : status:1
			request.setAttribute("status", 0);
		} else {
			//----로그인된 경우 테스트----
			//Customer c = new Customer();
			//c.setId("id1");
			//1.장바구니 내용
			//-----------------------
			Map<String, Integer> cart = (Map)session.getAttribute("cart");
			if(cart != null && cart.size() > 0) {
				//2.장바구니 내용을 OrderInfo객체로 변환
				OrderInfo info = new OrderInfo();
				List<OrderLine> lines = new ArrayList<>();
				for(String prod_no: cart.keySet()) {
					int quantity = cart.get(prod_no);

					OrderLine line = new OrderLine();	//주문상세
					Product order_p = new Product();
					order_p.setProd_no(prod_no);
					line.setOrder_p(order_p);	//주문상품
					line.setOrder_quantity(quantity);	//주문수량
					lines.add(line);

				}
				info.setLines(lines);//주문상세들
				info.setOrder_c(c);//주문자

				OrderService service = OrderService.getInstance();
				try {
					service.add(info);//주문추가
					session.removeAttribute("cart");//장바구니 비우기
					request.setAttribute("status", 1);

				}catch(AddException e){
					e.printStackTrace();
					request.setAttribute("msg", e.getMessage());
					request.setAttribute("status", -2);
				}
			} else {//장바구니가 비어있는 경우
				request.setAttribute("status", -1);
			}
		}
		String path= "addorder.jsp";
		return path;
	}
	
	public String orderlist(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		if(c == null) {//로그인이 안되었거나 세션이 끊긴상태
			request.setAttribute("msg", 0);//로그인 안한경우
		} else {
			//2.비지니스로직 호출
			OrderService service;
			service = OrderService.getInstance();
			try {
				List<OrderInfo> infos = service.findById(c.getId());
				request.setAttribute("infos", infos);
			} catch (FindException e) {
				e.printStackTrace();
			}
		}
		
		String path = "orderlist.jsp";
		return path;
	}
	
}
