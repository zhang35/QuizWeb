package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectMySQL {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;	
	
	//数据库的用户名与密码
	private static String USER = "JT";
	private static String PASS = "123456";

	//JDBC驱动名及数据库URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/vote?useUnicode=true&characterEncoding=utf-8&useSSL=false";	

	/*
	 * 链接数据库
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try{
			//注册JDBC驱动
			Class.forName(JDBC_DRIVER).newInstance();		
			//打开链接
			conn = DriverManager.getConnection(DB_URL,USER,PASS);		
		}catch(Exception e){
			e.printStackTrace();
		}
		if(conn == null){
			System.err.println(
					"警告：DbConnectionManager.getConnection()获得数据库链接失败？\r\n\r\n链接类型：" +
							JDBC_DRIVER + "\r\n链接位置：" + DB_URL);
		}
		return conn;
	}
	
	/*
	 *  功能：执行查询语句
	 */
	public ResultSet executeQuery(String sql){
		try{
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
		return rs;
	}
	/*
	 * 功能：关闭数据库链接
	 */
	public void close(){
		try{
			if(rs != null){
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace(System.err);
		}				
	}
	
	//get和set方法
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRs() {
		return rs;
	}
    
	public static String getUSER() {
		return USER;
	}

	public static void setUSER(String uSER) {
		USER = uSER;
	}

	public static String getPASS() {
		return PASS;
	}

	public static void setPASS(String pASS) {
		PASS = pASS;
	}
}
