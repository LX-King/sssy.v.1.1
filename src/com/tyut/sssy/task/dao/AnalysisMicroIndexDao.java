package com.tyut.sssy.task.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.domain.AnalysisMicroIndex;
import com.tyut.sssy.utils.db.C3P0Util;

public class AnalysisMicroIndexDao {

	/**
	 * 按纳税人识别号和分析期获取列表
	 * @param taxPayerCode
	 * @param dataCalcParameter
	 * @return
	 */
	public List<AnalysisMicroIndex> getListByTaxPayerCodeAndFxq(
			String taxPayerCode, DataCalcParameter dataCalcParameter) {
		Connection conn = null;
        String sql = "select * from fx_wgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and nsrsbh = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisMicroIndex> list = new ArrayList<AnalysisMicroIndex>();

        AnalysisMicroIndex analysisMicroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, taxPayerCode);

            rs = ps.executeQuery();

            while (rs.next()) {
            	analysisMicroIndex = new AnalysisMicroIndex();
            	analysisMicroIndex.setNsrsbh(rs.getString("nsrsbh"));
            	analysisMicroIndex.setNsrmc(rs.getString("nsrmc"));
            	analysisMicroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
            	analysisMicroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
            	analysisMicroIndex.setZbqz(rs.getInt("zbqz"));
            	analysisMicroIndex.setFxqNd(rs.getString("fxq_nd"));
            	analysisMicroIndex.setJqNd(rs.getString("jq_nd"));
            	analysisMicroIndex.setFxqSjq(rs.getString("fxq_sjq"));
            	analysisMicroIndex.setFxqSjz(rs.getString("fxq_sjz"));
            	analysisMicroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
            	analysisMicroIndex.setJqsj(rs.getBigDecimal("jqsj"));
            	analysisMicroIndex.setFxzbDm(rs.getString("fxzb_dm"));
            	analysisMicroIndex.setFxq(rs.getString("fxq"));
            	analysisMicroIndex.setFxzblx(rs.getString("fxzblx"));
            	analysisMicroIndex.setBdl(rs.getBigDecimal("bdl"));
            	analysisMicroIndex.setTzqj(rs.getString("tzqj"));
            	analysisMicroIndex.setTzfz(rs.getInt("tzfz"));
            	analysisMicroIndex.setCyDm(rs.getString("cy_dm"));
            	analysisMicroIndex.setHydlDm(rs.getString("hydl_dm"));
            	analysisMicroIndex.setQyzclxDm(rs.getString("qyzclx_dm"));
            	analysisMicroIndex.setFxqsj1(rs.getBigDecimal("fxqsj1"));
            	analysisMicroIndex.setJqsj1(rs.getBigDecimal("jqsj1"));
            	
                list.add(analysisMicroIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}

	/**
	 * 根据参数获得分析微观指标
	 * @param nsrsbh
	 * @param fxqNd
	 * @param fxqSjq
	 * @param fxqSjz
	 * @param fxzbDm
	 * @return
	 */
	public AnalysisMicroIndex getForProcess(String nsrsbh, String fxqNd,
			String fxqSjq, String fxqSjz, String fxzbDm) {
		Connection conn = null;
        String sql = "select * from fx_wgzb where nsrsbh = ? and fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzb_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        AnalysisMicroIndex analysisMicroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, nsrsbh);
            ps.setString(2, fxqNd);
            ps.setString(3, fxqSjq);
            ps.setString(4, fxqSjz);
            ps.setString(5, fxzbDm);

            rs = ps.executeQuery();

            if (rs.next()) {
            	analysisMicroIndex = new AnalysisMicroIndex();
            	analysisMicroIndex.setNsrsbh(rs.getString("nsrsbh"));
            	analysisMicroIndex.setNsrmc(rs.getString("nsrmc"));
            	analysisMicroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
            	analysisMicroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
            	analysisMicroIndex.setZbqz(rs.getInt("zbqz"));
            	analysisMicroIndex.setFxqNd(rs.getString("fxq_nd"));
            	analysisMicroIndex.setJqNd(rs.getString("jq_nd"));
            	analysisMicroIndex.setFxqSjq(rs.getString("fxq_sjq"));
            	analysisMicroIndex.setFxqSjz(rs.getString("fxq_sjz"));
            	analysisMicroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
            	analysisMicroIndex.setJqsj(rs.getBigDecimal("jqsj"));
            	analysisMicroIndex.setFxzbDm(rs.getString("fxzb_dm"));
            	analysisMicroIndex.setFxq(rs.getString("fxq"));
            	analysisMicroIndex.setFxzblx(rs.getString("fxzblx"));
            	analysisMicroIndex.setBdl(rs.getBigDecimal("bdl"));
            	analysisMicroIndex.setTzqj(rs.getString("tzqj"));
            	analysisMicroIndex.setTzfz(rs.getInt("tzfz"));
            	analysisMicroIndex.setCyDm(rs.getString("cy_dm"));
            	analysisMicroIndex.setHydlDm(rs.getString("hydl_dm"));
            	analysisMicroIndex.setQyzclxDm(rs.getString("qyzclx_dm"));
            	analysisMicroIndex.setFxqsj1(rs.getBigDecimal("fxqsj1"));
            	analysisMicroIndex.setJqsj1(rs.getBigDecimal("jqsj1"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return analysisMicroIndex;
	}

}
