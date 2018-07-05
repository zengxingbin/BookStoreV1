package com.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Tomcat jndi
 * @author zyb
 *
 */
public class JNDIUtils {
	private static DataSource ds;
	
	static {
		Context context = null;
		
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			throw new ExceptionInInitializerError(e);
		}
		
		try {
			ds = (DataSource) context.lookup("java:/comp/env/" + "jdbc/mysql");
		} catch (NamingException e) {
			throw new ExceptionInInitializerError(e);
		}
		
	}
	
	public static DataSource getDs() {
		return ds;
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public static void release(ResultSet rs, Statement stm, Connection conn) {
		try {
			if(rs != null) 
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stm != null) 
					stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if(conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
