package com.tyut.sssy.taxManager.dao.reportQueryDao;

import com.tyut.sssy.taxManager.dao.identityDao.TaxPayerRegisterStatusDao;
import com.tyut.sssy.taxManager.domain.parameters.TaxRegisterQueryParameters;
import com.tyut.sssy.taxManager.domain.report.TaxRegisterReturnReport;
import com.tyut.sssy.utils.db.C3P0Util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sssyrisk
 * 类名称：税务登记查询数据库接口
 * 类描述：税务征管登记类数据库查询类
 * 创建人：刘翔
 * 创建时间：2014.4.20
 * 修改人：刘翔
 */
public class TaxRegisterQueryDao {

    /**
     * 税务登记查询
     * @param  taxRegisterQueryParameters
     * @return List<TaxRegisterReturnReport> report
     */
    public List<TaxRegisterReturnReport> taxRegisterQueryBy(TaxRegisterQueryParameters taxRegisterQueryParameters){

        List<TaxRegisterReturnReport> report = new ArrayList<TaxRegisterReturnReport>();
        PreparedStatement ps = null;
        ResultSet rs = null ;
        Connection conn = null;
        TaxRegisterReturnReport taxRegisterReturnReport = null;

        /**
         * 数据接口调用为:
         * dss_swdjqk_cx
         * Parameters:
         * @fxq CHAR(6)  分析期,
         * @czq char(6)  参照期,
         * @zclx char(3) 注册类型,
         * @hydl char(4) 行业类别,
         * @hsjg char(11)核算机关 ,
         * @gljg char(11)管理机关,
         * @zgy CHAR(11) 征管员,
         * @hsxs char(1) 核算形式,
         * @zdsy char(1) 重点税源标志
         */
        String sql = "select * from dss_swdjqk_cx (?,?,?,?,?,?,?,?,?) ";

        try{
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,taxRegisterQueryParameters.getFxq());
            ps.setString(2,taxRegisterQueryParameters.getCzq());
            ps.setString(3,taxRegisterQueryParameters.getZclx());
            ps.setString(4,taxRegisterQueryParameters.getHylb());
            ps.setString(5,taxRegisterQueryParameters.getHsjg());
            ps.setString(6,taxRegisterQueryParameters.getGljg());
            ps.setString(7,taxRegisterQueryParameters.getZgy());
            ps.setString(8,taxRegisterQueryParameters.getHsxs());
            ps.setString(9,taxRegisterQueryParameters.getZdsy());

            rs = ps.executeQuery();

            TaxPayerRegisterStatusDao statusDao  = new TaxPayerRegisterStatusDao();
            while(rs.next()){
                taxRegisterReturnReport = new TaxRegisterReturnReport() ;
                taxRegisterReturnReport.setRegisterType(rs.getString("xm"));//列名
                taxRegisterReturnReport.setA1(rs.getInt("a1"));
                taxRegisterReturnReport.setA2(rs.getInt("a2"));
                taxRegisterReturnReport.setA3(rs.getInt("a3"));
                taxRegisterReturnReport.setA4(rs.getBigDecimal("a4").multiply(new BigDecimal(100)));
                taxRegisterReturnReport.setRegisterStatus(statusDao.getTaxPayerReigsterStatusByName(taxRegisterReturnReport.getRegisterType()));
                report.add(taxRegisterReturnReport);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            C3P0Util.close(ps,rs,conn);
        }
        return report;
    }
}
