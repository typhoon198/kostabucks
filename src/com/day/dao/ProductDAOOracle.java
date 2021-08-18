package com.day.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.day.dto.Product;
import com.day.exception.FindException;
import com.day.sql.MyConnection;

public class ProductDAOOracle implements ProductDAO{
	                         //업케스팅해서 던짐
	public ProductDAOOracle() throws Exception {
		//JDBC드라이버 로드
		Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("JDBC드라이버로드 성공");
	}

	@Override
	public List<Product> selectAll() throws FindException {
		//DB연결
		Connection con = null;
		try {
			con =MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		//정렬 기준은 설계단계에서 
		String selectALLSQL = "SELECT * FROM product ORDER BY prod_no ASC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<>();
		
		try {
			pstmt = con.prepareStatement(selectALLSQL);//예외 발생 예상 : SQL문 오타, 네트워크 연결 불안정 : SQL예외
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String prod_no = rs.getString("prod_no");	//예외 발생 예상 : 속성(컬럼)이름 오타 : SQL예외
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");
				
				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);
				list.add(p);
			}
			
			if(list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
			return list;  //리턴전에 finally 수행하고 리턴(finally문 위에 있지만)
			
		} catch (SQLException e) {//SQLExecption 검색,추가,수정,삭제 
			e.printStackTrace();  //콘솔에 예외종류, 내용, 줄번호 출력
			throw new FindException(e.getMessage());//예외를 명확히(검색 예외) 알리기 위해(SQLException(원시예외를) 가공해서 반환)
		} finally {
			MyConnection.close(con, pstmt, rs);

		}
		
	}

	@Override
	public List<Product> selectAll(int currentPage) throws FindException {
		int cnt_per_page = 4;//페이지별 보여줄 목록 수
		//전체건수 : 7건, 총페이지수 : 2페이지
		
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<>();
		String selectAllPageSQL = "SELECT *\r\n" + 
				"FROM (SELECT rownum rn, a.*\r\n" + 
				"           FROM   (SELECT *\r\n" + 
				"                        FROM product \r\n" + 
				"                        ORDER BY prod_no ASC\r\n" + 
				"                       ) a\r\n" + 
				"          )\r\n" + 
				"WHERE rn BETWEEN START_ROW(?, ?) AND  END_ROW(?, ?)";

//		String selectAllPageSQL = "SELECT *\r\n" + 
//				"FROM (SELECT rownum rn, a.*\r\n" + 
//				"           FROM   (SELECT *\r\n" + 
//				"                        FROM order_view \r\n" + 
//				"                        --WHERE order_dt BETWEEN '21/01/01' AND '21/03/31' \r\n" + 
//				"                        ORDER BY order_no DESC\r\n" + 
//				"                       ) a\r\n" + 
//				"          )\r\n" + 
//				"WHERE rn BETWEEN START_ROW(?, ?) AND  END_ROW(?, ?)";
		try {
			pstmt = con.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, currentPage);
			pstmt.setInt(2, cnt_per_page);
			pstmt.setInt(3, currentPage);
			pstmt.setInt(4, cnt_per_page);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
//				int rn = rs.getInt("rn");
				String prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");
	
				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);
				list.add(p);
			}
			
			if(list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
			return list; 
			
			
		} catch (SQLException e) {//SQLExecption 검색,추가,수정,삭제 
			e.printStackTrace();  //콘솔에 예외종류, 내용, 줄번호 출력
			throw new FindException(e.getMessage());//예외를 명확히(검색 예외) 알리기 위해(SQLException(원시예외를) 가공해서 반환)
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	public Product selectByNo(String prod_no) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
			
		}
		String selectByNoSQL = "SELECT * FROM product WHERE prod_no = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt=con.prepareStatement(selectByNoSQL);
			pstmt.setString(1,prod_no);
			rs = pstmt.executeQuery();

			if(rs.next()) {
//				prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				java.sql.Date prod_mf_dt = rs.getDate("prod_mf_dt");
				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);
				return p;
			} else {
				throw new FindException("상품번호" +prod_no+"의 상품이 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
	}

	@Override
	public List<Product> selectByName(String word) throws FindException {
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
//		String selectByNameSQL = "SELECT * FROM product WHERE prod_name LIKE '%"+word+"%'";
		PreparedStatement pstmt = null;
		String selectByNameSQL = "SELECT * FROM product WHERE prod_name LIKE ? ORDER BY prod_no";
		ResultSet rs = null;
		List<Product> list = new ArrayList<>();
	
		try {
			pstmt = con.prepareStatement(selectByNameSQL);
			pstmt.setString(1, "%"+word+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String prod_no = rs.getString("prod_no");
				String prod_name = rs.getString("prod_name");
				int prod_price = rs.getInt("prod_price");
				Date prod_mf_dt = rs.getDate("prod_mf_dt");
				Product p = new Product(prod_no, prod_name, prod_price, prod_mf_dt, null);
				list.add(p);
			}
			if(list.size() == 0) {
				throw new FindException("상품이 없습니다.");
			}
//			return list; //여기에 적어도 finally 수행하고 리턴~ 		
		} catch (SQLException e) {
			e.printStackTrace(); 
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(con, pstmt, rs);
		}
		
		return list; 
	}
	
//테스트
//	public static void main(String[] args) {
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			List<Product> all = dao.selectAll();
//			for(Product p : all) {
//				System.out.println(p);//p.toString 자동호출됨
//			}
//			List<Product> part1 = dao.selectAll(1);
//			for(Product p : part1) {
//				System.out.println(p);
//			}
//			List<Product> part2 = dao.selectAll(2);
//			for(Product p : part2) {
//				System.out.println(p);
//			}
//			List<Product> part3 = dao.selectAll(3);
//			for(Product p : part3) {
//				System.out.println(p);
//			}			
//			String prod_no = "X0002";
//			System.out.println(dao.selectByNo(prod_no));
//			System.out.println(dao.selectByNo("Gdd002"));
//		String word = "ㅇ";
//		System.out.println("\""+word+"\"단어를 포함한 상품목록");
//		try {
//			ProductDAOOracle dao = new ProductDAOOracle();
//			List<Product> list = dao.selectByName(word);
//			for(Product p : list) {
//				System.out.println(p);
//			}
//		} catch (FindException e) {		//자식 예외를 먼저 캐치
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		} catch (Exception e) {			//부모 예외를 나중에 캐치
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//
//	}


}
