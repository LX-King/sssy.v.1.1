package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.TaxManager;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sssyrisk
 * 类名称：${CLASS}
 * 类描述：微观任务类
 * 创建人：刘翔
 * 创建时间：
 * 修改人：刘翔
 * 修改时间：14-5-25
 * 修改备注：
 */
public class TaxManagerDao {


    /**
     * 获取征管员的信息根据ID
     *
     * @param zgyDm
     * @return 征管员对象
     */
    public TaxManager getTaxManagerById(String zgyDm) {
        TaxManager manager = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from dm_zgy where zgy_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, zgyDm.trim());
            rs = ps.executeQuery();

            while (rs.next()) {
                manager = new TaxManager();
                manager.setMc(rs.getString("mc"));
                manager.setSwjgDm(rs.getString("swjg_dm"));
                manager.setZgyDm(rs.getString("zgy_dm"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return manager;
    }

    /**
     * 返回特定的税务机关的征管员
     * @param swjgDm
     * @return 征管员列表
     */
    public List<TaxManager> getTaxManagerListBySwjg(String swjgDm) {
        List<TaxManager> list = new ArrayList<TaxManager>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from dm_zgy where swjg_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, swjgDm.trim());
            rs = ps.executeQuery();

            while (rs.next()) {
                TaxManager manager = new TaxManager();
                manager.setMc(rs.getString("mc"));
                manager.setSwjgDm(rs.getString("swjg_dm"));
                manager.setZgyDm(rs.getString("zgy_dm"));
                list.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }
}
