package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.domain.report.InvoiceSaleReturnReport;
import com.tyut.sssy.taxManager.domain.parameters.InvoiceSaleQueryParameters;
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
 * 创建时间：2014-06-04
 *
 * @version 1.1
 */
public class InvoiceSaleQueryDao {

    public List<InvoiceSaleReturnReport> invoiceSaleQueryBy(InvoiceSaleQueryParameters parameters) {
        List<InvoiceSaleReturnReport> report = new ArrayList<InvoiceSaleReturnReport>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        /**参数：
         *发票销售日期:@xsrqq CHAR(10)
         *注册类型 @zclx char(3)
         *行业类别 @hydl char(4)
         *核算机关 @hsjg char(11)
         *管理机关 @gljg char(11)
         *管理员 @zgy CHAR(11)
         *发票种类 @fpzl char(12)
         *销售人员 @xsry char(11)
         *纳税人识别号 @nsrsbh varchar(20)
         *销售起号码 @xsqhm char(10)
         *销售止号码 @xszhm char(10)
         */
        String sql = "select * from dss_fpxsqk_cx (?,?,?,?,?,?,?,?,?,?,?)";
        try{
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1,parameters.getFpxsrqq());
            ps.setString(2,parameters.getZclx());
            ps.setString(3,parameters.getHydl());
            ps.setString(4,parameters.getHsjg());
            ps.setString(5,parameters.getSwjg());
            ps.setString(6,parameters.getZgy());
            ps.setString(7,parameters.getFpzl());
            ps.setString(8,parameters.getFpfsry());
            ps.setString(9,parameters.getNsrsbh());
            ps.setString(10,parameters.getXsqhm());
            ps.setString(11,parameters.getXszhm());

            rs = ps.executeQuery();

            while(rs.next()){
                InvoiceSaleReturnReport record = new InvoiceSaleReturnReport();
                record.setNsrsbh(rs.getString("nsrsbh"));
                record.setNsrmc(rs.getString("nsrmc"));
                record.setZclx(rs.getString("zclx"));
                record.setHydl(rs.getString("hydl"));
                record.setFpzl(rs.getString("fpzl"));
                record.setYde(rs.getInt("yde"));
                record.setYnyys(rs.getBigDecimal("ynyys"));
                record.setYnsds(rs.getBigDecimal("ynsds"));
                record.setGpje(rs.getBigDecimal("gpje"));
                record.setFsry(rs.getString("fsry"));
                record.setDgce(rs.getBigDecimal("dgce"));
                record.setFphm(rs.getString("fphm"));
                record.setFsrq(rs.getString("fsrq"));
                record.setQzd(rs.getInt("qzd"));
                record.setXwqy(rs.getString("xwqy"));
                record.setYysjsyj(rs.getBigDecimal("yysjsyj"));
                record.setYjyys(rs.getBigDecimal("yjyys"));
                record.setYjsds(rs.getBigDecimal("yjsds"));
                record.setSdsce(rs.getBigDecimal("sdsce"));
                record.setSbrq(rs.getString("sbrq"));
                record.setSbbzl(rs.getString("sbbzl"));

                report.add(record);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return report;
    }
}
