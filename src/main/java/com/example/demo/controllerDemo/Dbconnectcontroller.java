package com.example.demo.controllerDemo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.CodeRe;
import com.example.demo.pblclass.Dbinsert1;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class Dbconnectcontroller {
    static final String JDBC_ORACLE = "oracle.jdbc.driver.OracleDriver";
    static final String JDBC_MYSQL= "com.mysql.cj.jdbc.Driver";
    CodeRe codeRe;
    public static  String dbuser;

    @RequestMapping(value = "/api/dbconnect",method = RequestMethod.POST)
    public Object Dbconnect(@RequestBody JSONObject jsonObject) {
        try {
            String type1 = jsonObject.getString("TYPE");
            long time = System.currentTimeMillis();
            String time1 = String.valueOf(time);
            String time2 = time1.substring(5);
            int ID = Integer.parseInt(time2);
            int type = Integer.parseInt(type1);
            String ipdess = jsonObject.getString("IPDESS");
            String duankou = jsonObject.getString("DUANKOU");
            String serversid = jsonObject.getString("SERVERSID");
            dbuser = jsonObject.getString("DBUSER");
            String dbpassword = jsonObject.getString("DBPASSWORD");
            String basename = jsonObject.getString("BASENAME");
            if (type == 1) {
                String DB_URL = "jdbc:oracle:thin:@" + ipdess + ":" + duankou + ":" + serversid;
                Class.forName(JDBC_ORACLE);
                System.out.println("连接数据库");
                Connection con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
                System.out.println("oralce数据库连接成功" + con);
                Class.forName(JDBC_MYSQL);
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8", "root", "root");
                System.out.println("本地数据mysql连接成功" + con1);
                Statement stem = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet resultSet = stem.executeQuery("SELECT dbuser FROM dbconnect");
                String aa="数据库没有本条数据";
                if (resultSet.next()){
                    resultSet.beforeFirst();
                    while (resultSet.next()){
                        String dbuser1 = resultSet.getString("dbuser");
                        if (dbuser.equals(dbuser1)){
                            System.out.println("插入数据库已存在");
                            aa="插入数据库已存在";
                            System.out.println(dbuser1);
                            break;
                        }
                    }
                    if (aa.equals("插入数据库已存在")){

                        System.out.println("插入数据库已存在");
                    }else {
                        Dbinsert1 dbinsert1=new Dbinsert1();
                        dbinsert1.DbInsert(ID, type, ipdess, duankou, serversid,dbuser,dbpassword, con1);
                    }
                }else{
                    Dbinsert1 dbinsert1=new Dbinsert1();
                    dbinsert1.DbInsert(ID, type, ipdess, duankou, serversid,dbuser,dbpassword, con1);
                }
                codeRe = new CodeRe();
                codeRe.setMsg("oracle连接数据库成功");
            } else if (type == 2) {
                String DB_URL = "jdbc:mysql://" + ipdess + ":" + duankou + "/" + basename+"?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
                Class.forName(JDBC_MYSQL);
                System.out.println("mysql连接数据库");
                Connection con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
                System.out.println("mysql数据库连接成功" + con);
                Class.forName(JDBC_MYSQL);
                Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8", "root", "root");
                Statement stem = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet resultSet = stem.executeQuery("SELECT dbuser FROM dbconnect");
                String aa="数据库没有本条数据";
                if (resultSet.next()){
                    resultSet.beforeFirst();
                    while (resultSet.next()){
                        String dbuser1 = resultSet.getString("dbuser");
                        if (dbuser.equals(dbuser1)){
                            System.out.println("插入数据库已存在");
                            aa="插入数据库已存在";
                            System.out.println(dbuser1);
                            break;
                        }
                    }
                    if (aa.equals("插入数据库已存在")){
                        System.out.println("插入数据库已存在");
                    }else {
                        Dbinsert1 dbinsert1=new Dbinsert1();
                        dbinsert1.DbInsert(ID, type, ipdess, duankou, serversid,dbuser,dbpassword, con1);
                    }
                }else {
                    Dbinsert1 dbinsert1=new Dbinsert1();
                    dbinsert1.DbInsert(ID, type, ipdess, duankou, serversid,dbuser,dbpassword, con1);
                }
                codeRe = new CodeRe();
                codeRe.setMsg("mysql连接数据库成功");
                return codeRe;

            }
            return codeRe;

        } catch (Exception e) {
            e.printStackTrace();
            CodeRe codeRe = new CodeRe();
            codeRe.setMsg("连接数据库失败");
            return codeRe;
        }

    }


}
