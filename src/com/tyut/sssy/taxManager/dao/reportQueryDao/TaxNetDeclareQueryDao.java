package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TaxNetDeclareQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxNetDeclareReturnReport;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：SSSY
 * 类名称：网报情况查询～
 * 创建人：刘翔
 * 创建时间：2014-07-02
 *
 * @version 1.1
 */
public class TaxNetDeclareQueryDao {

    public List<TaxNetDeclareReturnReport> taxNetDeclareQueryBy(TaxNetDeclareQueryParameters parameters) {

        List<TaxNetDeclareReturnReport> returnReport = new ArrayList<TaxNetDeclareReturnReport>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;


        String sql = "select * from dss_wbqk_cx(?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getFxq());
            ps.setString(2, parameters.getZclx());
            ps.setString(3, parameters.getHydl());
            ps.setString(4, parameters.getHsjg());
            ps.setString(5, parameters.getGljg());
            ps.setString(6, parameters.getZgy());
            ps.setString(7,parameters.getYgz());
            ps.setString(8, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TaxNetDeclareReturnReport record = new TaxNetDeclareReturnReport();
                record.setZgh(rs.getInt("zgh"));
                record.setYbh(rs.getInt("ybh"));
                record.setDqwbh(rs.getInt("dqwbh"));
                record.setKtl(rs.getBigDecimal("ktl"));
                record.setSbse(rs.getBigDecimal("sbse"));
                record.setYjse(rs.getBigDecimal("yjse"));
                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
