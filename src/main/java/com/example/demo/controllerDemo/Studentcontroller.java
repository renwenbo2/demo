package com.example.demo.controllerDemo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.CodeRe;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class Studentcontroller {
    static final String JDBC_MYSQL= "com.mysql.cj.jdbc.Driver";
    CodeRe codeRe;
    String ipdess ="192.168.1.55";
    String duankou="3308";
    String basename= "student";
    String dbuser ="root";
    String dbpassword="root";
    @RequestMapping(value = "/api/student",method = RequestMethod.POST)
    public Object Student(@RequestBody JSONObject jsonObject) throws ClassNotFoundException, SQLException {
            String name=jsonObject.getString("name");
            int age=jsonObject.getInteger("age");
            String classname=jsonObject.getString("classname");
        String DB_URL = "jdbc:mysql://" + ipdess + ":" + duankou + "/" + basename+"?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
        Class.forName(JDBC_MYSQL);
        System.out.println("mysql连接数据库");
        Connection con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
        System.out.println("mysql数据库连接成功" + con);
        Class.forName(JDBC_MYSQL);
        Statement stem = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = stem.executeQuery("SELECT dbuser FROM dbconnect");
        return codeRe;
    }
}
