package com.tyut.sssy.data.dao;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.data.domain.DataImportParameter;
import com.tyut.sssy.data.domain.DataImportQt;
import com.tyut.sssy.data.domain.DataImportZdsy;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sssy20120419
 * 类名称：SoftwareDataImport
 * 类描述：综合征管软件数据操作dao
 * 创建人：梁斌
 * 创建时间：2012-4-19 下午08:11:57
 * 修改人：梁斌
 * 修改时间：2012-4-19 下午08:11:57
 * 修改备注：
 */
public class DataDao {

    /**
     * 数据导入
     */
    public void dataImport(DataImportParameter dataImportParameter) {
        Connection conn = null;
        String sql = "exec dss_trans ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataImportParameter.getFxqNd());
            ps.setString(2, dataImportParameter.getFxqYf());
            ps.setString(3, dataImportParameter.getFxqSssqQ());
            ps.setString(4, dataImportParameter.getFxqSssqZ());
            ps.setString(5, dataImportParameter.getPd());
            ps.setString(6, dataImportParameter.getSxdmbz());
            ps.setString(7, dataImportParameter.getSxdjxx());
            ps.setString(8, dataImportParameter.getRkbz());
            ps.setString(9, dataImportParameter.getDjbz());
            ps.setString(10, dataImportParameter.getSjbz());
            ps.setString(11, dataImportParameter.getQjbz());

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 数据分析calc
     *
     * @param dataCalcParameter
     */
    public void dataCalc(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "exec dss_calc ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 数据分析2
     *
     * @param dataCalcParameter
     */
    public void dataCalc2(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "exec wcs_wg_zbjs ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 重点税源数据导入
     *
     * @param dataImportZdsy
     */
    public void zdsyDataImport(DataImportZdsy dataImportZdsy) {
        Connection conn = null;
        String sql = "exec dss_trans_zdsy ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataImportZdsy.getFxqNd());
            ps.setString(2, dataImportZdsy.getFxqSssqQ());
            ps.setString(3, dataImportZdsy.getFxqSssqZ());

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 综合征管软件数据导入
     *
     * @param dataImportQt
     */
    public void zrDataImport(DataImportQt dataImportQt) {
        Connection conn = null;
        String sql = "exec dss_trans_qt ?, ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataImportQt.getFxqNd());
            ps.setString(2, dataImportQt.getFxqYf());
            ps.setString(3, dataImportQt.getFxqSssqQ());
            ps.setString(4, dataImportQt.getFxqSssqZ());
            ps.setString(5, dataImportQt.getPd());
            ps.setString(6, dataImportQt.getRkbz());
            ps.setString(7, dataImportQt.getDjbz());
            ps.setString(8, dataImportQt.getSjbz());
            ps.setString(9, dataImportQt.getQjbz());

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 代码刷新
     */
    public void refreshCode() {
        Connection conn = null;
        String sql = "exec dss_trans_dm";
        PreparedStatement ps = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.execute();
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 从fx_wgzb中选出不同的纳税人识别号
     *
     * @param dataCalcParameter
     * @return
     */
    public List<String> getTaxPayerCodeListFromFxWgzb(
            DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "select distinct nsrsbh from fx_wgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String taxPayerCode = "";
        List<String> taxPayerCodeList = new ArrayList<String>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            rs = ps.executeQuery();

            while (rs.next()) {
                taxPayerCode = rs.getString("nsrsbh").trim();
                taxPayerCodeList.add(taxPayerCode);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

        return taxPayerCodeList;
    }

    /**
     * 按分析期删除任务记录
     *
     * @param dataCalcParameter
     */
    public void deleteTaskDataByDate(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "delete from wgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ?";
        String sql2 = "delete from hgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ?";

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, dataCalcParameter.getFxqNd());
            ps2.setString(2, dataCalcParameter.getFxqSssqQ());
            ps2.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
            ps2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 从fx_hgzb中得到管理机关代码列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<String> getSwjgDmListFromFxHgzb(
            DataCalcParameter dataCalcParameter, String fxzblxMx) {
        Connection conn = null;
        String sql = "select distinct zg_swjg_dm from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String swjgDm = "";
        List<String> swjgDmList = new ArrayList<String>();

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
                swjgDm = rs.getString("zg_swjg_dm").trim();
                swjgDmList.add(swjgDm);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

        return swjgDmList;
    }

    /**
     * 从fx_hgzb中得到行业代码列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<String> getHydlDmListFromFxHgzb(
            DataCalcParameter dataCalcParameter, String fxzblxMx) {
        Connection conn = null;
        String sql = "select distinct fxzblx_mx_dm from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String hydlDm = "";
        List<String> hydlDmList = new ArrayList<String>();

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
                hydlDm = rs.getString("fxzblx_mx_dm").trim();
                hydlDmList.add(hydlDm);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

        return hydlDmList;
    }

    /**
     * 从fx_hgzb中得到注册类型代码列表
     *
     * @param dataCalcParameter
     * @param fxzblxMx
     * @return
     */
    public List<String> getQyzclxDmListFromFxHgzb(
            DataCalcParameter dataCalcParameter, String fxzblxMx) {
        Connection conn = null;
        String sql = "select distinct fxzblx_mx_dm from fx_hgzb where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzblx_mx = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        String qyzclxDm = "";
        List<String> qyzclxDmList = new ArrayList<String>();

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
                qyzclxDm = rs.getString("fxzblx_mx_dm").trim();
                qyzclxDmList.add(qyzclxDm);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

        return qyzclxDmList;
    }

    /**
     * 风险指数微观数据导入
     */
    public boolean riskTaskImport(DataCalcParameter dataCalcParameter, String isOverrided) {
        Connection conn = null;
        String sql = "exec lx_fxzs_wgrw_import ?, ?, ?, ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());
            ps.setString(4, isOverrided);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

}
