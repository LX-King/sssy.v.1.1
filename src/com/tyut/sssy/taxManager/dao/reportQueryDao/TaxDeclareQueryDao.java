package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TaxDeclareQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxDeclareReturnReport;
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
 * 创建时间：2014-07-02
 *
 * @version 1.1
 */
public class TaxDeclareQueryDao {

    public List<TaxDeclareReturnReport> taxDeclareQueryBy(TaxDeclareQueryParameters parameters) {

        List<TaxDeclareReturnReport> returnReport = new ArrayList<TaxDeclareReturnReport>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;


        String sql = "select * from dss_sbqk_cx(?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getFxq());
            ps.setString(2, parameters.getZclx());
            ps.setString(3, parameters.getHydl());
            ps.setString(4, parameters.getHsjg());
            ps.setString(5, parameters.getGljg());
            ps.setString(6, parameters.getZgy());
            ps.setString(7,parameters.getSbfs());
            ps.setString(8, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TaxDeclareReturnReport record = new TaxDeclareReturnReport();
                record.setYsbhs(rs.getInt("ysbhs"));
                record.setSbl(rs.getBigDecimal("sbl"));
                record.setZqsbhs(rs.getInt("zqsbhs"));
                record.setZqsbl(rs.getBigDecimal("zqsbl"));
                record.setYqsbhs(rs.getInt("yqsbhs"));
                record.setYqsbl(rs.getBigDecimal("yqsbl"));
                record.setLsbhs(rs.getInt("lsbhs"));
                record.setLsbl(rs.getBigDecimal("lsbl"));
                record.setLxsbhs(rs.getInt("lxsbhs"));
                record.setLxsbl(rs.getBigDecimal("lxsbl"));
                record.setWsbhs(rs.getInt("wsbhs"));
                record.setWsbl(rs.getBigDecimal("wsbl"));

                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
