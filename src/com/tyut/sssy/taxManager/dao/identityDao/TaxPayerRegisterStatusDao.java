package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.TaxPayerRegisterStatus;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 项目名称：sssyrisk
 * 类名称：WTDZ接口
 * 创建人：刘翔
 * 修改时间：14-5-26
 * 修改备注：
 */
public class TaxPayerRegisterStatusDao {


    /**
     * 返回纳税人登记状态根据其名称
     * @param name
     * @return 纳税人登记状态实体
     */
    public TaxPayerRegisterStatus getTaxPayerReigsterStatusByName(String name) {
        if (name == null || name.trim().equals("")) return null;
        else {
            TaxPayerRegisterStatus result = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String sql = "select * from dm_djzt where mc  = ? ";
            try {
                conn = C3P0Util.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, name.trim());
                rs = ps.executeQuery();

                while (rs.next()) {
                    result = new TaxPayerRegisterStatus();
                    result.setMc(rs.getString("mc"));
                    result.setDjztDm(rs.getString("djzt_dm"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                C3P0Util.close(ps, rs, conn);
            }
            return result;

        }

    }

    public TaxPayerRegisterStatus getTaxPayerRegisterStatusById(String djztDm) {
        if (djztDm == null || djztDm.trim().equals("")) return null;
        else {
            TaxPayerRegisterStatus result = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String sql = "select * from dm_djzt where djzt_dm  = ? ";
            try {
                conn = C3P0Util.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, djztDm.trim());
                rs = ps.executeQuery();

                while (rs.next()) {
                    result = new TaxPayerRegisterStatus();
                    result.setMc(rs.getString("mc"));
                    result.setDjztDm(rs.getString("djzt_dm"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                C3P0Util.close(ps, rs, conn);
            }
            return result;

        }


    }
}
