package com.tyut.sssy.report.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.report.domain.FirmTaxCollectionSituation;
import com.tyut.sssy.utils.db.C3P0Util;

public class FirmTaxCollectionSituationDao {

	/**
	 * 取得前10的记录
	 * @param year
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public List<FirmTaxCollectionSituation> getTopTenRecords(String year,
			String sqQ, String sqZ, String swjgDm) {
		Connection conn = null;
        String sql = "";
        
		sql = "select top 10 * from dss_qysstz (?, ?, ?, ?, ?) where xm not like '%合计%' order by a12 desc";
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FirmTaxCollectionSituation> tableList = new ArrayList<FirmTaxCollectionSituation>();

        FirmTaxCollectionSituation firmTaxCollectionSituation = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, "");
            ps.setString(3, swjgDm);
            ps.setString(4, year);
            ps.setString(5, sqZ);

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmTaxCollectionSituation = new FirmTaxCollectionSituation();
            	firmTaxCollectionSituation.setNsrsbh(rs.getString("xm").trim());
            	firmTaxCollectionSituation.setBnYjss(rs.getBigDecimal("a11"));
            	firmTaxCollectionSituation.setBnSjss(rs.getBigDecimal("a12"));
            	firmTaxCollectionSituation.setBnQjss(rs.getBigDecimal("a13"));            	
            	tableList.add(firmTaxCollectionSituation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	/**
	 * 获得相对应的数据
	 * @param year
	 * @param string
	 * @param swjgDm
	 * @param nsrsbh
	 * @param string2
	 * @return
	 */
	public BigDecimal getRecordByNsrsbh(String year, String sqZ,
			String swjgDm, String nsrsbh, String type) {
		
		Connection conn = null;
        String sql = "";
        
        if ("bqYjss".equals(type)) {
        	// 取a11
        	sql = "select a11 as a from dss_qysstz (?, ?, ?, ?, ?) where xm = ?";
        } else if ("bqSjss".equals(type)) {
        	// 取a12
        	sql = "select a12 as a from dss_qysstz (?, ?, ?, ?, ?) where xm = ?";
        } else if ("bqQjss".equals(type)) {
        	// 取a13
        	sql = "select a13 as a from dss_qysstz (?, ?, ?, ?, ?) where xm = ?";
        }
		
        PreparedStatement ps = null;
        ResultSet rs = null;
        BigDecimal record = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, "");
            ps.setString(3, swjgDm);
            ps.setString(4, year);
            ps.setString(5, sqZ);
            ps.setString(6, nsrsbh);

            rs = ps.executeQuery();

            if (rs.next()) {
            	record = rs.getBigDecimal("a");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
		
		return record;
	}

}
