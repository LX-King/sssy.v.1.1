package com.tyut.sssy.infosearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.taxresource.domain.AssetLiabilityTable;
import com.tyut.sssy.taxresource.domain.AssetLiabilityTableParameter;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTable;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTableParameter;
import com.tyut.sssy.taxresource.domain.FirmRunInfoTable;
import com.tyut.sssy.taxresource.domain.FirmRunInfoTableParameter;
import com.tyut.sssy.taxresource.domain.InterestTable;
import com.tyut.sssy.taxresource.domain.InterestTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTable;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTable;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTable;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceSumChangeTable;
import com.tyut.sssy.taxresource.domain.TaxResourceSumChangeTableParameter;
import com.tyut.sssy.utils.db.C3P0Util;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxResourceSearchDao  
 * 类描述：税源类查询 dao  
 * 创建人：梁斌  
 * 创建时间：2012-5-15 上午10:14:09  
 * 修改人：梁斌  
 * 修改时间：2012-5-15 上午10:14:09  
 * 修改备注：  
 * @version 
 *
 */
public class TaxResourceSearchDao {

	/**
	 * 企业重点税源监控企业明细表
	 * @param taxResourceMonitorFirmDetailTableParameter
	 * @return
	 */
	public List<TaxResourceMonitorFirmDetailTable> getTaxResourceMonitorFirmDetailTable(
			TaxResourceMonitorFirmDetailTableParameter taxResourceMonitorFirmDetailTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_02 ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceMonitorFirmDetailTable> tableList = new ArrayList<TaxResourceMonitorFirmDetailTable>();

        TaxResourceMonitorFirmDetailTable taxResourceMonitorFirmDetailTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, taxResourceMonitorFirmDetailTableParameter.getSsjgDm());
            ps.setString(2, taxResourceMonitorFirmDetailTableParameter.getQygm());
            ps.setString(3, taxResourceMonitorFirmDetailTableParameter.getNd());
            ps.setString(4, taxResourceMonitorFirmDetailTableParameter.getSssqQ());
            ps.setString(5, taxResourceMonitorFirmDetailTableParameter.getSssqZ());
            ps.setString(6, taxResourceMonitorFirmDetailTableParameter.getHydlDm());
            ps.setString(7, taxResourceMonitorFirmDetailTableParameter.getQyzclx());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxResourceMonitorFirmDetailTable = new TaxResourceMonitorFirmDetailTable();
            	
            	taxResourceMonitorFirmDetailTable.setA1(rs.getString("a1"));
            	taxResourceMonitorFirmDetailTable.setA2(rs.getString("a2"));
            	taxResourceMonitorFirmDetailTable.setA3(rs.getString("a3"));
            	taxResourceMonitorFirmDetailTable.setA4(rs.getString("a4"));
            	taxResourceMonitorFirmDetailTable.setA5(rs.getString("a5"));
            	
            	tableList.add(taxResourceMonitorFirmDetailTable);
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
	 * 重点税源监测户数统计表
	 * @param taxResourceMonitorTableParameter
	 * @return
	 */
	public List<TaxResourceMonitorTable> getTaxResourceMonitorTable(
			TaxResourceMonitorTableParameter taxResourceMonitorTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_01 ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceMonitorTable> tableList = new ArrayList<TaxResourceMonitorTable>();

        TaxResourceMonitorTable taxResourceMonitorTable = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, taxResourceMonitorTableParameter.getSsjgDm());
            ps.setString(2, taxResourceMonitorTableParameter.getQygm());
            ps.setString(3, taxResourceMonitorTableParameter.getNd());
            ps.setString(4, taxResourceMonitorTableParameter.getSssqQ());
            ps.setString(5, taxResourceMonitorTableParameter.getSssqZ());
            ps.setString(6, taxResourceMonitorTableParameter.getHydlDm());
            ps.setString(7, taxResourceMonitorTableParameter.getQyzclx());
            ps.setString(8, taxResourceMonitorTableParameter.getXmbz());
            ps.setString(9, taxResourceMonitorTableParameter.getDszsbz());

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxResourceMonitorTable = new TaxResourceMonitorTable();
            	
            	taxResourceMonitorTable.setXm(rs.getString("xm"));
            	taxResourceMonitorTable.setA0(rs.getInt("a0"));
            	taxResourceMonitorTable.setA1(rs.getInt("a1"));
            	taxResourceMonitorTable.setA2(rs.getInt("a2"));
            	taxResourceMonitorTable.setA3(rs.getInt("a3"));
            	taxResourceMonitorTable.setA4(rs.getInt("a4"));
            	taxResourceMonitorTable.setA5(rs.getInt("a5"));
            	taxResourceMonitorTable.setA6(rs.getInt("a6"));
            	taxResourceMonitorTable.setA7(rs.getInt("a7"));
            	taxResourceMonitorTable.setA8(rs.getInt("a8"));
            	
            	tableList.add(taxResourceMonitorTable);
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
	 * 企业基础信息查询表
	 * @param firmInfoSearchTableParameter
	 * @return
	 */
	public FirmInfoSearchTable getFirmInfoSearchTable(
			FirmInfoSearchTableParameter firmInfoSearchTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_03 ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        FirmInfoSearchTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, firmInfoSearchTableParameter.getNsrsbh());
            ps.setString(2, firmInfoSearchTableParameter.getNd());
            ps.setString(3, firmInfoSearchTableParameter.getSssqZ());

            rs = ps.executeQuery();

            if (rs.next()) {
            	table = new FirmInfoSearchTable();
            	
            	table.setA102(rs.getString("a102"));
            	table.setA106(rs.getString("a106"));
            	table.setA105(rs.getString("a105"));
            	table.setA108(rs.getString("a108"));
            	table.setA112(rs.getString("a112"));
            	table.setA114(rs.getString("a114"));
            	table.setA116(rs.getString("a116"));
            	table.setA118(rs.getString("a118"));
            	table.setA120(rs.getString("a120"));
            	table.setA107(rs.getString("a107"));
            	table.setA110(rs.getString("a110"));
            	table.setA109(rs.getString("a109"));
            	table.setA111(rs.getString("a111"));
            	table.setA113(rs.getString("a113"));
            	table.setA115(rs.getString("a115"));
            	table.setA117(rs.getString("a117"));
            	table.setA119(rs.getString("a119"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return table;
	}

	/**
	 * 企业经营信息表
	 * @param firmRunInfoTableParameter
	 * @return
	 */
	public FirmRunInfoTable getFirmRunInfoTable(
			FirmRunInfoTableParameter firmRunInfoTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_04 ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        FirmRunInfoTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, firmRunInfoTableParameter.getNsrsbh());
            ps.setString(2, firmRunInfoTableParameter.getNd());
            ps.setString(3, firmRunInfoTableParameter.getSssqZ());

            rs = ps.executeQuery();

            if (rs.next()) {
            	table = new FirmRunInfoTable();
            	
            	table.setA401(rs.getString("a401"));
            	table.setA402(rs.getString("a402"));
            	table.setA403(rs.getString("a403"));
            	table.setA404(rs.getString("a404"));
            	table.setA405(rs.getString("a405"));
            	table.setA406(rs.getString("a406"));
            	table.setA407(rs.getString("a407"));
            	table.setA408(rs.getString("a408"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return table;
	}

	/**
	 * 税源总量增减变动情况表
	 * @param taxResourceSumChangeTableParameter
	 * @return
	 */
	public List<TaxResourceSumChangeTable> getTaxResourceSumChangeTable(
			TaxResourceSumChangeTableParameter taxResourceSumChangeTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_09 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceSumChangeTable> tableList = new ArrayList<TaxResourceSumChangeTable>();
        TaxResourceSumChangeTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, taxResourceSumChangeTableParameter.getNd());
            ps.setString(2, taxResourceSumChangeTableParameter.getSssqQ());
            ps.setString(3, taxResourceSumChangeTableParameter.getSssqZ());
            ps.setString(4, taxResourceSumChangeTableParameter.getNsrsbh());
            ps.setString(5, taxResourceSumChangeTableParameter.getSwjgDm());
            ps.setString(6, taxResourceSumChangeTableParameter.getJedw());
            ps.setString(7, taxResourceSumChangeTableParameter.getQyzclx());
            ps.setString(8, taxResourceSumChangeTableParameter.getCy());
            ps.setString(9, taxResourceSumChangeTableParameter.getHydl());
            ps.setString(10, taxResourceSumChangeTableParameter.getXmbz());
            ps.setString(11, taxResourceSumChangeTableParameter.getDszsbz());

            rs = ps.executeQuery();

            while (rs.next()) {
            	table = new TaxResourceSumChangeTable();
            	
            	table.setA(rs.getString("a"));
            	table.setB1(rs.getBigDecimal("b1"));
            	table.setB2(rs.getBigDecimal("b2"));
            	table.setB3(rs.getBigDecimal("b3"));
            	table.setC1(rs.getBigDecimal("c1"));
            	table.setC2(rs.getBigDecimal("c2"));
            	table.setC3(rs.getBigDecimal("c3"));
            	table.setD1(rs.getBigDecimal("d1"));
            	table.setD2(rs.getBigDecimal("d2"));
            	table.setD3(rs.getBigDecimal("d3"));
            	table.setE1(rs.getBigDecimal("e1"));
            	table.setE2(rs.getBigDecimal("e2"));
            	table.setE3(rs.getBigDecimal("e3"));
            	table.setF1(rs.getBigDecimal("f1"));
            	table.setF2(rs.getBigDecimal("f2"));
            	table.setF3(rs.getBigDecimal("f3"));
            	
            	tableList.add(table);
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
	 * 税源质量变动情况表
	 * @param taxResourceQualityChangeTableParameter
	 * @return
	 */
	public List<TaxResourceQualityChangeTable> getTaxResourceQualityChangeTable(
			TaxResourceQualityChangeTableParameter taxResourceQualityChangeTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_10 ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxResourceQualityChangeTable> tableList = new ArrayList<TaxResourceQualityChangeTable>();
        TaxResourceQualityChangeTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, taxResourceQualityChangeTableParameter.getNd());
            ps.setString(2, taxResourceQualityChangeTableParameter.getSssqQ());
            ps.setString(3, taxResourceQualityChangeTableParameter.getSssqZ());
            ps.setString(4, taxResourceQualityChangeTableParameter.getNsrsbh());
            ps.setString(5, taxResourceQualityChangeTableParameter.getSwjgDm());
            ps.setString(6, taxResourceQualityChangeTableParameter.getQyzclx());
            ps.setString(7, taxResourceQualityChangeTableParameter.getCy());
            ps.setString(8, taxResourceQualityChangeTableParameter.getHydl());
            ps.setString(9, taxResourceQualityChangeTableParameter.getXmbz());

            rs = ps.executeQuery();

            while (rs.next()) {
            	table = new TaxResourceQualityChangeTable();
            	
            	table.setA(rs.getString("a"));
            	table.setB1(rs.getString("b1"));
            	table.setB2(rs.getString("b2"));
            	table.setB3(rs.getString("b3"));
            	table.setC1(rs.getString("c1"));
            	table.setC2(rs.getString("c2"));
            	table.setC3(rs.getString("c3"));
            	table.setD1(rs.getString("d1"));
            	table.setD2(rs.getString("d2"));
            	table.setD3(rs.getString("d3"));
            	table.setE1(rs.getString("e1"));
            	table.setE2(rs.getString("e2"));
            	table.setE3(rs.getString("e3"));
            	table.setF1(rs.getString("f1"));
            	table.setF2(rs.getString("f2"));
            	table.setF3(rs.getString("f3"));
            	
            	tableList.add(table);
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
	 * 利润表
	 * @param interestTableParameter
	 * @return
	 */
	public List<InterestTable> getInterestTable(
			InterestTableParameter interestTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_56 ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InterestTable> tableList = new ArrayList<InterestTable>();
        InterestTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, interestTableParameter.getNsrsbh());
            ps.setString(2, interestTableParameter.getNd());
            ps.setString(3, interestTableParameter.getSssqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	table = new InterestTable();
            	
            	table.setXh(rs.getInt("xh"));
            	table.setId(rs.getInt("id"));
            	table.setLr(rs.getString("lr").trim());
            	table.setLr1(rs.getString("lr1").trim());
            	table.setBq(rs.getString("bq").trim());
            	table.setLj(rs.getString("lj").trim());
            	table.setBz(rs.getInt("bz"));
            	
            	tableList.add(table);
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
	 * 获取新or旧
	 * @param interestTableParameter
	 * @return
	 */
	public String getInterestTableCwzbgs(
			InterestTableParameter interestTableParameter) {
		Connection conn = null;
        String sql = "select cwzbgs from gc_zhb where nsrsbh = ? and nd = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cwzbgs = "";

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, interestTableParameter.getNsrsbh());
            ps.setString(2, interestTableParameter.getNd());
            ps.setString(3, interestTableParameter.getSssqZ());

            rs = ps.executeQuery();

            if (rs.next()) {
            	cwzbgs = rs.getString("cwzbgs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return cwzbgs;
	}

	/**
	 * 获取新旧资产负债表
	 * @param assetLiabilityTableParameter
	 * @return
	 */
	public String getAssetLiabilityTableCwzbgs(
			AssetLiabilityTableParameter assetLiabilityTableParameter) {
		Connection conn = null;
        String sql = "select cwzbgs from gc_zhb where nsrsbh = ? and nd = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cwzbgs = "";

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, assetLiabilityTableParameter.getNsrsbh());
            ps.setString(2, assetLiabilityTableParameter.getNd());
            ps.setString(3, assetLiabilityTableParameter.getSssqZ());

            rs = ps.executeQuery();

            if (rs.next()) {
            	cwzbgs = rs.getString("cwzbgs");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return cwzbgs;
	}

	/**
	 * 资产负债表
	 * @param assetLiabilityTableParameter
	 * @return
	 */
	public List<AssetLiabilityTable> getAssetLiabilityTable(
			AssetLiabilityTableParameter assetLiabilityTableParameter) {
		Connection conn = null;
        String sql = "exec wcs_sycx_78 ?, ?, ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AssetLiabilityTable> tableList = new ArrayList<AssetLiabilityTable>();
        AssetLiabilityTable table = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, assetLiabilityTableParameter.getNsrsbh());
            ps.setString(2, assetLiabilityTableParameter.getNd());
            ps.setString(3, assetLiabilityTableParameter.getSssqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
            	table = new AssetLiabilityTable();
            	
            	table.setXh(rs.getInt("xh"));
            	table.setId(rs.getInt("id"));
            	table.setZc(rs.getString("zc"));
            	table.setZc1(rs.getString("zc1"));
            	table.setZcnc(rs.getString("zcnc"));
            	table.setZcqm(rs.getString("zcqm"));
            	table.setFz(rs.getString("fz"));
            	table.setFz1(rs.getString("fz1"));
            	table.setFznc(rs.getString("fznc"));
            	table.setFzqm(rs.getString("fzqm"));
            	table.setBz(rs.getInt("bz"));
            	
            	tableList.add(table);
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
