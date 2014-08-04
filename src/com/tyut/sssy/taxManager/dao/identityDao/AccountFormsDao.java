package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.AccountForms;
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
public class AccountFormsDao {


    /**
     * 获取征管员的信息根据ID
     *
     * @param hsxsDm
     * @return 征管员对象
     */
    public AccountForms getAccountFormsById(String hsxsDm) {
        AccountForms accountForms = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from hsxs_dm where hsxs_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, hsxsDm.trim());
            rs = ps.executeQuery();

            while (rs.next()) {
                accountForms = new AccountForms();
                accountForms.setMc(rs.getString("mc"));
                accountForms.setHsxsDm(rs.getString("hsxs_dm"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return accountForms;
    }

    /**
     * 返回核算形式列表
     * @return 核算形式列表
     */
    public List<AccountForms> getAccountFormsList() {
        List<AccountForms> list = new ArrayList<AccountForms>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from dm_hsxs ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccountForms manager = new AccountForms();
                manager.setMc(rs.getString("mc"));
                manager.setHsxsDm(rs.getString("hsxs_dm"));
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
