package com.day.service;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import com.day.dao.CustomerDAO;
import com.day.dto.Customer;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.DuplicatedException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;

public class CustomerService {
	private CustomerDAO dao;
//	private static CustomerService service = new CustomerService();
	private static CustomerService service;
	public static String envProp;;
	/*
	 * 가입 signup(c:Customer) : void
	 * 로그인 login(id:String, pwd:String) : void
	 * 정보 조회 detail(id:String) : Customer
	 * 정보 수정 modify(id:String) : void
	 * 탈퇴 leave(id:String) : void
	 */
	private CustomerService() {
		Properties env = new Properties();
		try {
//			env.load(new FileInputStream("classes.prop"));  
			env.load(new FileInputStream(envProp));  
			String className = env.getProperty("customerDAO"); 
			Class c = Class.forName(className);  
			dao = (CustomerDAO)c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public static CustomerService getInstance() {
		if(service == null) {
			service = new CustomerService();
		}
		return service;
	}
	
	
	public void signup(Customer c) throws AddException{
		try {
			dao.insert(c);
		} catch (DuplicatedException e) {
			e.printStackTrace();
			throw new DuplicatedException(e.getMessage());
		} catch (AddException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());

		}
	}

	public Customer login(String id, String pwd) throws FindException{
		Customer c = dao.selectById(id);;
			if(!c.getPwd().equals(pwd)) {
				throw new FindException("로그인실패");
			}
			return c;
	}

	public Customer detail(String id) throws FindException{
			return dao.selectById(id);
	}
	/**
	 * 
	 * @param c
	 * @throws ModifyException
	 */
	public void modify(Customer c) throws ModifyException{
		if(c.getEnabled() == 0) { //
			throw new ModifyException("탈퇴작업은 할 수 없습니다");
		}
//		c.setEnabled(-1); 강사님 정보수정시 Enabled변경 no
		dao.update(c);
	}
	
	/**
	 * 
	 * @param c
	 * @throws ModifyException
	 */
	public void leave(Customer c) throws ModifyException{
		c.setEnabled(0);
		dao.update(c);
	}
	
}
