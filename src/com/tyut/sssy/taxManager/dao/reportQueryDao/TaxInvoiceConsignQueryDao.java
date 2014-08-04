package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TaxInvoiceConsignQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxInvoiceConsignReturnReport;
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
public class TaxInvoiceConsignQueryDao {

    public List<TaxInvoiceConsignReturnReport> taxInvoiceConsignQueryBy(TaxInvoiceConsignQueryParameters parameters) {

        List<TaxInvoiceConsignReturnReport> returnReport = new ArrayList<TaxInvoiceConsignReturnReport>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        /**
         *申报日期起:sbrqq,
         *注册类型 zclx,
         *行业类别 hydl,
         *核算机关 hsjg,
         *管理机关 gljg
         *管理员 zgy,
         *纳税人识别号 nsrsbh
         **/
        String sql = "select * from dss_dkfp_cx(?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getKprqq());
            ps.setString(2,parameters.getKprqz());
            ps.setString(3, parameters.getZclx());
            ps.setString(4, parameters.getHydl());
            ps.setString(5, parameters.getHsjg());
            ps.setString(6, parameters.getGljg());
            ps.setString(7, parameters.getZgy());
            ps.setString(8, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TaxInvoiceConsignReturnReport record = new TaxInvoiceConsignReturnReport();
                record.setNsrmc(rs.getString("nsrmc"));
                record.setNsrsbh(rs.getString("nsrsbh"));
                record.setHymc(rs.getString("hymc"));
                record.setJyxm(rs.getString("jyxm"));
                record.setKprq(rs.getDate("kprq").toString());
                record.setFpmc(rs.getString("fpmc"));
                record.setFphm(rs.getString("fphm"));
                record.setWspzhm(rs.getString("wspzhm"));
                record.setDkfpje(rs.getBigDecimal("dkfpje"));
                record.setZspm(rs.getString("zspm"));
                record.setSe(rs.getBigDecimal("se"));
                record.setZfbz(rs.getString("zfbz"));
                record.setZfrq(rs.getDate("zfrq").toString());
                record.setZfyy(rs.getString("zfyy"));
                record.setSpc(rs.getString("spc"));
                record.setCs(rs.getInt("cs"));

                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
