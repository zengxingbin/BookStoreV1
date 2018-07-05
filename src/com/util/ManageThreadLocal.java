package com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class ManageThreadLocal {
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if(conn == null) {
			conn = C3P0Utils.getConnection();//JNDIUtils.getConnection();

			tl.set(conn);
		}
		return conn;
	}
	
	public static void setAutoCommit(boolean autoCommit) throws SQLException {
		getConnection().setAutoCommit(autoCommit);
	}
	
	public static void commit() throws SQLException {
		getConnection().commit();
	}
	
	public static void rollback() throws SQLException {
		getConnection().rollback();
	}
	
	public static void rollback(Savepoint savepoint) throws SQLException {
		getConnection().rollback(savepoint);
	}
	
	public static Savepoint setSavepoint() throws SQLException {
		return getConnection().setSavepoint();
	}
	
	public static Savepoint setSavepoint(String name) throws SQLException {
		return getConnection().setSavepoint(name);
	}
	
	public static void releaseSavepoint(Savepoint savepoint) throws SQLException {
		getConnection().releaseSavepoint(savepoint);
	}
	
	public static void close() {
		Connection conn = tl.get();
		if(conn != null) {
			tl.remove();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
