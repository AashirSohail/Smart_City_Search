package lab10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

	
	
	public static Connection getCon() throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/L10","root","");	
		return con;  
		
	}
	
}
