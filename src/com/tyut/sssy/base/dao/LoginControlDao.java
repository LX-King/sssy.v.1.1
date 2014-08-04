package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tyut.sssy.base.domain.LoginControl;
import com.tyut.sssy.utils.db.LoginControlC3P0Util;

public class LoginControlDao {

	/**
	 * 根据代码前缀获取登录控制类
	 * @param dmQz
	 * @return
	 */
	public LoginControl getBySwjgDm(String dmQz) {
		Connection conn = null;
        String sql = "select * from dlkz where swjg_dm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        LoginControl loginControl = null;
        try {
            // C3P0创建连接
            conn = LoginControlC3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, dmQz);

            rs = ps.executeQuery();

            while (rs.next()) {
            	loginControl = new LoginControl();
            	loginControl.setSwjgDm(rs.getString("swjg_dm"));
            	loginControl.setYyDz(rs.getString("yy_dz"));
            	loginControl.setIpQ(rs.getString("ip_q"));
            	loginControl.setIpZ(rs.getString("ip_z"));
            	loginControl.setMc(rs.getString("mc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
        	LoginControlC3P0Util.close(ps, rs, conn);
        }

        return loginControl;
	}

}
