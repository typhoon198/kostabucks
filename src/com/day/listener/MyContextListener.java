package com.day.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MyContextListener
 *
 */
@WebListener
public class MyContextListener implements ServletContextListener {

	/**
	 * Default constructor. 
	 */
	public MyContextListener() {
		System.out.println("MyContextListner 객체 생성됨!");    
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	//tomcat이 꺼지기전에 context객체 소멸
	//context소멸을 감지해서 실행되는 매서드
	//tomcat sever가 꺼지기전에 할일이 있으면 이  매서드에 작성 
	public void contextDestroyed(ServletContextEvent sce)  { 
		System.out.println("MyContextListner의   contextDestroyed가 호출됨!");    
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	//tomcat이 시작되면  context객체 생성
	//context객체 생성 초기 실행되는 매서드
	//tomcat servet가 켜지자자마자  이  매서드에 해야할일들 작성(예: DBconnection pool setting )
	public void contextInitialized(ServletContextEvent sce)  { 
		System.out.println("MyContextListner의   contextInitialized가 호출됨!");    
	}

}
