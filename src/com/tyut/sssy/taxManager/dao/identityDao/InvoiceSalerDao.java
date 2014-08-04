package com.tyut.sssy.taxManager.dao.identityDao;

import com.tyut.sssy.taxManager.domain.identity.InvoiceSaler;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 项目名称：SSSY
 * 类名称：${CLASS}
 * 创建人：刘翔
 * 创建时间：2014-06-26
 *
 * @version 1.1
 */
public class InvoiceSalerDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_fpfsrm";

    /*映射的表主键*/
    private static String DB_IDENTITY = "fpfsry_dm" ;


    /**
     * 根据参数查询
     *
     * @param params
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(Map<String, Object> params, int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<InvoiceSaler> list = new ArrayList<InvoiceSaler>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;
            if (params.isEmpty()) {
                sqlTotal.append("select count(*) from " + DB_MAPPING);
            } else {
                sqlTotal.append("select count(*) from " + DB_MAPPING + " where ");
                Map<String, Object> params2 = reflect(params);
                Iterator iter = params2.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
                    if (entry.getValue() == null) {
                        sqlTotal.append(entry.getKey() + " like '%'");
                    } else if (entry.getValue() instanceof String) {
                        sqlTotal.append(entry.getKey() + " like '%" + entry.getValue() + "%'");
                    } else {
                        sqlTotal.append(entry.getKey() + " = " + entry.getValue());
                    }

                    if (iter.hasNext()) {
                        sqlTotal.append(" and ");
                    }
                }
            }


            String sqlTotalStr = sqlTotal.toString();
            ps = conn.prepareStatement(sqlTotalStr);
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;
            //拼接分页SQL查询
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from " + DB_MAPPING + " where " + DB_IDENTITY + " > (select ISNULL(MAX(" + DB_IDENTITY + "),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from " + DB_MAPPING + " ");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where")));
            }
            sqlPaging.append("  )as A )");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(" and ");
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where") + 5));
            }
            sqlPaging.append("order by " + DB_IDENTITY + " ASC ");
            System.out.println(sqlPaging.toString());
            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                InvoiceSaler temp = objectMapping(rs);
                list.add(temp);
            }
            map.put("list", list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return map;
    }

    /**
     * 实现ORM
     *
     * @param id
     * @return a
     */
    public InvoiceSaler getById(String  id) {
        InvoiceSaler o = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from " + DB_MAPPING + " where " + DB_IDENTITY + " = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
           rs = ps.executeQuery();
            while (rs.next()) {
                o = objectMapping(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return o;
    }


    /**
     * 实现ORM
     *
     * @param rs
     * @return flag
     */
    public InvoiceSaler objectMapping(ResultSet rs) {
         InvoiceSaler o = new InvoiceSaler();
        try {
            o.setFpfsryDm(rs.getString("fpfsrm_dm"));
            o.setMc(rs.getString("mc"));
            o.setYxbz(rs.getString("yxbz"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * 维护表关系
     *
     * @param map
     * @return map
     */
    private Map<String, Object> reflect(Map<String, Object> map) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> relations = new HashMap<String, String>();
        /*relations.put("indexCode", "fxzb_dm");
        relations.put("indexName", "fxzb_mc");
        relations.put("unusualRange", "ycqj1");*/

        if(map.size()>0)
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, String> entry2 : relations.entrySet()) {
                if (key.equals(entry2.getKey())) {
                    params.put(entry2.getValue(), entry.getValue());
                }
            }
        }
        return params;
    }

}
