package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.TaxPayerInfo;
import com.tyut.sssy.taxManager.domain.parameters.TaxRegisterQueryParameters;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：sssyrisk
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：${year}-${month}-${day}
 *
 * @version 1.1
 */
public class TaxPayerInfoDao {

    /**
     * 根据纳税人识别号查询纳税人信息
     * @param nsrsbh
     * @return 纳税人实体
     */
    public TaxPayerInfo queryById(String nsrsbh) {
        if (nsrsbh == null || nsrsbh.trim().equals("")) return null;
        else {
            TaxPayerInfo result = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String sql = "select * from dj_nsrxx where nsrsbh = ? ";
            try {
                conn = C3P0Util.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, nsrsbh.trim());
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = objectRefact(rs);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                C3P0Util.close(ps, rs, conn);
            }
            return result;
        }
    }

    /**
     * 根据登记状态获取纳税人信息列表
     * @param djztDm
     * @return 纳税人列表
     */
    public List<TaxPayerInfo> queryByDjzt(String djztDm,String qj , TaxRegisterQueryParameters paramters) {
        if (djztDm == null || djztDm.trim().equals("")) return null;
        else {
            List<TaxPayerInfo> list = new ArrayList<TaxPayerInfo>();
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String sql = "select * from dj_nsrxx where djzt_dm = ? ";
            try {
                conn = C3P0Util.getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, djztDm.trim());
                rs = ps.executeQuery();

                while (rs.next()) {
                    TaxPayerInfo taxPayerInfo = objectRefact(rs);
                    list.add(taxPayerInfo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                C3P0Util.close(ps, rs, conn);
            }
            return list;
        }
    }

    /**
     * 数据库字段到实体的映射类
     * @param rs
     * @return 纳税人信息实体
     */
    public TaxPayerInfo objectRefact(ResultSet rs){
        TaxPayerInfo taxPayerInfo = new TaxPayerInfo();
        try{
            taxPayerInfo.setSwjgDm(rs.getString("swjg_dm"));
            taxPayerInfo.setSwjgMc(rs.getString("swjg_mc"));
            taxPayerInfo.setNsrmc(rs.getString("nsrmc"));
            taxPayerInfo.setNsrsbh(rs.getString("nsrsbh"));
            taxPayerInfo.setCyDm(rs.getString("cy_dm"));
            taxPayerInfo.setDjztDm(rs.getString("djzt_dm"));
            taxPayerInfo.setHydlDm(rs.getString("hydl_dm"));
            taxPayerInfo.setWtdzDm(rs.getString("wtdz_dm"));
            taxPayerInfo.setSwdjztDm(rs.getString("swdjlb_dm"));
            taxPayerInfo.setDkdjDm(rs.getString("dkdj_dm"));
            taxPayerInfo.setQykglxDm(rs.getString("qykglx_dm"));
            taxPayerInfo.setQyzclxDm(rs.getString("qyzclx_dm"));
            taxPayerInfo.setZgyDm(rs.getString("zgy_dm"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return taxPayerInfo;
    }
}
