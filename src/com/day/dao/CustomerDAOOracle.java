package com.day.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import com.day.dto.Customer;
import com.day.dto.Product;
import com.day.exception.AddException;
import com.day.exception.DuplicatedException;
import com.day.exception.FindException;
import com.day.exception.ModifyException;
import com.day.sql.MyConnection;

public class CustomerDAOOracle implements CustomerDAO {
	public CustomerDAOOracle() throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");
	}
	@Override
	public void insert(Customer c) throws AddException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}
		String insertJoinSQL = "INSERT INTO customer(id, pwd, name,buildingno) VALUES(?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(insertJoinSQL);
			pstmt.setString(1,c.getId());
			pstmt.setString(2,c.getPwd());
			pstmt.setString(3,c.getName());
			pstmt.setString(4,c.getBuildingno());
			int rowcnt = pstmt.executeUpdate();
		if(rowcnt==1) {
			System.out.println("ID: "+c.getId()+"로 가입 성공");
		}
		
		} catch(SQLIntegrityConstraintViolationException e1) {
			e1.printStackTrace();
			throw new DuplicatedException("중복 ID존재");
		}catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("가입에 실패하셨습니다.");
		} finally {
			MyConnection.close(con, pstmt, null);
		}
	}

	@Override
	public Customer selectById(String id) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
			
		}
		String selectByIdSQL = "SELECT * FROM customer WHERE id= ? AND enabled=1";//탈퇴회원 로그인, 정보조회 불가
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt=con.prepareStatement(selectByIdSQL);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String buildingno = rs.getString("buildingno");
				Customer c = new Customer(id, pwd, name,buildingno);
				return c;
			} else {
				throw new FindException("고객정보가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException("고객정보 불러오기를 실패하였습니다");
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public void update(Customer c) throws ModifyException {
		Connection con = null;
		try {
			con=MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}
// [태일] CLI에서만ㅠㅠ
//Scanner 입력시 null "" 구분안됨 (변경 원하지 않을경우 엔터입력)     
		String pwd = c.getPwd();
		String name = c.getName();
		String buildingno = c.getBuildingno();
		int enabled = c.getEnabled();		

		if(pwd.equals("") 
				&& name.equals("")
				&& buildingno.equals("")
				&& enabled==1){
			System.out.println("아무값도 변경하지 않았습니다.");
			return;
		}
		
		String updateSQL = "UPDATE customer	SET ";
		if(!c.getPwd().equals("")) 
			updateSQL += "pwd = '"+pwd+"' ,";	
		   if(!c.getName().equals(""))
			updateSQL += "name = '"+name+"' ,";	
		     if(!c.getBuildingno().equals("")) 
			updateSQL += "buildingno = '"+buildingno+"' ,";

		if(c.getEnabled()==1) {
			updateSQL += "enabled = 1 "; 
			//쉼표처리때문에 enabled를 항상 1로 update 비효율적 ㅠ
		} else{
			updateSQL += "enabled = 0 ";
		}
		updateSQL +="WHERE id = ?";		
		
/*
*[강사님] flag로 변경할경우 SET절 변경할 속성 추가(쉼표처리!)
		String updateSQL = "UPDATE customer	SET ";
		String updateSQL2= "WHERE id = ?";
		boolean flag = false; //변경할 값이 있는 경우 true
		String pwd = c.getPwd();		
		if( pwd != null && !pwd.equals("")) {
			updateSQL += "pwd = '" + pwd + "'";
			flag = true;
		}		

		String name = c.getName();		
		if( name != null && !name.equals("")) {
			if(flag) {
				updateSQL += ",";
			}		
			updateSQL += "name = '" + name + "'";
			flag = true;
		}		

		String buildingno = c.getBuildingno();		
		if( buildingno != null && !buildingno.equals("")) {
			if(flag) {
				updateSQL += ",";
			}	
			updateSQL += "buildingno = '" + buildingno + "'";
			flag = true;
		}
		int enabled = c.getEnabled();		
		if( enabled > -1 ) { //0-탈퇴, 1-활동 
			if(flag) {
				updateSQL += ",";
			}
			updateSQL += "enabled = '" + enabled + "'";
			flag = true;
		}
		if(!flag) {
			throw new ModifyException("수정할 내용이 없습니다");
		}
		updateSQL += updateSQL2; //Where절 추가
*/
		
/*
*[태양님] Statement,Substring으로 쉼표제거
      String updateSQL = "UPDATE customer SET ";
      if (c.getEnabled() == 0) {
         updateSQL += "enabled = '0'";
      } else {
         if (c.getPwd().equals("") && c.getName().equals("") && c.getBuildingno().equals("")) {
            System.out.println("변경할 내용이 없습니다");
            return;
         }
         if (!c.getPwd().equals("")) {
            updateSQL += "pwd = '" + c.getPwd() + "', ";
         }
         if (!c.getName().equals("")) {
            updateSQL += "name = '" + c.getName() + "', ";
         }
         if (!c.getBuildingno().equals("")) {
            updateSQL += "buildingno = '" + c.getBuildingno() + "', ";
         }
         updateSQL = updateSQL.substring(0, updateSQL.length() - 2);
      }
      updateSQL += " WHERE id ='" + c.getId() + "'";
      System.out.println(updateSQL);
      Statement stmt = null;
*[GUI??] 기존 Customer c와 변경정보를 담고있는 Temp Customer인스턴스와 속성 비교
*		  비교하고 값이 다르면 변경
*/
		
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1,c.getId());
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt==1) {
				System.out.println("고객의 내용이 변경되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, null);

		}
		
		
	}
	
}
