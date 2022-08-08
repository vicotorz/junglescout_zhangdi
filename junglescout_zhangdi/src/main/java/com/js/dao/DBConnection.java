package com.js.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *  DB Driver init
 * */
public class DBConnection {

    //数据库连接对象的连接属性
    //todo：这里的明文链接信息可以放入配置管理系统统一管理
    //todo：后续可加入数据库链接池
    private static final String diver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/amazon_info?serverTimezone=Asia/Shanghai";
    private static final String usernaem = "root";
    private static final String possword = "root";
    private static Connection connection = null;

    //使用一个静态块初始化连接对象
    static {
        try {
            //加载驱动类
            Class.forName(diver);
            //2.获得数据库连接对象
            connection = DriverManager.getConnection(url, usernaem, possword);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}
