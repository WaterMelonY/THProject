package util;

/**
 * Created by job on 2017/6/2.
 */
import util.PropertiesUtil;
import java.sql.*;

public class PoolConnection {

    private static final String JDBC_DRIVER=PropertiesUtil.getValue("jdbc.driver");
    private  static final String  DB_URL=PropertiesUtil.getValue("jdbc.url");
    private  static final String  DB_NAME=PropertiesUtil.getValue("jdbc.username");
    private  static final String  DB_PWD=PropertiesUtil.getValue("jdbc.password");
    public static Connection getConnection(){
        Connection conn=null;
        try {
            Class.forName(JDBC_DRIVER);
            conn=DriverManager.getConnection(DB_URL,DB_NAME,DB_PWD);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeDB(ResultSet rs,Statement stt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("结果集关闭错误！");
            }

        }

        if(stt!=null){
            try {
                stt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("sql池关闭错误！");
            }

        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("链接关闭错误！");
            }

        }


    }

}
