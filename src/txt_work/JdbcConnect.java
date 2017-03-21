package txt_work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class JdbcConnect { 

	public static final  String  url = "jdbc:mysql://localhost:3306/soft";
	public static final  String driver = "com.mysql.jdbc.Driver";
	public static final  String user = "root";
	public static final  String password = "root";
	private Connection   conn=null;
	private Statement statement=null;
	private ResultSet rs=null;
	
	static{
		 try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Connection getConn(String url,String username,String  password){
		try {
			conn=DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public ResultSet getResultset(String sql){
		try {
		 conn=getConn(url,user,password);
		 statement = conn.createStatement();
		 rs=statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public int update(String sql){
		int i=0;
		try {
		 conn=getConn(url,user,password);
		 statement = conn.createStatement();
		  i=statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	public ArrayList<String> getSummary(String sql,String field) throws SQLException{
		ArrayList<String> lists=new ArrayList<String>();
		ResultSet  resultSet=getResultset(sql);
		while(resultSet.next()){
			lists.add(resultSet.getString(field));
		}
		return lists;
	}
	
	
	public void close(){
		
			try {
				if(rs!=null){
				 rs.close();
			  }
				if(statement!=null){
					statement.close();
				}
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	public static void main(String[] args) throws SQLException {
		JdbcConnect jdbcConnect=new JdbcConnect();
		ArrayList<String> result=jdbcConnect.getSummary("select summary from t_record limit 1,2", "summary");
		System.out.println(result);
    }
}
