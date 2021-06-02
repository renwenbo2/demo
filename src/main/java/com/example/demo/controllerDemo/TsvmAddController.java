package com.example.demo.controllerDemo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.CodeRe;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class TsvmAddController {
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
    @RequestMapping(value = "/api/tsvmadd",method = RequestMethod.POST)
    public Object tsvmadd(@RequestBody JSONObject jsonObject){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            Date date = new Date();
            long time = System.currentTimeMillis();
            String time1 = String.valueOf(time);
            String time2 = time1.substring(5);
            int ID = Integer.parseInt(time2);
            String INV_KIND = jsonObject.getString("INV_KIND");
            String INV_NUM = jsonObject.getString("INV_NUM");
            String BUYER_TAXNO = jsonObject.getString("BUYER_TAXNO");
            String SYS_COMPANY_CODE = jsonObject.getString("SYS_COMPANY_CODE");
            String SYS_ORG_CODE = jsonObject.getString("SYS_ORG_CODE");
            String SELLER_TAXNO = jsonObject.getString("SELLER_TAXNO");
            String INV_DATE1 = jsonObject.getString("INV_DATE");
            Date INV_DATE2 = simpleDateFormat.parse(INV_DATE1);
            java.sql.Date INV_DATE=new java.sql.Date(INV_DATE2.getTime());
            String INV_COST1 = jsonObject.getString("INV_COST");
            Double INV_COST = Double.parseDouble(INV_COST1);
            String INV_VAT1 = jsonObject.getString("INV_VAT");
            Double INV_VAT = Double.parseDouble(INV_VAT1);
            String INV_SUM1 = jsonObject.getString("INV_SUM");
            Double INV_SUM = Double.parseDouble(INV_SUM1);
            String INV_FROM = jsonObject.getString("INV_FROM");
            String INV_TYPE = jsonObject.getString("INV_TYPE");
            String INV_COMMENT = jsonObject.getString("INV_COMMENT");
            String INV_INPUT_USER = jsonObject.getString("INV_INPUT_USER");
            String INV_INPUT_TIME1 = jsonObject.getString("INV_INPUT_TIME");
            Date INV_INPUT_TIME2 = simpleDateFormat.parse(INV_INPUT_TIME1);
            java.sql.Date INV_INPUT_TIME=new java.sql.Date(INV_INPUT_TIME2.getTime());
            String BUYER_NAME = jsonObject.getString("BUYER_NAME");
            String BUYER_ADDR_TEL = jsonObject.getString("BUYER_ADDR_TEL");
            String BUYER_BANK = jsonObject.getString("BUYER_BANK");
            String SELLER_NAME = jsonObject.getString("SELLER_NAME");
            String SELLER_ADDR_TEL = jsonObject.getString("SELLER_ADDR_TEL");
            String SELLER_BANK = jsonObject.getString("SELLER_BANK");
            String FULL_CODE = jsonObject.getString("FULL_CODE");
            String CREATE_DATE1 = simpleDateFormat1.format(date);
            Date CREATE_DATE2=simpleDateFormat1.parse(CREATE_DATE1);
            java.sql.Date CREATE_DATE=new java.sql.Date(CREATE_DATE2.getTime());
            String INCOME_MONTH = jsonObject.getString("INCOME_MONTH");
            String IS_SCANNER = jsonObject.getString("IS_SCANNER");
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
            PreparedStatement psql = con.prepareStatement("INSERT INTO T_SCM_VAT_MAIN (ID, BUYER_TAXNO, INV_KIND, INV_NUM,INV_DEDU_RESULT,INV_CONFIRM_STATUS,SYS_COMPANY_CODE,SYS_ORG_CODE,SELLER_TAXNO, INV_DATE, INV_COST, INV_VAT, INV_SUM,INV_FROM,INV_TYPE,INV_STATUS,INV_COMMENT,INV_INPUT_USER,INV_INPUT_TIME,BUYER_NAME,BUYER_ADDR_TEL,BUYER_BANK,SELLER_NAME,SELLER_ADDR_TEL,SELLER_BANK,FULL_CODE,CREATE_DATE,INCOME_MONTH,IS_COLLECT_ALL,IS_SCANNER,IS_SAME_TAXINFO,CERTIFICATION_TYPE,IS_COMPLIANCE_COLLECT) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            psql.setInt(1,ID);
            psql.setString(2,BUYER_TAXNO);
            psql.setString(3,INV_KIND);
            psql.setString(4,INV_NUM);
            psql.setString(5,"1");
            psql.setString(6,"4");
            psql.setString(7,SYS_COMPANY_CODE);
            psql.setString(8,SYS_ORG_CODE);
            psql.setString(9,SELLER_TAXNO);
            psql.setDate(10,INV_DATE);
            psql.setDouble(11,INV_COST);
            psql.setDouble(12,INV_VAT);
            psql.setDouble(13,INV_SUM);
            psql.setString(14,INV_FROM);
            psql.setString(15,INV_TYPE);
            psql.setString(16,"0");
            psql.setString(17,INV_COMMENT);
            psql.setString(18,INV_INPUT_USER);
            psql.setDate(19,INV_INPUT_TIME);
            psql.setString(20,BUYER_NAME);
            psql.setString(21,BUYER_ADDR_TEL);
            psql.setString(22,BUYER_BANK);
            psql.setString(23,SELLER_NAME);
            psql.setString(24,SELLER_ADDR_TEL);
            psql.setString(25,SELLER_BANK);
            psql.setString(26,FULL_CODE);
            psql.setDate(27,CREATE_DATE);
            psql.setString(28,INCOME_MONTH);
            psql.setString(29,"1");
            psql.setString(30,IS_SCANNER);
            psql.setString(31,"1");
            psql.setString(32,"1");
            psql.setString(33,"1");
            psql.executeQuery();
            con.close();
            psql.close();
            CodeRe cddea=new CodeRe();
            cddea.setMsg("添加成功");
            System.out.println(ID);
            return cddea;
        }catch (Exception e) {
            e.printStackTrace();
            CodeRe cddea=new CodeRe();
            cddea.setMsg("添加失败");
            return cddea;
        }

    }
}
