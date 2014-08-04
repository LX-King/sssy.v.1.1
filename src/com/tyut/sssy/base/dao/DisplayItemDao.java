package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.DisplayItem;
import com.tyut.sssy.utils.db.C3P0Util;

public class DisplayItemDao {

	/**
	 * 显示项目列表
	 * @return
	 */
	public List<DisplayItem> getDisplayItemList() {
		Connection conn = null;
        String sql = "select * from dm_zbfl";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DisplayItem> list = new ArrayList<DisplayItem>();

        DisplayItem displayItem = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	displayItem = new DisplayItem();
            	displayItem.setFl(rs.getString("fl"));
            	displayItem.setFlmc(rs.getString("flmc"));
            	
                list.add(displayItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}
	
	public DisplayItem getById(String id) {
        Connection conn = null;
        String sql = "select * from dm_zbfl where fl = ? ";
        PreparedStatement ps = null;
        ResultSet rs = null;

        DisplayItem displayItem = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                displayItem = new DisplayItem();
                displayItem.setFl(rs.getString("fl"));
                displayItem.setFlmc(rs.getString("flmc"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return displayItem;
    }

}
