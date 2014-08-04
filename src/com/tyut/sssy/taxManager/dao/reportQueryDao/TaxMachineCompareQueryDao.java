package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TaxMachineCompareQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxMachineCompareReturnReport;
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
public class TaxMachineCompareQueryDao {

    public List<TaxMachineCompareReturnReport> taxMachineCompareQueryBy(TaxMachineCompareQueryParameters parameters) {

        List<TaxMachineCompareReturnReport> returnReport = new ArrayList<TaxMachineCompareReturnReport>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        /**
         *申报日期起:sbrqq,
         *申报日期止:sbrqz,
         *催报日期起 cbrqq,
         *催报日期止 cbrqz,
         *注册类型 zclx,
         *行业类别 hydl,
         *核算机关 hsjg,
         *管理机关 gljg
         *管理员 zgy,
         *纳税人识别号 nsrsbh
         **/

        String sql = "select * from dss_lgjdp_cx(?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getSbrqq());
            ps.setString(2, parameters.getSbrqz());
            ps.setString(3, parameters.getCbrqq());
            ps.setString(4, parameters.getCbrqz());
            ps.setString(5, parameters.getZclx());
            ps.setString(6, parameters.getHydl());
            ps.setString(7, parameters.getHsjg());
            ps.setString(8, parameters.getSwjg());
            ps.setString(9, parameters.getNsrsbh());
            ps.setString(10, parameters.getZgy());
            ps.setString(11, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TaxMachineCompareReturnReport record = new TaxMachineCompareReturnReport();
                record.setYyshs(rs.getInt("yyshs"));
                record.setLph(rs.getInt("lph"));
                record.setLpzb(rs.getBigDecimal("lphzb"));
                record.setJdp(rs.getInt("jdp"));
                record.setJdpzb(rs.getBigDecimal("jdpzb"));
                record.setWgp(rs.getInt("wgp"));
                record.setWgpzb(rs.getBigDecimal("wgpzb"));

                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
