package com.tyut.sssy.infosearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenSubIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionSubIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceSubIndexTableParameter;
import com.tyut.sssy.utils.db.C3P0Util;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：DecisionAnalysisSearchDao  
 * 类描述：决策分析类查询  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午09:37:03  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午09:37:03  
 * 修改备注：  
 * @version 
 *
 */
public class DecisionAnalysisSearchDao {

	/**
	 * 税收结构分析表
	 * @param taxCollectionStructureAnalysisTableParameter
	 * @return
	 */
	public List<TaxCollectionStructureAnalysisTable> getTaxCollectionStructureAnalysisTable(
			TaxCollectionStructureAnalysisTableParameter taxCollectionStructureAnalysisTableParameter) {
		Connection conn = null;
        String sql = "select * from dss_zcfx_ssjg (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionStructureAnalysisTable> tableList = new ArrayList<TaxCollectionStructureAnalysisTable>();

        TaxCollectionStructureAnalysisTable taxCollectionStructureAnalysisTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCollectionStructureAnalysisTableParameter.getSwjg());
            ps.setString(2, taxCollectionStructureAnalysisTableParameter.getNsrsbh());
            ps.setString(3, taxCollectionStructureAnalysisTableParameter.getQyzclx());
            ps.setString(4, taxCollectionStructureAnalysisTableParameter.getCy());
            ps.setString(5, taxCollectionStructureAnalysisTableParameter.getHy());
            ps.setString(6, taxCollectionStructureAnalysisTableParameter.getSklx());
            ps.setString(7, taxCollectionStructureAnalysisTableParameter.getNd());
            ps.setString(8, taxCollectionStructureAnalysisTableParameter.getSqZ());
            ps.setString(9, taxCollectionStructureAnalysisTableParameter.getXsxm());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionStructureAnalysisTable = new TaxCollectionStructureAnalysisTable();
            	taxCollectionStructureAnalysisTable.setXm(rs.getString("xm"));
            	taxCollectionStructureAnalysisTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionStructureAnalysisTable.setA2(rs.getBigDecimal("a2"));
            	taxCollectionStructureAnalysisTable.setA3(rs.getBigDecimal("a3"));
            	taxCollectionStructureAnalysisTable.setA4(rs.getBigDecimal("a4"));
            	taxCollectionStructureAnalysisTable.setXsxm(taxCollectionStructureAnalysisTableParameter.getXsxm());
            	
            	tableList.add(taxCollectionStructureAnalysisTable);
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
	 * 税收类所有指标分析查询表
	 * @param taxCollectionAllIndexTableParameter
	 * @return
	 */
	public List<TaxCollectionAllIndexTable> getTaxCollectionAllIndexTable(
			TaxCollectionAllIndexTableParameter taxCollectionAllIndexTableParameter) {
		Connection conn = null;
        String sql = "select * from dss_zcfx_syzbfx (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionAllIndexTable> tableList = new ArrayList<TaxCollectionAllIndexTable>();

        TaxCollectionAllIndexTable taxCollectionAllIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCollectionAllIndexTableParameter.getSwjg());
            ps.setString(2, taxCollectionAllIndexTableParameter.getNsrsbh());
            ps.setString(3, taxCollectionAllIndexTableParameter.getQyzclx());
            ps.setString(4, taxCollectionAllIndexTableParameter.getCy());
            ps.setString(5, taxCollectionAllIndexTableParameter.getHy());
            ps.setString(6, taxCollectionAllIndexTableParameter.getNd());
            ps.setString(7, taxCollectionAllIndexTableParameter.getSqQ());
            ps.setString(8, taxCollectionAllIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionAllIndexTable = new TaxCollectionAllIndexTable();
            	taxCollectionAllIndexTable.setXm(rs.getString("xm"));
            	taxCollectionAllIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionAllIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxCollectionAllIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxCollectionAllIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxCollectionAllIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxCollectionAllIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxCollectionAllIndexTable.setA7(rs.getBigDecimal("a7"));
            	taxCollectionAllIndexTable.setA8(rs.getString("a8"));
            	taxCollectionAllIndexTable.setA9(rs.getString("a9"));
            	
            	tableList.add(taxCollectionAllIndexTable);
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
	 * 税收类分指标分析查询表
	 * @param taxCollectionSubIndexTableParameter
	 * @return
	 */
	public List<TaxCollectionSubIndexTable> getTaxCollectionSubIndexTable(
			TaxCollectionSubIndexTableParameter taxCollectionSubIndexTableParameter) {
		Connection conn = null;
        String sql = "select * from dss_zcfx_flzbfx (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxCollectionSubIndexTable> tableList = new ArrayList<TaxCollectionSubIndexTable>();

        TaxCollectionSubIndexTable taxCollectionSubIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxCollectionSubIndexTableParameter.getSwjg());
            ps.setString(2, taxCollectionSubIndexTableParameter.getNsrsbh());
            ps.setString(3, taxCollectionSubIndexTableParameter.getQyzclx());
            ps.setString(4, taxCollectionSubIndexTableParameter.getCy());
            ps.setString(5, taxCollectionSubIndexTableParameter.getHy());
            ps.setString(6, taxCollectionSubIndexTableParameter.getFxzb());
            ps.setString(7, taxCollectionSubIndexTableParameter.getNd());
            ps.setString(8, taxCollectionSubIndexTableParameter.getSqQ());
            ps.setString(9, taxCollectionSubIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxCollectionSubIndexTable = new TaxCollectionSubIndexTable();
            	taxCollectionSubIndexTable.setXm(rs.getString("xm"));
            	taxCollectionSubIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxCollectionSubIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxCollectionSubIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxCollectionSubIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxCollectionSubIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxCollectionSubIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxCollectionSubIndexTable.setA7(rs.getInt("a7"));
            	
            	tableList.add(taxCollectionSubIndexTable);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return tableList;
	}

	public TaxGrowTaxResourceDevelopIndexTable getTaxGrowTaxResourceDevelopIndexTable(
			TaxGrowTaxResourceDevelopIndexTableParameter taxGrowTaxResourceDevelopIndexTableParameter) {
		Connection conn = null;
//        String sql = "select * from dss_zcfx_fzzs (?, ?, ?)";
		String sqlExec = "exec dss_syfzzs ?, ?, ?";
		String sqlQuery = "select * from sj_syfzzs where nd = ? and sssq_q = ? and sssq_z = ?";
		
        PreparedStatement ps = null;
        ResultSet rs = null;

        TaxGrowTaxResourceDevelopIndexTable taxGrowTaxResourceDevelopIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sqlExec);
            ps.setString(1, taxGrowTaxResourceDevelopIndexTableParameter.getNd());
            ps.setString(2, taxGrowTaxResourceDevelopIndexTableParameter.getSqQ());
            ps.setString(3, taxGrowTaxResourceDevelopIndexTableParameter.getSqZ());

            ps.execute();
            
            ps = conn.prepareStatement(sqlQuery);
            ps.setString(1, taxGrowTaxResourceDevelopIndexTableParameter.getNd());
            ps.setString(2, taxGrowTaxResourceDevelopIndexTableParameter.getSqQ());
            ps.setString(3, taxGrowTaxResourceDevelopIndexTableParameter.getSqZ());
            rs = ps.executeQuery();

            if (rs.next()) {
            	taxGrowTaxResourceDevelopIndexTable = new TaxGrowTaxResourceDevelopIndexTable();
            	taxGrowTaxResourceDevelopIndexTable.setNd(rs.getString("nd"));
            	taxGrowTaxResourceDevelopIndexTable.setSqQ(rs.getString("sssq_q"));
            	taxGrowTaxResourceDevelopIndexTable.setSqZ(rs.getString("sssq_z"));
            	
            	taxGrowTaxResourceDevelopIndexTable.setSjss(rs.getBigDecimal("sjss"));
            	taxGrowTaxResourceDevelopIndexTable.setZzl(rs.getBigDecimal("zzl"));
            	taxGrowTaxResourceDevelopIndexTable.setFzzs(rs.getBigDecimal("fzzs"));
            	taxGrowTaxResourceDevelopIndexTable.setTxfx(rs.getBigDecimal("txfx"));
            	
            	taxGrowTaxResourceDevelopIndexTable.setYysrzzl(rs.getBigDecimal("yysrzzl"));
            	taxGrowTaxResourceDevelopIndexTable.setLrzezzl(rs.getBigDecimal("lrzezzl"));
            	taxGrowTaxResourceDevelopIndexTable.setScjyzk(rs.getBigDecimal("scjyzk"));
            	taxGrowTaxResourceDevelopIndexTable.setYycbtx(rs.getBigDecimal("yycbtx"));
            	taxGrowTaxResourceDevelopIndexTable.setYyfytx(rs.getBigDecimal("yyfytx"));
            	taxGrowTaxResourceDevelopIndexTable.setJzcsrl(rs.getBigDecimal("jzcsrl"));
            	taxGrowTaxResourceDevelopIndexTable.setMllbdl(rs.getBigDecimal("mllbdl"));
            	taxGrowTaxResourceDevelopIndexTable.setLssszzl(rs.getBigDecimal("lssszzl"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxGrowTaxResourceDevelopIndexTable;
	}

	/**
	 * 查询指数结构表
	 * @param nd
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public TaxGrowTaxResourceDevelopIndexTable getIndexStructureTable(
			String nd, String sqQ, String sqZ) {
		Connection conn = null;
		String sql = "select * from sj_syfzzs where nd = ? and sssq_q = ? and sssq_z = ?";
		
      PreparedStatement ps = null;
      ResultSet rs = null;

      TaxGrowTaxResourceDevelopIndexTable taxGrowTaxResourceDevelopIndexTable = null;
      try {

          // C3P0创建连接
          conn = C3P0Util.getConnection();
          
          ps = conn.prepareStatement(sql);
          ps.setString(1, nd);
          ps.setString(2, sqQ);
          ps.setString(3, sqZ);
          rs = ps.executeQuery();

          if (rs.next()) {
          	taxGrowTaxResourceDevelopIndexTable = new TaxGrowTaxResourceDevelopIndexTable();
          	taxGrowTaxResourceDevelopIndexTable.setNd(rs.getString("nd"));
          	taxGrowTaxResourceDevelopIndexTable.setSqQ(rs.getString("sssq_q"));
          	taxGrowTaxResourceDevelopIndexTable.setSqZ(rs.getString("sssq_z"));
          	
          	taxGrowTaxResourceDevelopIndexTable.setSjss(rs.getBigDecimal("sjss"));
          	taxGrowTaxResourceDevelopIndexTable.setZzl(rs.getBigDecimal("zzl"));
          	taxGrowTaxResourceDevelopIndexTable.setFzzs(rs.getBigDecimal("fzzs"));
          	taxGrowTaxResourceDevelopIndexTable.setTxfx(rs.getBigDecimal("txfx"));
          	
          	taxGrowTaxResourceDevelopIndexTable.setYysrzzl(rs.getBigDecimal("yysrzzl"));
          	taxGrowTaxResourceDevelopIndexTable.setLrzezzl(rs.getBigDecimal("lrzezzl"));
          	taxGrowTaxResourceDevelopIndexTable.setScjyzk(rs.getBigDecimal("scjyzk"));
          	taxGrowTaxResourceDevelopIndexTable.setYycbtx(rs.getBigDecimal("yycbtx"));
          	taxGrowTaxResourceDevelopIndexTable.setYyfytx(rs.getBigDecimal("yyfytx"));
          	taxGrowTaxResourceDevelopIndexTable.setJzcsrl(rs.getBigDecimal("jzcsrl"));
          	taxGrowTaxResourceDevelopIndexTable.setMllbdl(rs.getBigDecimal("mllbdl"));
          	taxGrowTaxResourceDevelopIndexTable.setLssszzl(rs.getBigDecimal("lssszzl"));
          	
          }
      } catch (SQLException e) {
          e.printStackTrace();
      } finally {

          // C3P0关闭连接
          C3P0Util.close(ps, rs, conn);
      }

      return taxGrowTaxResourceDevelopIndexTable;
	}

	/**
	 * 税源类所有指标查询
	 * @param taxResourceAllIndexTableParameter
	 * @return
	 */
	public List<TaxResourceAllIndexTable> getTaxResourceAllIndexTable(
			TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_zbcx_sy ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceAllIndexTable> tableList = new ArrayList<TaxResourceAllIndexTable>();

        TaxResourceAllIndexTable taxResourceAllIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxResourceAllIndexTableParameter.getSwjg());
            ps.setString(2, taxResourceAllIndexTableParameter.getNsrsbh());
            ps.setString(3, taxResourceAllIndexTableParameter.getQyzclx());
            ps.setString(4, taxResourceAllIndexTableParameter.getCy());
            ps.setString(5, taxResourceAllIndexTableParameter.getHy());
            ps.setString(6, taxResourceAllIndexTableParameter.getNd());
            ps.setString(7, taxResourceAllIndexTableParameter.getSqQ());
            ps.setString(8, taxResourceAllIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxResourceAllIndexTable = new TaxResourceAllIndexTable();
            	taxResourceAllIndexTable.setXm(rs.getString("xm"));
            	taxResourceAllIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxResourceAllIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxResourceAllIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxResourceAllIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxResourceAllIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxResourceAllIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxResourceAllIndexTable.setA7(rs.getBigDecimal("a7"));
            	taxResourceAllIndexTable.setA8(rs.getString("a8"));
            	taxResourceAllIndexTable.setA9(rs.getString("a9"));
            	
            	tableList.add(taxResourceAllIndexTable);
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
	 * 税源类分指标查询
	 * @param taxResourceSubIndexTableParameter
	 * @return
	 */
	public List<TaxResourceSubIndexTable> getTaxResourceSubIndexTable(
			TaxResourceSubIndexTableParameter taxResourceSubIndexTableParameter) {
		Connection conn = null;
        String sql = "select * from wcs_zbcx_sy6 (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceSubIndexTable> tableList = new ArrayList<TaxResourceSubIndexTable>();

        TaxResourceSubIndexTable taxResourceSubIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxResourceSubIndexTableParameter.getSwjg());
            ps.setString(2, taxResourceSubIndexTableParameter.getNsrsbh());
            ps.setString(3, taxResourceSubIndexTableParameter.getQyzclx());
            ps.setString(4, taxResourceSubIndexTableParameter.getCy());
            ps.setString(5, taxResourceSubIndexTableParameter.getHy());
            ps.setString(6, taxResourceSubIndexTableParameter.getFxzb());
            ps.setString(7, taxResourceSubIndexTableParameter.getNd());
            ps.setString(8, taxResourceSubIndexTableParameter.getSqQ());
            ps.setString(9, taxResourceSubIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxResourceSubIndexTable = new TaxResourceSubIndexTable();
            	taxResourceSubIndexTable.setXm(rs.getString("xm"));
            	taxResourceSubIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxResourceSubIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxResourceSubIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxResourceSubIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxResourceSubIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxResourceSubIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxResourceSubIndexTable.setA7(rs.getInt("a7"));
            	
            	tableList.add(taxResourceSubIndexTable);
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
	 * 税负类所有指标
	 * @param taxBurdenAllIndexTableParameter
	 * @return
	 */
	public List<TaxBurdenAllIndexTable> getTaxBurdenAllIndexTable(
			TaxBurdenAllIndexTableParameter taxBurdenAllIndexTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_zbcx_sf ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxBurdenAllIndexTable> tableList = new ArrayList<TaxBurdenAllIndexTable>();

        TaxBurdenAllIndexTable taxBurdenAllIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxBurdenAllIndexTableParameter.getSwjg());
            ps.setString(2, taxBurdenAllIndexTableParameter.getNsrsbh());
            ps.setString(3, taxBurdenAllIndexTableParameter.getQyzclx());
            ps.setString(4, taxBurdenAllIndexTableParameter.getCy());
            ps.setString(5, taxBurdenAllIndexTableParameter.getHy());
            ps.setString(6, taxBurdenAllIndexTableParameter.getNd());
            ps.setString(7, taxBurdenAllIndexTableParameter.getSqQ());
            ps.setString(8, taxBurdenAllIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxBurdenAllIndexTable = new TaxBurdenAllIndexTable();
            	taxBurdenAllIndexTable.setXm(rs.getString("xm"));
            	taxBurdenAllIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxBurdenAllIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxBurdenAllIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxBurdenAllIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxBurdenAllIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxBurdenAllIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxBurdenAllIndexTable.setA7(rs.getBigDecimal("a7"));
            	taxBurdenAllIndexTable.setA8(rs.getString("a8"));
            	taxBurdenAllIndexTable.setA9(rs.getString("a9"));
            	
            	tableList.add(taxBurdenAllIndexTable);
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
	 * 税负类分指标查询
	 * @param taxBurdenSubIndexTableParameter
	 * @return
	 */
	public List<TaxBurdenSubIndexTable> getTaxBurdenSubIndexTable(
			TaxBurdenSubIndexTableParameter taxBurdenSubIndexTableParameter) {
		Connection conn = null;
        String sql = "select * from wcs_zbcx_sf8 (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxBurdenSubIndexTable> tableList = new ArrayList<TaxBurdenSubIndexTable>();

        TaxBurdenSubIndexTable taxBurdenSubIndexTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxBurdenSubIndexTableParameter.getSwjg());
            ps.setString(2, taxBurdenSubIndexTableParameter.getNsrsbh());
            ps.setString(3, taxBurdenSubIndexTableParameter.getQyzclx());
            ps.setString(4, taxBurdenSubIndexTableParameter.getCy());
            ps.setString(5, taxBurdenSubIndexTableParameter.getHy());
            ps.setString(6, taxBurdenSubIndexTableParameter.getFxzb());
            ps.setString(7, taxBurdenSubIndexTableParameter.getNd());
            ps.setString(8, taxBurdenSubIndexTableParameter.getSqQ());
            ps.setString(9, taxBurdenSubIndexTableParameter.getSqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxBurdenSubIndexTable = new TaxBurdenSubIndexTable();
            	taxBurdenSubIndexTable.setXm(rs.getString("xm"));
            	taxBurdenSubIndexTable.setA1(rs.getBigDecimal("a1"));
            	taxBurdenSubIndexTable.setA2(rs.getBigDecimal("a2"));
            	taxBurdenSubIndexTable.setA3(rs.getBigDecimal("a3"));
            	taxBurdenSubIndexTable.setA4(rs.getBigDecimal("a4"));
            	taxBurdenSubIndexTable.setA5(rs.getBigDecimal("a5"));
            	taxBurdenSubIndexTable.setA6(rs.getBigDecimal("a6"));
            	taxBurdenSubIndexTable.setA7(rs.getInt("a7"));
            	
            	tableList.add(taxBurdenSubIndexTable);
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
