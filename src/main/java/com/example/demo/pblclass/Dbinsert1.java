package com.example.demo.pblclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dbinsert1 {
    public void DbInsert(int ID, int type, String ipdess, String duankou, String serversid,String dbuser, String dbpassword, Connection con1) throws SQLException {
        PreparedStatement plsq = con1.prepareStatement("INSERT INTO db1.dbconnect (id, ipdess, duankou, serversid, dbuser, dbpassword, type) VALUES (?,?,?,?,?,?,?)");
        plsq.setInt(1, ID);
        plsq.setString(2, ipdess);
        plsq.setString(3, duankou);
        plsq.setString(4, serversid);
        plsq.setString(5, dbuser);
        plsq.setString(6, dbpassword);
        plsq.setInt(7, type);
        plsq.executeUpdate();
        System.out.println("本地mysql数据插入成功");
    }
}
