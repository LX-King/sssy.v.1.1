package com.tyut.sssy.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.domain.FirmSize;
import com.tyut.sssy.utils.db.C3P0Util;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：FirmSizeDao  
 * 类描述：企业规模dao  
 * 创建人：梁斌  
 * 创建时间：2012-5-17 下午03:08:50  
 * 修改人：梁斌  
 * 修改时间：2012-5-17 下午03:08:50  
 * 修改备注：  
 * @version 
 *
 */
public class FirmSizeDao {

	public List<FirmSize> getFirmSizeList() {
		Connection conn = null;
        String sql = "select * from dm_qygm";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<FirmSize> list = new ArrayList<FirmSize>();

        FirmSize firmSize = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	firmSize = new FirmSize();
            	firmSize.setQygmDm(rs.getString("qygm_dm"));
            	firmSize.setQygmMc(rs.getString("qygm_mc"));
            	firmSize.setSeQ(rs.getString("se_q"));
            	firmSize.setSeZ(rs.getString("se_z"));
            	
                list.add(firmSize);
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
