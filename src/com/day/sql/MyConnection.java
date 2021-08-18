package com.day.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection{
												//1.여기서 처리 2.사용자가 처리(throws)
	public static Connection getConnection() throws SQLException{

		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "hr";
		Connection con = null;
		con = DriverManager.getConnection(url, user, password);
		
//1.여기서 처리
//		try {
//			con = DriverManager.getConnection(url, user, password);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		return con;
	}
	public static void close(Connection con, Statement stmt, ResultSet rs) {
		try {
			if(rs != null ){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null ){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(con != null ){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


