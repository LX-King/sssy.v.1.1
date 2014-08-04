package com.tyut.sssy.junit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tyut.sssy.utils.db.C3P0Util;

public class TestDataImport {

	public void dataImport() {
		Connection conn = null;
        String sql = "exec dss_trans ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "2012");
            ps.setString(2, "");
            ps.setString(3, "01");
            ps.setString(4, "03");
            ps.setString(5, "月");
            ps.setString(6, "Y");
            ps.setString(7, "Y");
            ps.setString(8, "Y");
            ps.setString(9, "Y");
            ps.setString(10, "Y");
            ps.setString(11, "Y");
			
            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
	}
	
	public void dataCalc() {
		Connection conn = null;
        String sql = "exec dss_calc ?, ?, ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "2012");
            ps.setString(2, "01");
            ps.setString(3, "03");
            ps.setString(4, "2011");
            ps.setString(5, "季");
			
            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
	}
	
	public static void main(String args[]) {
		TestDataImport dataImport = new TestDataImport();
//		dataImport.dataImport();
		dataImport.dataCalc();
		System.out.println("success!");
	}
	
}
