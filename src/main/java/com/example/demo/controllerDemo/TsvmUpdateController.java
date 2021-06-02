package com.example.demo.controllerDemo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.CodeRe;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@CrossOrigin
@RestController
public class TsvmUpdateController {
    static final String JDBC_MYSQL= "com.mysql.cj.jdbc.Driver";
    static final String JDBC_ORACLE = "oracle.jdbc.driver.OracleDriver";
    String ipdess;
    String duankou;
    String serversid;
    String dbuser;
    String dbpassword;
    String basename;
    int type;
    Connection con;
    PreparedStatement psql;
    @RequestMapping(value = "/api/update",method = RequestMethod.POST)
    public Object update(@RequestBody JSONObject jsonObject) {
        try {
            String INV_KIND=jsonObject.getString("INV_KIND");
            String INV_NUM=jsonObject.getString("INV_NUM");
            String sjkuser = jsonObject.getString("SJKUSER");
            String dbuser1 = Dbconnectcontroller.dbuser.toString();
            System.out.println("这是从Dbconnectcontroller中取出来的" + dbuser1);
            Class.forName(JDBC_MYSQL);
            Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8", "root", "root");
            System.out.println("本地数据mysql连接成功" + con1);
            Statement stem = con1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = stem.executeQuery("SELECT * FROM dbconnect WHERE dbuser='" + sjkuser + "'");
            while (resultSet.next()) {
                ipdess = resultSet.getString("ipdess");
                duankou = resultSet.getString("duankou");
                serversid = resultSet.getString("serversid");
                dbuser = resultSet.getString("dbuser");
                dbpassword = resultSet.getString("dbpassword");
                basename = resultSet.getString("basename");
                type = resultSet.getInt("type");
            }
            if (type == 1) {
                String DB_URL = "jdbc:oracle:thin:@" + ipdess + ":" + duankou + ":" + serversid;
                Class.forName(JDBC_ORACLE);
                System.out.println("连接数据库");
                con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
                System.out.println("oralce数据库连接成功" + con);
                Class.forName(JDBC_ORACLE);
                System.out.println("连接数据库");
                con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);

            } else if (type == 2) {
                String DB_URL = "jdbc:mysql://" + ipdess + ":" + duankou + "/" + basename + "?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
                Class.forName(JDBC_MYSQL);
                System.out.println("mysql连接数据库");
                con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
                System.out.println("mysql数据库连接成功" + con);
                Class.forName(JDBC_MYSQL);
                System.out.println("连接数据库");
                con = DriverManager.getConnection(DB_URL, dbuser, dbpassword);
            }
            psql = con.prepareStatement("UPDATE T_SCM_VAT_MAIN SET INV_DEDU_RESULT=?,INV_CONFIRM_STATUS=? WHERE INV_KIND=? AND INV_NUM=?");
            psql.setString(1,"1");
            psql.setString(2,"4");
            psql.setString(3,INV_KIND);
            psql.setString(4,INV_NUM);
            psql.executeUpdate();
            con.close();
            psql.close();
            CodeRe cddea=new CodeRe();
            cddea.setMsg("修改成功");
            return cddea;
        } catch (Exception e) {
            e.printStackTrace();
            CodeRe cddea=new CodeRe();
            cddea.setMsg("修改失败");
            return cddea;
        }
    }



}
