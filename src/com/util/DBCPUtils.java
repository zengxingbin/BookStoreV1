package com.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtils {
	private static DataSource ds;
	
	static {
		Properties properties = new Properties();
		try {
			properties.load(DBCPUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties"));
			ds = BasicDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
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
