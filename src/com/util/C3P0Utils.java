package com.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
	private static DataSource ds;
	
	static {
		ds = new ComboPooledDataSource();
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
