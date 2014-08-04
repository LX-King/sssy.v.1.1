package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.Invoice;
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
public class InvoiceDao {


    /**
     * 获取发票种类的信息根据ID
     *
     * @param fpDm
     * @return 发票种类实体
     */
    public Invoice getInvoiceKindById(String fpDm) {
        Invoice invoiceKind = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from dm_fpzl where fp_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, fpDm.trim());
            rs = ps.executeQuery();

            while (rs.next()) {
                invoiceKind = new Invoice();
                invoiceKind.setMc(rs.getString("mc"));
                invoiceKind.setFpDm(rs.getString("fp_dm"));
                invoiceKind.setJe(rs.getInt("pmje"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return invoiceKind;
    }

    /**
     * 返回所有发票信息
     * @return 发票种类列表
     */
    public List<Invoice> getInvoiceKindList() {
        List<Invoice> list = new ArrayList<Invoice>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        String sql = "select * from dm_fpzl ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                Invoice manager = new Invoice();
                manager.setMc(rs.getString("mc"));
                manager.setFpDm(rs.getString("fp_dm"));
                manager.setJe(rs.getInt("pmje"));
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
