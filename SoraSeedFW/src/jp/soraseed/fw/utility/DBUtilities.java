package jp.soraseed.fw.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import jp.soraseed.fw.exception.SoraSeedFWRuntimelException;

/**
 * データベースユーティリティ
 * @author Tatsuya
 *
 */
public final class DBUtilities {
        
    private DBUtilities(){}

    /**
     * ResultSetとPreparedStatementをクローズする。<br>
     * [クローズ順序]<br>
     * <ol>
     * <li> ResultSet </li>
     * <li> PreparedStatement </li>
     * </ol>
     * 
     * @param rs
     * @param pstmt
     */
    public static void closeRsAndPstmt(ResultSet rs, PreparedStatement pstmt) {

        closePreparedStatement(pstmt);
        closeResultSet(rs);

    }
    
    /**
     * PreparedStatementをクローズする。<br>
     * 
     * @param pstmt
     * @throws SQLException
     */
    public static void closePreparedStatement(PreparedStatement pstmt) {

        // クローズ
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {}
        }
    }

    /**
     * ResultSetをクローズする。<br>
     * 
     * @param rs
     * @throws SQLException
     */
    public static void closeResultSet(ResultSet rs) {

        // クローズ
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {}
        }
    }
    
    /**
     * Connectionをクローズする。<br>
     * 
     * @param con
     */
    public static void closeConnection(Connection con) {

        // クローズ
        if (con != null) {
            try {
            	con.close();
            } catch (SQLException e) {}
        }
    }
    
    /**
     * コネクション作成
     * @param userName oracleユーザー名
     * @param password oracleパスワード
     * @return connection コネクション
     */
    public static Connection createConnection() throws Exception {
        
        
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        Connection con = DriverManager.getConnection(
        		"jdbc:oracle:thin:@153.121.74.216:1521:XE", 
        		"sora", 
        		"sorasora");
        con.setAutoCommit(false);
        return con;
    }
    
    /**
     * コネクション作成
     * @param userName oracleユーザー名
     * @param password oracleパスワード
     * @return connection コネクション
     */
    public static Connection createPostConnection() throws Exception {
//        String host = "153.120.34.153";
        String host = "localhost";
        String port = "5432";
        String dbname = "funddb";
        String passwd = "funduser";
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
        
        Properties props = new Properties();
        props.setProperty("user", "funduser");
        props.setProperty("password", "funduser");
        
        Class.forName("org.postgresql.Driver");
        
        Connection con = DriverManager.getConnection(url, props);
        con.setAutoCommit(false);
        return con;
    }
    
   
}
