package com.tyut.sssy.task.dao;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.domain.AnalysisMacroIndex;
import com.tyut.sssy.utils.db.C3P0Util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisMacroIndexDao {

    /**
     * 根据指标类型明细和分析期获取总分量分析指标列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<AnalysisMacroIndex> getListByFxqAndFxzblxMx(
            DataCalcParameter dataCalcParameter, String fxzblxMx) {
        Connection conn = null;
        String sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisMacroIndex> list = new ArrayList<AnalysisMacroIndex>();

        AnalysisMacroIndex analysisMacroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, fxzblxMx);

            rs = ps.executeQuery();

            while (rs.next()) {
                analysisMacroIndex = new AnalysisMacroIndex();
                analysisMacroIndex.setNsrsbh(rs.getString("nsrsbh"));
                analysisMacroIndex.setNsrmc(rs.getString("nsrmc"));
                analysisMacroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
                analysisMacroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
                analysisMacroIndex.setZbqz(rs.getInt("zbqz"));
                analysisMacroIndex.setFxqNd(rs.getString("fxq_nd"));
                analysisMacroIndex.setJqNd(rs.getString("jq_nd"));
                analysisMacroIndex.setFxqSjq(rs.getString("fxq_sjq"));
                analysisMacroIndex.setFxqSjz(rs.getString("fxq_sjz"));
                analysisMacroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
                analysisMacroIndex.setJqsj(rs.getBigDecimal("jqsj"));
                analysisMacroIndex.setFxzbDm(rs.getString("fxzb_dm"));
                analysisMacroIndex.setFxq(rs.getString("fxq"));
                analysisMacroIndex.setFxzblx(rs.getString("fxzblx"));
                analysisMacroIndex.setFxzblxMx(rs.getString("fxzblx_mx"));
                analysisMacroIndex.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                analysisMacroIndex.setBdl(rs.getBigDecimal("bdl"));
                analysisMacroIndex.setTzqj(rs.getString("tzqj"));
                analysisMacroIndex.setTzfz(rs.getInt("tzfz"));

                list.add(analysisMacroIndex);
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
     * 根据分析期，分析指标类型明细，税务机关代码获得指标列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndSwjgDm(
            DataCalcParameter dataCalcParameter, String fxzblxMx, String swjgDm) {
        Connection conn = null;
        String sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ? and zg_swjg_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisMacroIndex> list = new ArrayList<AnalysisMacroIndex>();

        AnalysisMacroIndex analysisMacroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, fxzblxMx);
            ps.setString(5, swjgDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                analysisMacroIndex = new AnalysisMacroIndex();
                analysisMacroIndex.setNsrsbh(rs.getString("nsrsbh"));
                analysisMacroIndex.setNsrmc(rs.getString("nsrmc"));
                analysisMacroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
                analysisMacroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
                analysisMacroIndex.setZbqz(rs.getInt("zbqz"));
                analysisMacroIndex.setFxqNd(rs.getString("fxq_nd"));
                analysisMacroIndex.setJqNd(rs.getString("jq_nd"));
                analysisMacroIndex.setFxqSjq(rs.getString("fxq_sjq"));
                analysisMacroIndex.setFxqSjz(rs.getString("fxq_sjz"));
                analysisMacroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
                analysisMacroIndex.setJqsj(rs.getBigDecimal("jqsj"));
                analysisMacroIndex.setFxzbDm(rs.getString("fxzb_dm"));
                analysisMacroIndex.setFxq(rs.getString("fxq"));
                analysisMacroIndex.setFxzblx(rs.getString("fxzblx"));
                analysisMacroIndex.setFxzblxMx(rs.getString("fxzblx_mx"));
                analysisMacroIndex.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                analysisMacroIndex.setBdl(rs.getBigDecimal("bdl"));
                analysisMacroIndex.setTzqj(rs.getString("tzqj"));
                analysisMacroIndex.setTzfz(rs.getInt("tzfz"));

                list.add(analysisMacroIndex);
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
     * 根据分析期，分析指标类型明细，行业大类代码 获得指标列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndHydlDm(
            DataCalcParameter dataCalcParameter, String fxzblxMx, String hydlDm) {
        Connection conn = null;
        String sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ? and fxzblx_mx_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisMacroIndex> list = new ArrayList<AnalysisMacroIndex>();

        AnalysisMacroIndex analysisMacroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, fxzblxMx);
            ps.setString(5, hydlDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                analysisMacroIndex = new AnalysisMacroIndex();
                analysisMacroIndex.setNsrsbh(rs.getString("nsrsbh"));
                analysisMacroIndex.setNsrmc(rs.getString("nsrmc"));
                analysisMacroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
                analysisMacroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
                analysisMacroIndex.setZbqz(rs.getInt("zbqz"));
                analysisMacroIndex.setFxqNd(rs.getString("fxq_nd"));
                analysisMacroIndex.setJqNd(rs.getString("jq_nd"));
                analysisMacroIndex.setFxqSjq(rs.getString("fxq_sjq"));
                analysisMacroIndex.setFxqSjz(rs.getString("fxq_sjz"));
                analysisMacroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
                analysisMacroIndex.setJqsj(rs.getBigDecimal("jqsj"));
                analysisMacroIndex.setFxzbDm(rs.getString("fxzb_dm"));
                analysisMacroIndex.setFxq(rs.getString("fxq"));
                analysisMacroIndex.setFxzblx(rs.getString("fxzblx"));
                analysisMacroIndex.setFxzblxMx(rs.getString("fxzblx_mx"));
                analysisMacroIndex.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                analysisMacroIndex.setBdl(rs.getBigDecimal("bdl"));
                analysisMacroIndex.setTzqj(rs.getString("tzqj"));
                analysisMacroIndex.setTzfz(rs.getInt("tzfz"));

                list.add(analysisMacroIndex);
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
     * 根据分析期，分析指标类型明细，注册类型代码 获得指标列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndQyzclxDm(
            DataCalcParameter dataCalcParameter, String fxzblxMx,
            String qyzclxDm) {
        Connection conn = null;
        String sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ? and fxzblx_mx_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AnalysisMacroIndex> list = new ArrayList<AnalysisMacroIndex>();

        AnalysisMacroIndex analysisMacroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, fxzblxMx);
            ps.setString(5, qyzclxDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                analysisMacroIndex = new AnalysisMacroIndex();
                analysisMacroIndex.setNsrsbh(rs.getString("nsrsbh"));
                analysisMacroIndex.setNsrmc(rs.getString("nsrmc"));
                analysisMacroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
                analysisMacroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
                analysisMacroIndex.setZbqz(rs.getInt("zbqz"));
                analysisMacroIndex.setFxqNd(rs.getString("fxq_nd"));
                analysisMacroIndex.setJqNd(rs.getString("jq_nd"));
                analysisMacroIndex.setFxqSjq(rs.getString("fxq_sjq"));
                analysisMacroIndex.setFxqSjz(rs.getString("fxq_sjz"));
                analysisMacroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
                analysisMacroIndex.setJqsj(rs.getBigDecimal("jqsj"));
                analysisMacroIndex.setFxzbDm(rs.getString("fxzb_dm"));
                analysisMacroIndex.setFxq(rs.getString("fxq"));
                analysisMacroIndex.setFxzblx(rs.getString("fxzblx"));
                analysisMacroIndex.setFxzblxMx(rs.getString("fxzblx_mx"));
                analysisMacroIndex.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                analysisMacroIndex.setBdl(rs.getBigDecimal("bdl"));
                analysisMacroIndex.setTzqj(rs.getString("tzqj"));
                analysisMacroIndex.setTzfz(rs.getInt("tzfz"));

                list.add(analysisMacroIndex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    public AnalysisMacroIndex getForProcess(String fxqNd, String fxqSjq, String fxqSjz, String fxzbDm, String tzqj, String fxzblx, String fxzblxMxDm, String zgswjgdm) {
        Connection conn = null;
        String sql = "";
        String sqlTotal = "";
        if (fxzblxMxDm != null && !fxzblxMxDm.trim().equals("")) {
            sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx like ? and tzqj = ? and fxzb_dm = ? and fxzblx_mx_dm = ? and zg_swjg_dm = ?";
            sqlTotal = "select count(*) from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx like ? and tzqj = ? and fxzb_dm = ? and fxzblx_mx_dm = ? and zg_swjg_dm = ?";
        } else {
            sql = "select * from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx like ? and tzqj = ? and fxzb_dm = ?  and zg_swjg_dm = ? ";
            sqlTotal = "select count(*) from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx like ? and tzqj = ? and fxzb_dm = ?  and zg_swjg_dm = ? ";
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        AnalysisMacroIndex analysisMacroIndex = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sqlTotal);
            ps.setString(1, fxqNd.trim());
            ps.setString(2, fxqSjq.trim());
            ps.setString(3, fxqSjz.trim());
            ps.setString(4, "%" + fxzblx.trim() + "%");
            ps.setString(5, tzqj.trim());
            ps.setString(6, fxzbDm.trim());
            if (fxzblxMxDm != null && !fxzblxMxDm.trim().equals("")) {
                ps.setString(7, fxzblxMxDm.trim());
                ps.setString(8, zgswjgdm.trim());
            } else
                ps.setString(7, zgswjgdm.trim());
            rs = ps.executeQuery();
            int total = 0;
            while (rs.next())
                total = rs.getInt(1);
            if (total > 0) {
                ps = conn.prepareStatement(sql);
                ps.setString(1, fxqNd.trim());
                ps.setString(2, fxqSjq.trim());
                ps.setString(3, fxqSjz.trim());
                ps.setString(4, "%" + fxzblx.trim() + "%");
                ps.setString(5, tzqj.trim());
                ps.setString(6, fxzbDm.trim());
                if (fxzblxMxDm != null && !fxzblxMxDm.trim().equals("")) {
                    ps.setString(7, fxzblxMxDm.trim());
                    ps.setString(8, zgswjgdm.trim());
                } else
                    ps.setString(7, zgswjgdm.trim());

                rs = ps.executeQuery();

                while (rs.next()) {
                    analysisMacroIndex = new AnalysisMacroIndex();
                    analysisMacroIndex.setNsrsbh(rs.getString("nsrsbh"));
                    analysisMacroIndex.setNsrmc(rs.getString("nsrmc"));
                    analysisMacroIndex.setHsSwjgDm(rs.getString("hs_swjg_dm"));
                    analysisMacroIndex.setZgSwjgDm(rs.getString("zg_swjg_dm"));
                    analysisMacroIndex.setZbqz(rs.getInt("zbqz"));
                    analysisMacroIndex.setFxqNd(rs.getString("fxq_nd"));
                    analysisMacroIndex.setJqNd(rs.getString("jq_nd"));
                    analysisMacroIndex.setFxqSjq(rs.getString("fxq_sjq"));
                    analysisMacroIndex.setFxqSjz(rs.getString("fxq_sjz"));
                    analysisMacroIndex.setFxqsj(rs.getBigDecimal("fxqsj"));
                    analysisMacroIndex.setJqsj(rs.getBigDecimal("jqsj"));
                    analysisMacroIndex.setFxzbDm(rs.getString("fxzb_dm"));
                    analysisMacroIndex.setFxq(rs.getString("fxq"));
                    analysisMacroIndex.setFxzblx(rs.getString("fxzblx"));
                    analysisMacroIndex.setFxzblxMx(rs.getString("fxzblx_mx"));
                    analysisMacroIndex.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                    analysisMacroIndex.setBdl(rs.getBigDecimal("bdl"));
                    analysisMacroIndex.setTzqj(rs.getString("tzqj"));
                    analysisMacroIndex.setTzfz(rs.getInt("tzfz"));
                }
            } else {
                int jqNd = Integer.valueOf(fxqNd) -1;
                analysisMacroIndex = new AnalysisMacroIndex();
                analysisMacroIndex.setNsrsbh("");
                analysisMacroIndex.setNsrmc("");
                analysisMacroIndex.setHsSwjgDm("");
                analysisMacroIndex.setZgSwjgDm("");
                analysisMacroIndex.setZbqz(1);
                analysisMacroIndex.setFxqNd(fxqNd);
                analysisMacroIndex.setJqNd(String.valueOf(jqNd));
                analysisMacroIndex.setFxqSjq(fxqSjq);
                analysisMacroIndex.setFxqSjz(fxqSjz);
                analysisMacroIndex.setFxqsj(new BigDecimal(0));
                analysisMacroIndex.setJqsj(new BigDecimal(0));
                analysisMacroIndex.setFxzbDm("");
                analysisMacroIndex.setFxq(fxqNd);
                analysisMacroIndex.setFxzblx("");
                analysisMacroIndex.setFxzblxMx("");
                analysisMacroIndex.setFxzblxMxDm("");
                analysisMacroIndex.setBdl(new BigDecimal(0));
                analysisMacroIndex.setTzqj("");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return analysisMacroIndex;
    }
}
