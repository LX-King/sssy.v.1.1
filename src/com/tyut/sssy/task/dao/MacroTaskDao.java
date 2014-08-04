package com.tyut.sssy.task.dao;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MacroTaskExtractParameter;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MacroTaskDao {

    public void deleteByFxq(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "delete from hgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ?";
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 插入总/分量任务
     *
     * @param macroTask
     */
    public void add(MacroTask macroTask) {
        Connection conn = null;
        String sql = "insert into hgrw values (?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTask.getNsrsbh());
            ps.setString(2, macroTask.getSwjgDm());
            ps.setString(3, macroTask.getFxqNd());
            ps.setString(4, macroTask.getFxqSjq());
            ps.setString(5, macroTask.getFxqSjz());

            ps.setString(6, macroTask.getFxzbDm());
            ps.setString(7, macroTask.getFxzblxMx());
            ps.setString(8, macroTask.getFxzblxMxDm());
            ps.setString(9, macroTask.getFlMc());
            ps.setString(10, macroTask.getTzqj());

            ps.setBigDecimal(11, macroTask.getBdl());
            ps.setInt(12, macroTask.getTzfz());
            ps.setBigDecimal(13, macroTask.getZfz());
            ps.setString(14, macroTask.getRwztDm());
            ps.setString(15, macroTask.getRwtqryDm());

            ps.setString(16, macroTask.getRwtqRq());
            ps.setString(17, macroTask.getRwfbryDm());
            ps.setString(18, macroTask.getRwfbRq());
            ps.setString(19, macroTask.getRwzxryDm());
            ps.setString(20, macroTask.getBzjsRq());

            ps.setString(21, macroTask.getSjjsRq());
            ps.setString(22, macroTask.getBzfkRq());
            ps.setString(23, macroTask.getSjfkRq());
            ps.setString(24, macroTask.getRwbgDm());
            ps.setString(25, macroTask.getGlpj());

            ps.setString(26, macroTask.getZfpj());
            ps.setString(27, macroTask.getZhpj());
            ps.setString(28, macroTask.getPjryDm());
            ps.setString(29, macroTask.getRwpjRq());

            ps.setInt(30, macroTask.getRwjsDf());
            ps.setInt(31, macroTask.getRwfkDf());
            ps.setInt(32, macroTask.getRwzxDf());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, null, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

    }

    /**
     * 在任务提取模块 得到任务列表
     *
     * @param macroTaskExtractParameter
     * @return
     */
    public List<MacroTask> getMacroTaskListInExtractSection(
            MacroTaskExtractParameter macroTaskExtractParameter) {
        Connection conn = null;
        String sql = "";

        if (!macroTaskExtractParameter.getType().equals("none")) {
            // zfz 排序
            if (macroTaskExtractParameter.getType().equals("zfz")) {
                if (macroTaskExtractParameter.getOrder().equals("asc")) {
                    // 升序
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and swjg_dm like ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz asc";
                } else if (macroTaskExtractParameter.getOrder().equals("desc")) {
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and swjg_dm like ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz desc";
                }
            }
        } else {
            // 不排序
            sql = "select * from hgrw where fxq_nd = ?" +
                    " and fxq_sjq = ?" +
                    " and fxq_sjz = ?" +
                    " and swjg_dm like ?" +
                    " and fxzblx_mx_dm like ?" +
                    " and fxzb_dm like ?" +
                    " and rwzt_dm like ?";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTaskExtractParameter.getFxqNd());
            ps.setString(2, macroTaskExtractParameter.getFxqSjq());
            ps.setString(3, macroTaskExtractParameter.getFxqSjz());
            ps.setString(4, "%" + macroTaskExtractParameter.getSwjgDm().trim() + "%");
            ps.setString(5, "%" + macroTaskExtractParameter.getHydlDm().trim() + "%");
//            ps.setString(6, "%" +macroTaskExtractParameter.getQyzclxDm().trim() + "%");
            ps.setString(6, "%" + macroTaskExtractParameter.getFxzbDm().trim() + "%");
            ps.setString(7, "%" + macroTaskExtractParameter.getRwztDm().trim() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 按 管理机关 分类查找提取列表
     *
     * @param macroTaskExtractParameter
     * @return
     */
    public List<MacroTask> getMacroTaskListBySwjgInExtractSection(
            MacroTaskExtractParameter macroTaskExtractParameter) {
        Connection conn = null;
        String sql = "";

        if (!macroTaskExtractParameter.getType().equals("none")) {
            // zfz 排序
            if (macroTaskExtractParameter.getType().equals("zfz")) {
                if (macroTaskExtractParameter.getOrder().equals("asc")) {
                    // 升序
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and swjg_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz asc";
                } else if (macroTaskExtractParameter.getOrder().equals("desc")) {
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and swjg_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz desc";
                }
            }
        } else {
            // 不排序
            sql = "select * from hgrw where fxq_nd = ?" +
                    " and fxq_sjq = ?" +
                    " and fxq_sjz = ?" +
                    " and fxzblx_mx = ?" +
                    " and swjg_dm like ?" +
                    " and fxzb_dm like ?" +
                    " and rwzt_dm like ?";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTaskExtractParameter.getFxqNd());
            ps.setString(2, macroTaskExtractParameter.getFxqSjq());
            ps.setString(3, macroTaskExtractParameter.getFxqSjz());
            ps.setString(4, macroTaskExtractParameter.getZflLx());
            ps.setString(5, "%" + macroTaskExtractParameter.getSwjgDm().trim() + "%");
            ps.setString(6, "%" + macroTaskExtractParameter.getFxzbDm().trim() + "%");
            ps.setString(7, "%" + macroTaskExtractParameter.getRwztDm().trim() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 按 行业 分类查找提取列表
     *
     * @param macroTaskExtractParameter
     * @return
     */
    public List<MacroTask> getMacroTaskListByHyInExtractSection(
            MacroTaskExtractParameter macroTaskExtractParameter) {
        Connection conn = null;
        String sql = "";

        if (!macroTaskExtractParameter.getType().equals("none")) {
            // zfz 排序
            if (macroTaskExtractParameter.getType().equals("zfz")) {
                if (macroTaskExtractParameter.getOrder().equals("asc")) {
                    // 升序
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz asc";
                } else if (macroTaskExtractParameter.getOrder().equals("desc")) {
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz desc";
                }
            }
        } else {
            // 不排序
            sql = "select * from hgrw where fxq_nd = ?" +
                    " and fxq_sjq = ?" +
                    " and fxq_sjz = ?" +
                    " and fxzblx_mx = ?" +
                    " and fxzblx_mx_dm like ?" +
                    " and fxzb_dm like ?" +
                    " and rwzt_dm like ?";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTaskExtractParameter.getFxqNd());
            ps.setString(2, macroTaskExtractParameter.getFxqSjq());
            ps.setString(3, macroTaskExtractParameter.getFxqSjz());
            ps.setString(4, macroTaskExtractParameter.getZflLx());
            ps.setString(5, "%" + macroTaskExtractParameter.getHydlDm().trim() + "%");
            ps.setString(6, "%" + macroTaskExtractParameter.getFxzbDm().trim() + "%");
            ps.setString(7, "%" + macroTaskExtractParameter.getRwztDm().trim() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 按 注册类型 分类查找提取列表
     *
     * @param macroTaskExtractParameter
     * @return
     */
    public List<MacroTask> getMacroTaskListByZclxInExtractSection(
            MacroTaskExtractParameter macroTaskExtractParameter) {
        Connection conn = null;
        String sql = "";

        if (!macroTaskExtractParameter.getType().equals("none")) {
            // zfz 排序
            if (macroTaskExtractParameter.getType().equals("zfz")) {
                if (macroTaskExtractParameter.getOrder().equals("asc")) {
                    // 升序
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz asc";
                } else if (macroTaskExtractParameter.getOrder().equals("desc")) {
                    sql = "select * from hgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and fxzblx_mx = ?" +
                            " and fxzblx_mx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz desc";
                }
            }
        } else {
            // 不排序
            sql = "select * from hgrw where fxq_nd = ?" +
                    " and fxq_sjq = ?" +
                    " and fxq_sjz = ?" +
                    " and fxzblx_mx = ?" +
                    " and fxzblx_mx_dm like ?" +
                    " and fxzb_dm like ?" +
                    " and rwzt_dm like ?";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTaskExtractParameter.getFxqNd());
            ps.setString(2, macroTaskExtractParameter.getFxqSjq());
            ps.setString(3, macroTaskExtractParameter.getFxqSjz());
            ps.setString(4, macroTaskExtractParameter.getZflLx());
            ps.setString(5, "%" + macroTaskExtractParameter.getQyzclxDm().trim() + "%");
            ps.setString(6, "%" + macroTaskExtractParameter.getFxzbDm().trim() + "%");
            ps.setString(7, "%" + macroTaskExtractParameter.getRwztDm().trim() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据id获得总/分量任务
     *
     * @param id
     * @return
     */
    public MacroTask getMacroTaskById(int id) {
        Connection conn = null;
        String sql = "select * from hgrw where id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));
                macroTask.setBdl(rs.getBigDecimal("bdl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return macroTask;
    }

    /**
     * 更新总/分量任务
     *
     * @param macroTask
     */
    public void update(MacroTask macroTask) {
        Connection conn = null;
        String sql = "update hgrw set " +
                "nsrsbh = ?, swjg_dm = ?, fxq_nd = ?, fxq_sjq = ?," +
                "fxq_sjz = ?, " +
                "fxzb_dm = ?, fxzblx_mx = ?, fxzblx_mx_dm = ?, fl_mc = ?, tzqj = ?, bdl = ?, tzfz = ?, zfz = ?," +
                "rwzt_dm = ?, rwtqry_dm = ?, rwtq_rq = ?, rwfbry_dm = ?," +
                "rwfb_rq = ?, rwzxry_dm = ?, bzjs_rq = ?, sjjs_rq = ?," +
                "bzfk_rq = ?, sjfk_rq = ?, rwbg_dm = ?, glpj = ?," +
                "zfpj = ?, zhpj = ?, pjry_dm = ?, rwpj_rq = ?, rwjs_df = ?, rwfk_df = ?, rwzx_df = ? " +
                "where id = ?";
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, macroTask.getNsrsbh());
            ps.setString(2, macroTask.getSwjgDm());
            ps.setString(3, macroTask.getFxqNd());
            ps.setString(4, macroTask.getFxqSjq());
            ps.setString(5, macroTask.getFxqSjz());

            ps.setString(6, macroTask.getFxzbDm());
            ps.setString(7, macroTask.getFxzblxMx());
            ps.setString(8, macroTask.getFxzblxMxDm());
            ps.setString(9, macroTask.getFlMc());
            ps.setString(10, macroTask.getTzqj());
            ps.setBigDecimal(11, macroTask.getBdl());
            ps.setInt(12, macroTask.getTzfz());
            ps.setBigDecimal(13, macroTask.getZfz());
            ps.setString(14, macroTask.getRwztDm());

            ps.setString(15, macroTask.getRwtqryDm());
            ps.setString(16, macroTask.getRwtqRq());
            ps.setString(17, macroTask.getRwfbryDm());
            ps.setString(18, macroTask.getRwfbRq());
            ps.setString(19, macroTask.getRwzxryDm());
            ps.setString(20, macroTask.getBzjsRq());

            ps.setString(21, macroTask.getSjjsRq());
            ps.setString(22, macroTask.getBzfkRq());
            ps.setString(23, macroTask.getSjfkRq());
            ps.setString(24, macroTask.getRwbgDm());
            ps.setString(25, macroTask.getGlpj());
            ps.setString(26, macroTask.getZfpj());
            ps.setString(27, macroTask.getZhpj());
            ps.setString(28, macroTask.getPjryDm());
            ps.setString(29, macroTask.getRwpjRq());

            ps.setInt(30, macroTask.getRwjsDf());
            ps.setInt(31, macroTask.getRwfkDf());
            ps.setInt(32, macroTask.getRwzxDf());

            ps.setInt(33, macroTask.getId());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 根据执行人员代码 和 任务状态代码 查询任务列表
     *
     * @param rwzxryDm
     * @param rwzrDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByZxryAndRwzt(String rwzxryDm,
                                                         String rwzrDm) {
        Connection conn = null;
        String sql = "select * from hgrw where rwzxry_dm = ? and rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwzxryDm);
            ps.setString(2, rwzrDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据任务状态获得任务列表
     *
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByRwzt(String rwztDm) {
        Connection conn = null;
        String sql = "select * from hgrw where rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwztDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据任务发布人员 和 任务状态获取任务列表
     *
     * @param rwfbryDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByFbryAndRwzt(String rwfbryDm,
                                                         String rwztDm) {
        Connection conn = null;
        String sql = "select * from hgrw where rwfbry_dm = ? and rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwfbryDm);
            ps.setString(2, rwztDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据发布人员 和 任务状态获得任务列表
     *
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
                                                             String sjZ, String rwztDm, String rwzxryDm) {
        Connection conn = null;
        String sql = "select * from hgrw where sjfk_rq >= ? and sjfk_rq <= ? and rwzt_dm = ? and rwzxry_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, sjQ);
            ps.setString(2, sjZ);
            ps.setString(3, rwztDm);
            ps.setString(4, rwzxryDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据税务机关和任务状态获取任务列表
     *
     * @param swjgDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListBySwjgAndRwzt(String fxzblxMx, String swjgDm,
                                                         String rwztDm) {
        Connection conn = null;
        String sql = "select * from hgrw where fxzblx_mx = ? and swjg_dm like ? and rwzt_dm like ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, fxzblxMx);
            ps.setString(2, "%" + swjgDm + "%");
            ps.setString(3, "%" + rwztDm + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据行业和任务状态获取任务列表
     *
     * @param hydlDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByHyAndRwzt(String fxzblxMx, String hydlDm,
                                                       String rwztDm) {
        Connection conn = null;
        String sql = "select * from hgrw where fxzblx_mx = ? and fxzblx_mx_dm like ? and rwzt_dm like ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, fxzblxMx);
            ps.setString(2, "%" + hydlDm + "%");
            ps.setString(3, "%" + rwztDm + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
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
     * 根据企业注册类型和任务状态获取任务列表
     *
     * @param qyzclxDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByZclxAndRwzt(String fxzblxMx, String qyzclxDm,
                                                         String rwztDm) {
        Connection conn = null;
        String sql = "select * from hgrw where fxzblx_mx = ? and fxzblx_mx_dm like ? and rwzt_dm like ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MacroTask> list = new ArrayList<MacroTask>();
        MacroTask macroTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, fxzblxMx);
            ps.setString(2, "%" + qyzclxDm + "%");
            ps.setString(3, "%" + rwztDm + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                macroTask = new MacroTask();
                macroTask.setId(rs.getInt("id"));
                macroTask.setNsrsbh(rs.getString("nsrsbh"));
                macroTask.setSwjgDm(rs.getString("swjg_dm"));
                macroTask.setFxqNd(rs.getString("fxq_nd"));
                macroTask.setFxqSjq(rs.getString("fxq_sjq"));
                macroTask.setFxqSjz(rs.getString("fxq_sjz"));
                macroTask.setFxzblxMx(rs.getString("fxzblx_mx"));
                macroTask.setFxzblxMxDm(rs.getString("fxzblx_mx_dm"));
                macroTask.setFxzbDm(rs.getString("fxzb_dm"));
                macroTask.setFlMc(rs.getString("fl_mc"));
                macroTask.setTzqj(rs.getString("tzqj"));
                macroTask.setTzfz(rs.getInt("tzfz"));
                macroTask.setZfz(rs.getBigDecimal("zfz"));
                macroTask.setRwztDm(rs.getString("rwzt_dm"));
                macroTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                macroTask.setRwtqRq(rs.getString("rwtq_rq"));
                macroTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                macroTask.setRwfbRq(rs.getString("rwfb_rq"));
                macroTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                macroTask.setBzjsRq(rs.getString("bzjs_rq"));
                macroTask.setSjjsRq(rs.getString("sjjs_rq"));
                macroTask.setBzfkRq(rs.getString("bzfk_rq"));
                macroTask.setSjfkRq(rs.getString("sjfk_rq"));
                macroTask.setRwbgDm(rs.getString("rwbg_dm"));
                macroTask.setGlpj(rs.getString("glpj"));
                macroTask.setZfpj(rs.getString("zfpj"));
                macroTask.setZhpj(rs.getString("zhpj"));
                macroTask.setPjryDm(rs.getString("pjry_dm"));
                macroTask.setRwpjRq(rs.getString("rwpj_rq"));
                macroTask.setRwjsDf(rs.getInt("rwjs_df"));
                macroTask.setRwfkDf(rs.getInt("rwfk_df"));
                macroTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(macroTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "delete from hgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and rwzt_dm = '00' ";
        PreparedStatement ps = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dataCalcParameter.getFxqNd());
            ps.setString(2, dataCalcParameter.getFxqSssqQ());
            ps.setString(3, dataCalcParameter.getFxqSssqZ());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    public boolean isExist(MacroTask macroTask) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = true;

        String sql = " select count(*) from hgrw where " +
                " swjg_dm = ? and fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and fxzb_dm = ? and fxzblx_mx = ? and fxzblx_mx_dm like ? and " +
                " fl_mc = ? and  tzqj = ? and  tzfz = ?  and rwzt_dm <> '00' ";

        try {
            String fxzblxMxDm = "";
            if(macroTask.getFxzblxMxDm() != null && !macroTask.getFxzblxMxDm().trim().equals(""))
                fxzblxMxDm = "%"+macroTask.getFxzblxMxDm().trim()+"%";
            else
                fxzblxMxDm = "%";
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, macroTask.getSwjgDm().trim());
            ps.setString(2, macroTask.getFxqNd().trim());
            ps.setString(3, macroTask.getFxqSjq().trim());
            ps.setString(4, macroTask.getFxqSjz().trim());
            ps.setString(5, macroTask.getFxzbDm().trim());
            ps.setString(6, macroTask.getFxzblxMx().trim());
            ps.setString(7, fxzblxMxDm);
            ps.setString(8, macroTask.getFlMc().trim());
            ps.setString(9, macroTask.getTzqj().trim());
            ps.setInt(10, macroTask.getTzfz());


            rs = ps.executeQuery();
            int total = 0;
            while(rs.next())
                 total = rs.getInt(1);

            if (total != 0)
                flag = true;
            else
                flag = false;


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        return flag;
    }
}
