package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.Industry;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName:IndustryDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 14:29:16
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndustryDao {


    /*映射的表名*/
    private static String DB_MAPPING = "dm_cy";

    /*映射的表主键*/
    private static String DB_IDENTITY = "cy_dm";


    /**
     * 根据参数查询
     *
     * @param params
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(HashMap<String, Object> params, int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Industry> list = new ArrayList<Industry>();

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
            System.out.println(sqlTotalStr);
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
                Industry temp = objectMapping(rs);
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
     * @param id
     * @return Industry
     */
    public Industry getById(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " select * from dm_cy where cy_dm = ?";
        Industry o = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                o = objectMapping(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return o;
    }

    public List<Industry> getAllIndustry() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " select * from dm_cy ";
        List<Industry> list = new ArrayList<Industry>();
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Industry o = objectMapping(rs);
                list.add(o);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }


    /**
     * 实现ORM
     *
     * @param rs
     * @return o
     */
    public Industry objectMapping(ResultSet rs) {
        Industry o = new Industry();
        try {
            o.setIndustryCode(rs.getString("cy_dm"));
            o.setIndustryName(rs.getString("mc"));
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
        relations.put("industryCode", "cy_dm");
        relations.put("industryName", "mc");

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
