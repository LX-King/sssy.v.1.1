package com.tyut.sssy.infosearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTable;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTableParameter;
import com.tyut.sssy.utils.db.C3P0Util;

public class TaxBurdenSearchDao {

	/**
	 * 税负分析查询表
	 * @param taxBurdenAnalysisSearchTableParameter
	 * @return
	 */
	public List<TaxBurdenAnalysisSearchTable> getTaxBurdenAnalysisSearchTable(
			TaxBurdenAnalysisSearchTableParameter taxBurdenAnalysisSearchTableParameter) {
		
		Connection conn = null;
        String sql = "exec wcs_sfcx ?, ?, ?, ?, ?, ?, ?, ?";
//		String sql = "{call wcs_sfcx (?, ?, ?, ?, ?, ?, ?, ?)}";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxBurdenAnalysisSearchTable> tableList = new ArrayList<TaxBurdenAnalysisSearchTable>();

        TaxBurdenAnalysisSearchTable taxBurdenAnalysisSearchTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxBurdenAnalysisSearchTableParameter.getNd());
            ps.setString(2, taxBurdenAnalysisSearchTableParameter.getSssqQ());
            ps.setString(3, taxBurdenAnalysisSearchTableParameter.getSssqZ());
            ps.setString(4, taxBurdenAnalysisSearchTableParameter.getSwjgDm());
            ps.setString(5, taxBurdenAnalysisSearchTableParameter.getQyzclx());
            ps.setString(6, taxBurdenAnalysisSearchTableParameter.getCy());
            ps.setString(7, taxBurdenAnalysisSearchTableParameter.getHydl());
            ps.setString(8, taxBurdenAnalysisSearchTableParameter.getSflx());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxBurdenAnalysisSearchTable = new TaxBurdenAnalysisSearchTable();
            	
            	taxBurdenAnalysisSearchTable.setDm(rs.getString("dm"));
            	taxBurdenAnalysisSearchTable.setMc(rs.getString("mc"));
            	taxBurdenAnalysisSearchTable.setSshj1(rs.getBigDecimal("a1"));
            	taxBurdenAnalysisSearchTable.setDqsf(rs.getBigDecimal("a2"));
            	taxBurdenAnalysisSearchTable.setTqsf(rs.getBigDecimal("a3"));
            	taxBurdenAnalysisSearchTable.setSfbdl(rs.getBigDecimal("a4"));
            	
            	tableList.add(taxBurdenAnalysisSearchTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

}
