package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.utils.db.C3P0Util;

public class TaxPayerDao {

	/**
	 * 根据税务机关获取纳税人列表
	 * @param swjgDm
	 * @return
	 */
	public List<TaxPayer> getTaxPayerListByUnit(String swjgDm) {
		Connection conn = null;
        String sql = "select * from jc_jbxxb_zdqy where swjg_dm = ? and nd = ? and sssq_q = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
       
        String maxPeriod = getMaxPeriod();

        TaxPayer taxPayer = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, swjgDm);
            ps.setString(2, maxPeriod.substring(0, 4));
            ps.setString(3, maxPeriod.substring(4, 6));
            ps.setString(4, maxPeriod.substring(6, 8));

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxPayer = new TaxPayer();
            	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
            	taxPayer.setNsrmc(rs.getString("nsrmc"));
            	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
            	taxPayer.setNd(rs.getString("nd"));
            	taxPayer.setSssqQ(rs.getString("sssq_q"));
            	taxPayer.setSssqZ(rs.getString("sssq_z"));
            	
            	taxPayerList.add(taxPayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxPayerList;
	}
	
	/**
	 * 查询jc_jbxxb_zdsy中nd+sssq_q+sssq_z的最大数值
	 * @return
	 */
	private String getMaxPeriod() {
		Connection conn = null;
        String sql = "select max(nd + sssq_q + sssq_z) as max_period from jc_jbxxb_zdqy";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxPeriod = "";

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {
            	maxPeriod = rs.getString("max_period");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return maxPeriod;
	}

	/**
	 * 根据纳税人编号查询纳税人
	 * @param swjgDm
	 * @return
	 */
	public TaxPayer getTaxPayerByCode(String nsrsbh) {
		Connection conn = null;
        String sql = "select * from jc_jbxxb_zdqy where nsrsbh = ?";
        String sql2 = "select * from jc_jbxxb_qtqy where nsrsbh = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        TaxPayer taxPayer = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nsrsbh);

            rs = ps.executeQuery();

            if (rs.next()) {
            	taxPayer = new TaxPayer();
            	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
            	taxPayer.setNsrmc(rs.getString("nsrmc"));
            	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
            	taxPayer.setNd(rs.getString("nd"));
            	taxPayer.setSssqQ(rs.getString("sssq_q"));
            	taxPayer.setSssqZ(rs.getString("sssq_z"));
            } else {
            	ps = conn.prepareStatement(sql2);
                ps.setString(1, nsrsbh);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                	taxPayer = new TaxPayer();
                	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
                	taxPayer.setNsrmc(rs.getString("nsrmc"));
                	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
                	taxPayer.setNd(rs.getString("nd"));
                	taxPayer.setSssqQ(rs.getString("sssq_q"));
                	taxPayer.setSssqZ(rs.getString("sssq_z"));
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxPayer;
	}

	public List<TaxPayer> getTaxPayerList() {
		Connection conn = null;
        String sql = "select * from jc_jbxxb_zdqy where nd = ? and sssq_q = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
       
        String maxPeriod = getMaxPeriod();

        TaxPayer taxPayer = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maxPeriod.substring(0, 4));
            ps.setString(2, maxPeriod.substring(4, 6));
            ps.setString(3, maxPeriod.substring(6, 8));

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxPayer = new TaxPayer();
            	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
            	taxPayer.setNsrmc(rs.getString("nsrmc"));
            	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
            	taxPayer.setNd(rs.getString("nd"));
            	taxPayer.setSssqQ(rs.getString("sssq_q"));
            	taxPayer.setSssqZ(rs.getString("sssq_z"));
            	
            	taxPayerList.add(taxPayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxPayerList;
	}
	
	/**
	 * 根据税务机关获取纳税人列表（待解税金 另一部分纳税人）
	 * @param swjgDm
	 * @return
	 */
	public List<TaxPayer> getDjTaxPayerListByUnit(String swjgDm) {
		Connection conn = null;
        String sql = "select * from jc_jbxxb_qtqy where swjg_dm = ? and nd = ? and sssq_q = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
       
        String maxPeriod = getDjMaxPeriod();

        TaxPayer taxPayer = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, swjgDm);
            ps.setString(2, maxPeriod.substring(0, 4));
            ps.setString(3, maxPeriod.substring(4, 6));
            ps.setString(4, maxPeriod.substring(6, 8));

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxPayer = new TaxPayer();
            	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
            	taxPayer.setNsrmc(rs.getString("nsrmc"));
            	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
            	taxPayer.setNd(rs.getString("nd"));
            	taxPayer.setSssqQ(rs.getString("sssq_q"));
            	taxPayer.setSssqZ(rs.getString("sssq_z"));
            	
            	taxPayerList.add(taxPayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxPayerList;
	}
	
	/**
	 * 查询jc_jbxxb_zdsy中nd+sssq_q+sssq_z的最大数值（另一部分内容）
	 * @return
	 */
	private String getDjMaxPeriod() {
		Connection conn = null;
        String sql = "select max(nd + sssq_q + sssq_z) as max_period from jc_jbxxb_qtqy";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxPeriod = "";

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next()) {
            	maxPeriod = rs.getString("max_period");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return maxPeriod;
	}
	
	/**
	 * 获取所有纳税人（待解 另一部分内容）
	 * @return
	 */
	public List<TaxPayer> getDjTaxPayerList() {
		Connection conn = null;
        String sql = "select * from jc_jbxxb_qtqy where nd = ? and sssq_q = ? and sssq_z = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
       
        String maxPeriod = getDjMaxPeriod();

        TaxPayer taxPayer = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, maxPeriod.substring(0, 4));
            ps.setString(2, maxPeriod.substring(4, 6));
            ps.setString(3, maxPeriod.substring(6, 8));

            rs = ps.executeQuery();

            while (rs.next()) {
            	taxPayer = new TaxPayer();
            	taxPayer.setNsrsbh(rs.getString("nsrsbh"));
            	taxPayer.setNsrmc(rs.getString("nsrmc"));
            	taxPayer.setSwjgDm(rs.getString("swjg_dm"));
            	taxPayer.setNd(rs.getString("nd"));
            	taxPayer.setSssqQ(rs.getString("sssq_q"));
            	taxPayer.setSssqZ(rs.getString("sssq_z"));
            	
            	taxPayerList.add(taxPayer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return taxPayerList;
	}

}
