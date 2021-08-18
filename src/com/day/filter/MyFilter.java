package com.day.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")//2.5버전에는 web.xml에 <filter></filter>
public class MyFilter implements Filter {
	public MyFilter() {
		System.out.println("MyFilter 객체 생성됨");    
	}
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("MyFilter init() 호출됨");	
		}

	public void destroy() {
		System.out.println("MyFilter destroy() 호출됨");
	}
	//요청에 대한전처리
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter doFilter() 호출됨"); //인코딩,암호화,인증
		chain.doFilter(request, response);
	}

}
