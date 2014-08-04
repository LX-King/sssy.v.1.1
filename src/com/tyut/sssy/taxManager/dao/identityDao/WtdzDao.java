package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.Wtdz;
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
public class WtdzDao {

    private Wtdz getWtdzById(String wtdz) {
        if (wtdz == null || wtdz.trim().equals("")) return null;
        else {
            Wtdz result = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String sql = "select * from dm_wtdz where wtdz_dm  = ? ";
            try {
                conn = C3P0Util.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, wtdz.trim());
                rs = ps.executeQuery();

                while (rs.next()) {
                    result = new Wtdz();
                    result.setMc(rs.getString("mc"));
                    result.setWtdzDm(rs.getString("wtdz_dm"));
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
