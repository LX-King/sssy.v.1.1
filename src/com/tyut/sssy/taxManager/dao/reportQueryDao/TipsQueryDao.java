package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TipsQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TipsReturnReport;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：SSSY
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：2014-07-03
 *
 * @version 1.1
 */
public class TipsQueryDao {



    public List<TipsReturnReport> tipsQueryBy(TipsQueryParameters parameters) {

        List<TipsReturnReport> returnReport = new ArrayList<TipsReturnReport>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;


        String sql = "select * from dss_tips_cx(?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getFxq());
            ps.setString(2, parameters.getZclx());
            ps.setString(3, parameters.getHydl());
            ps.setString(4, parameters.getHsjg());
            ps.setString(5, parameters.getGljg());
            ps.setString(6, parameters.getZgy());
            ps.setString(7, parameters.getJkfs());
            ps.setString(8, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TipsReturnReport record = new TipsReturnReport();
                record.setKths(rs.getInt("kths"));
                record.setKtl(rs.getBigDecimal("ktl"));
                record.setRkl(rs.getBigDecimal("rkl"));
                record.setRkse(rs.getBigDecimal("rkse"));
                record.setYnsje(rs.getBigDecimal("ynsje"));
                record.setZhs(rs.getInt("zhs"));

                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
