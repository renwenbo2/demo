package com.example.demo.controllerDemo;



import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.CodeRe;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@CrossOrigin
@RestController
public class TsvdiAddcontroller {
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
 

    @RequestMapping(value = "/api/add",method = RequestMethod.POST)
    public Object insert(@RequestBody JSONObject jsonObject,HttpServletResponse response) {
        try {
            response.addHeader("Access-Control-Allow-Origin","http://localhost:8080");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long time=System.currentTimeMillis();
            String  time1=String.valueOf(time);
            String time2=time1.substring(5);
            int ID =Integer.parseInt(time2);
            String INV_KIND = jsonObject.getString("INV_KIND");
            String INV_NUM = jsonObject.getString("INV_NUM");
            String BUYER_TAXNO = jsonObject.getString("BUYER_TAXNO");
            String DEDUCT_TYPE1 = jsonObject.getString("DEDUCT_TYPE");
            int DEDUCT_TYPE=Integer.parseInt(DEDUCT_TYPE1);
            String DEDUCT_STATUS1 = jsonObject.getString("DEDUCT_STATUS");
            int DEDUCT_STATUS=Integer.parseInt(DEDUCT_STATUS1);
            String INV_DATE1 = jsonObject.getString("INV_DATE");
             Date Cate = sdf.parse(INV_DATE1);
             java.sql.Date INV_DATE=new java.sql.Date(Cate.getTime());
            String INV_VAT1 = jsonObject.getString("INV_VAT");
            Double INV_VAT = Double.parseDouble(INV_VAT1);
            String APPLY_USER = jsonObject.getString("APPLY_USER");
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
            PreparedStatement psql = con.prepareStatement("INSERT INTO T_SCM_VAT_DEDUCT_INFO (ID,INV_KIND,INV_NUM, INV_DATE, INV_VAT, BUYER_TAXNO, DEDUCT_TYPE, DEDUCT_STATUS, APPLY_USER,APPLY_TIME) " + "VALUES (?,?,?,?,?,?,?,?,?,?)");
            psql.setInt(1, ID);
            psql.setString(2, INV_KIND);
            psql.setString(3, INV_NUM);
            psql.setDate(4, INV_DATE);
            psql.setDouble(5,INV_VAT);
            psql.setString(6, BUYER_TAXNO);
            psql.setInt(7,DEDUCT_TYPE);
            psql.setInt(8,DEDUCT_STATUS);
            psql.setString(9,APPLY_USER);
            psql.setDate(10,INV_DATE);
            psql.executeUpdate();
            CodeRe codeRe = new CodeRe();
            codeRe.setMsg("添加成功");
            con.close();
            psql.close();
            return codeRe;

        } catch (SQLException | ParseException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
            CodeRe codeRe = new CodeRe();
            codeRe.setMsg("添加失败，sql出现错误,发票号码已经存在");
            System.out.println(se);
            return codeRe;
        } catch (Exception s) {
            s.printStackTrace();
            CodeRe codeRe = new CodeRe();
            codeRe.setMsg("添加失败，系统错误");
            System.out.println(s);
            return codeRe;
        }

    }

}
