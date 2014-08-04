package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.parameters.TaxOtherCityRunQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxOtherCityRunReturnReport;
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
public class TaxOtherCityRunQueryDao {

    public List<TaxOtherCityRunReturnReport> taxOtherCityRunQueryBy(TaxOtherCityRunQueryParameters parameters) {

        List<TaxOtherCityRunReturnReport> returnReport = new ArrayList<TaxOtherCityRunReturnReport>();

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
        String sql = "select * from dss_wcnsr_cx(?,?,?,?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, parameters.getSbrqq());
            ps.setString(2,parameters.getSbrqz());
            ps.setString(3, parameters.getZclx());
            ps.setString(4, parameters.getHydl());
            ps.setString(5, parameters.getHsjg());
            ps.setString(6, parameters.getGljg());
            ps.setString(7, parameters.getZgy());
            ps.setString(8, parameters.getNsrsbh());

            rs = ps.executeQuery();

            while (rs.next()) {
                TaxOtherCityRunReturnReport record = new TaxOtherCityRunReturnReport();
                record.setNsrmc(rs.getString("nsrmc"));
                record.setNsrsbh(rs.getString("nsrsbh"));
                record.setJydz(rs.getString("jydz"));
                record.setJyxm(rs.getString("jyxm"));
                record.setZspm(rs.getString("zspm"));
                record.setSbrq(rs.getDate("sbrqq").toString());
                record.setHtje(rs.getBigDecimal("htje"));
                record.setYhs(rs.getBigDecimal("yhs"));
                record.setYys(rs.getBigDecimal("yys"));
                record.setSds(rs.getBigDecimal("sds"));
                record.setCye(rs.getBigDecimal("cye"));
                record.setZcdgljg(rs.getString("zcdgljg"));
                record.setWjzhm(rs.getString("wjzhm"));
                record.setWjckjjg(rs.getString("wjckjjg"));
                record.setWjzkjr(rs.getString("wjzkjr"));
                record.setWjzkjrq(rs.getString("wjzkjrq"));
                record.setWjzyxq(rs.getDate("wjzyxq").toString());
                record.setZfb(rs.getString("zfb"));
                record.setBydjrq(rs.getDate("bydjrq").toString());
                record.setSxbz(rs.getString("sxbz"));
                record.setGcxmbh(rs.getString("gcxmbh"));
                record.setGcxmmc(rs.getString("gcxmmc"));
                record.setJnsfhj(rs.getBigDecimal("jnsfhj"));

                returnReport.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnReport;

    }
}
