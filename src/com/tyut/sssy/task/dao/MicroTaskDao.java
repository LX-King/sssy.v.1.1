package com.tyut.sssy.task.dao;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.domain.MicroTaskExtractParameter;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MicroTaskDao {

    /**
     * 获取提取任务列表
     *
     * @param microTaskExtractParameter
     * @return
     */
    public List<MicroTask> getExtractMicroTaskList(
            MicroTaskExtractParameter microTaskExtractParameter) {

        Connection conn = null;
        String sql = "";

        if (!microTaskExtractParameter.getType().equals("none")) {
            // zfz 排序
            if (microTaskExtractParameter.getType().equals("zfz")) {
                if (microTaskExtractParameter.getOrder().equals("asc")) {
                    // 升序
                    sql = "select * from wgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and swjg_dm like ?" +
                            " and nsrsbh like ?" +
                            " and hydl_dm like ?" +
                            " and qyzclx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz asc";
                } else if (microTaskExtractParameter.getOrder().equals("desc")) {
                    sql = "select * from wgrw where fxq_nd = ?" +
                            " and fxq_sjq = ?" +
                            " and fxq_sjz = ?" +
                            " and swjg_dm like ?" +
                            " and nsrsbh like ?" +
                            " and hydl_dm like ?" +
                            " and qyzclx_dm like ?" +
                            " and fxzb_dm like ?" +
                            " and rwzt_dm like ? order by zfz desc";
                }
            }
        } else {
            // 不排序
            sql = "select * from wgrw where fxq_nd = ?" +
                    " and fxq_sjq = ?" +
                    " and fxq_sjz = ?" +
                    " and swjg_dm like ?" +
                    " and nsrsbh like ?" +
                    " and hydl_dm like ?" +
                    " and qyzclx_dm like ?" +
                    " and fxzb_dm like ?" +
                    " and rwzt_dm like ?";
        }

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, microTaskExtractParameter.getFxqNd());
            ps.setString(2, microTaskExtractParameter.getFxqSjq());
            ps.setString(3, microTaskExtractParameter.getFxqSjz());
            ps.setString(4, "%" + microTaskExtractParameter.getSwjgDm().trim() + "%");
            ps.setString(5, "%" + microTaskExtractParameter.getNsrsbh().trim() + "%");
            ps.setString(6, "%" + microTaskExtractParameter.getHydlDm().trim() + "%");
            ps.setString(7, "%" + microTaskExtractParameter.getQyzclxDm().trim() + "%");
            ps.setString(8, "%" + microTaskExtractParameter.getFxzbDm().trim() + "%");
            ps.setString(9, "%" + microTaskExtractParameter.getRwztDm().trim() + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    public void add(MicroTask microTask) {
        Connection conn = null;
        String sql = "insert into wgrw values (?, ?, ?, ?, ?, ?, " +
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

            ps.setString(1, microTask.getNsrsbh());
            ps.setString(2, microTask.getSwjgDm());
            ps.setString(3, microTask.getFxqNd());
            ps.setString(4, microTask.getFxqSjq());
            ps.setString(5, microTask.getFxqSjz());
            ps.setString(6, microTask.getCyDm());

            ps.setString(7, microTask.getHydlDm());
            ps.setString(8, microTask.getQyzclxDm());
            ps.setString(9, microTask.getFxzbDm());
            ps.setString(10, microTask.getTzqj());
            ps.setBigDecimal(11, microTask.getBdl());
            ps.setInt(12, microTask.getTzfz());
            ps.setBigDecimal(13, microTask.getZfz());
            ps.setString(14, microTask.getRwztDm());

            ps.setString(15, microTask.getRwtqryDm());
            ps.setString(16, microTask.getRwtqRq());
            ps.setString(17, microTask.getRwfbryDm());
            ps.setString(18, microTask.getRwfbRq());
            ps.setString(19, microTask.getRwzxryDm());
            ps.setString(20, microTask.getBzjsRq());

            ps.setString(21, microTask.getSjjsRq());
            ps.setString(22, microTask.getBzfkRq());
            ps.setString(23, microTask.getSjfkRq());
            ps.setString(24, microTask.getRwbgDm());
            ps.setString(25, microTask.getGlpj());

            ps.setString(26, microTask.getZfpj());
            ps.setString(27, microTask.getZhpj());
            ps.setString(28, microTask.getPjryDm());
            ps.setString(29, microTask.getRwpjRq());

            ps.setInt(30, microTask.getRwjsDf());
            ps.setInt(31, microTask.getRwfkDf());
            ps.setInt(32, microTask.getRwzxDf());

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
     * 按分析期删除记录
     *
     * @param dataCalcParameter
     */
    public void deleteByFxq(DataCalcParameter dataCalcParameter) {

        Connection conn = null;
        String sql = "delete from wgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ?";
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
     * 根据id获取任务对象
     *
     * @param id
     * @return
     */
    public MicroTask getMicroTaskById(int id) {
        Connection conn = null;
        String sql = "select * from wgrw where id = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setBdl(rs.getBigDecimal("bdl"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return microTask;
    }

    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter) {
        Connection conn = null;
        String sql = "delete from wgrw where fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and rwzt_dm = '00'";
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
     * 更新任务对象
     *
     * @param microTask
     */

    public void update(MicroTask microTask) {
        Connection conn = null;
        String sql = "update wgrw set " +
                "nsrsbh = ?, swjg_dm = ?, fxq_nd = ?, fxq_sjq = ?," +
                "fxq_sjz = ?, cy_dm = ?, hydl_dm = ?, qyzclx_dm = ?," +
                "fxzb_dm = ?, tzqj = ?, tzfz = ?, zfz = ?," +
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

            ps.setString(1, microTask.getNsrsbh());
            ps.setString(2, microTask.getSwjgDm());
            ps.setString(3, microTask.getFxqNd());
            ps.setString(4, microTask.getFxqSjq());
            ps.setString(5, microTask.getFxqSjz());
            ps.setString(6, microTask.getCyDm());

            ps.setString(7, microTask.getHydlDm());
            ps.setString(8, microTask.getQyzclxDm());
            ps.setString(9, microTask.getFxzbDm());
            ps.setString(10, microTask.getTzqj());
            ps.setInt(11, microTask.getTzfz());
            ps.setBigDecimal(12, microTask.getZfz());
            ps.setString(13, microTask.getRwztDm());

            ps.setString(14, microTask.getRwtqryDm());
            ps.setString(15, microTask.getRwtqRq());
            ps.setString(16, microTask.getRwfbryDm());
            ps.setString(17, microTask.getRwfbRq());
            ps.setString(18, microTask.getRwzxryDm());
            ps.setString(19, microTask.getBzjsRq());

            ps.setString(20, microTask.getSjjsRq());
            ps.setString(21, microTask.getBzfkRq());
            ps.setString(22, microTask.getSjfkRq());
            ps.setString(23, microTask.getRwbgDm());
            ps.setString(24, microTask.getGlpj());
            ps.setString(25, microTask.getZfpj());
            ps.setString(26, microTask.getZhpj());
            ps.setString(27, microTask.getPjryDm());
            ps.setString(28, microTask.getRwpjRq());
            ps.setInt(29, microTask.getRwjsDf());
            ps.setInt(30, microTask.getRwfkDf());
            ps.setInt(31, microTask.getRwzxDf());

            ps.setInt(32, microTask.getId());

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
    public List<MicroTask> getMicroTaskListByZxryAndRwzt(String rwzxryDm,
                                                         String rwzrDm) {
        Connection conn = null;
        String sql = "select * from wgrw where rwzxry_dm = ? and rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwzxryDm);
            ps.setString(2, rwzrDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    public boolean isExist(MicroTask microTask) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean flag = true;
        String sql = " select count(*) from wgrw where nsrsbh = ? and " +
                " swjg_dm = ? and fxq_nd = ? and fxq_sjq = ? and fxq_sjz = ? and cy_dm = ? and hydl_dm = ? and qyzclx_dm = ? and fxzb_dm = ? and " +
                " tzqj = ? and tzfz = ?  and rwzt_dm <> '00' ";

        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,microTask.getNsrsbh().trim());
            ps.setString(2,microTask.getSwjgDm().trim());
            ps.setString(3,microTask.getFxqNd().trim());
            ps.setString(4,microTask.getFxqSjq().trim());
            ps.setString(5,microTask.getFxqSjz().trim());
            ps.setString(6,microTask.getCyDm().trim());
            ps.setString(7,microTask.getHydlDm().trim());
            ps.setString(8,microTask.getQyzclxDm().trim());
            ps.setString(9,microTask.getFxzbDm().trim());
            ps.setString(10,microTask.getTzqj().trim());
            ps.setInt(11,microTask.getTzfz());


            rs = ps.executeQuery();
            int total = 0;
            while(rs.next())
                 total = rs.getInt(1);
            if(total != 0)
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

    /**
     * 根据任务状态获取任务列表
     *
     * @param rwztDm
     * @return
     */
    public List<MicroTask> getMicroTaskListByRwzt(String rwztDm) {
        Connection conn = null;
        String sql = "select * from wgrw where rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwztDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
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
     * @param rwfbryDm
     * @param rwztDm
     * @return
     */
    public List<MicroTask> getMicroTaskListByFbryAndRwzt(String rwfbryDm,
                                                         String rwztDm) {
        Connection conn = null;
        String sql = "select * from wgrw where rwfbry_dm = ? and rwzt_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, rwfbryDm);
            ps.setString(2, rwztDm);

            rs = ps.executeQuery();

            while (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
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
     * 根据反馈时间起止 任务状态 任务执行人员 获得任务列表
     *
     * @param sjQ
     * @param sjZ
     * @param rwztDm
     * @param rwzxryDm
     * @return
     */
    public List<MicroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
                                                             String sjZ, String rwztDm, String rwzxryDm) {
        Connection conn = null;
        String sql = "select * from wgrw where sjfk_rq >= ? and sjfk_rq <= ? and rwzt_dm = ? and rwzxry_dm = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
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
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
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
     * 根据管理机关和任务状态获取任务列表
     *
     * @param swjgDm
     * @param rwztDm
     * @return
     */
    public List<MicroTask> getListBySwjgAndRwzt(String swjgDm, String rwztDm) {
        Connection conn = null;
        String sql = "select * from wgrw where swjg_dm like ? and rwzt_dm like ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        List<MicroTask> list = new ArrayList<MicroTask>();
        MicroTask microTask = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + swjgDm + "%");
            ps.setString(2, "%" + rwztDm + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                microTask = new MicroTask();
                microTask.setId(rs.getInt("id"));
                microTask.setNsrsbh(rs.getString("nsrsbh"));
                microTask.setSwjgDm(rs.getString("swjg_dm"));
                microTask.setFxqNd(rs.getString("fxq_nd"));
                microTask.setFxqSjq(rs.getString("fxq_sjq"));
                microTask.setFxqSjz(rs.getString("fxq_sjz"));
                microTask.setCyDm(rs.getString("cy_dm"));
                microTask.setHydlDm(rs.getString("hydl_dm"));
                microTask.setQyzclxDm(rs.getString("qyzclx_dm"));
                microTask.setFxzbDm(rs.getString("fxzb_dm"));
                microTask.setTzqj(rs.getString("tzqj"));
                microTask.setTzfz(rs.getInt("tzfz"));
                microTask.setZfz(rs.getBigDecimal("zfz"));
                microTask.setRwztDm(rs.getString("rwzt_dm"));
                microTask.setRwtqryDm(rs.getString("rwtqry_dm"));
                microTask.setRwtqRq(rs.getString("rwtq_rq"));
                microTask.setRwfbryDm(rs.getString("rwfbry_dm"));
                microTask.setRwfbRq(rs.getString("rwfb_rq"));
                microTask.setRwzxryDm(rs.getString("rwzxry_dm"));
                microTask.setBzjsRq(rs.getString("bzjs_rq"));
                microTask.setSjjsRq(rs.getString("sjjs_rq"));
                microTask.setBzfkRq(rs.getString("bzfk_rq"));
                microTask.setSjfkRq(rs.getString("sjfk_rq"));
                microTask.setRwbgDm(rs.getString("rwbg_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setGlpj(rs.getString("glpj"));
                microTask.setZfpj(rs.getString("zfpj"));
                microTask.setZhpj(rs.getString("zhpj"));
                microTask.setPjryDm(rs.getString("pjry_dm"));
                microTask.setRwpjRq(rs.getString("rwpj_rq"));
                microTask.setRwjsDf(rs.getInt("rwjs_df"));
                microTask.setRwfkDf(rs.getInt("rwfk_df"));
                microTask.setRwzxDf(rs.getInt("rwzx_df"));

                list.add(microTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

}
