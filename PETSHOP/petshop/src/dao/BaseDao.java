package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driverClass; //数据库驱动
    private static String url; //url
    private static String user; //数据库用户名
    private static String password = null; //数据库密码
    private static Connection connection = null; //数据连接对象

    static {
        try {
            Properties properties = new Properties();
            InputStream ras = BaseDao.class.getClassLoader().getResourceAsStream("jdbc.properties");
            try {
                properties.load(ras);
                driverClass = properties.getProperty("driverClass");
                url = properties.getProperty("url");
                user = properties.getProperty("user");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开数据库连接
     */
    public static Connection openConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 关闭数据库链接
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
                BaseDao.connection=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增删改
     * @param sql
     * @param param
     * @return
     */
    public static int excuteUpdate(String sql, Object[] param) {
        connection = null;
        PreparedStatement ps = null;
        int rs = 0;
        //处理sql，执行sql
        try {
            connection = openConnection(); //连接数据库
            ps = connection.prepareStatement(sql); //得到prepareStatement对象
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    ps.setObject(i + 1, param[i]); //为预编译sql设置参数
                }
            }
            rs = ps.executeUpdate(); //执行sql语句
        } catch (SQLException e) {
            e.printStackTrace(); //异常处理
        }finally {
            closeAll(connection,ps,null);
        }
        return rs;
    }
}
