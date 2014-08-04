package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.IndexInitConfig;
import com.tyut.sssy.utils.db.C3P0Util;

import java.util.*;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName:IndexInitConfigDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 15:53:02
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexInitConfigDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_fxzb_qj";

    /*映射的表主键*/
    private static String DB_IDENTITY = "fxzb_dm";

    /**
     * 修改
     *
     * @param o
     * @return flag
     */
    public boolean modify(IndexInitConfig o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set ycqj1 = ? , ycqj2 = ? ,ysqj = ? ,zcqj = ? ,lsqj = ? , ycqj1_qs = ? , ycqj2_qs = ? ,ysqj_qs =?,zcqj_qs = ?, lsqj_qs = ? where fxzb_dm = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, o.getUnusualRange());
            ps.setBigDecimal(2, o.getUnusualRange2());
            ps.setBigDecimal(3, o.getExcellentRange());
            ps.setBigDecimal(4, o.getUsualRange());
            ps.setBigDecimal(5, o.getInferiorRange());
            ps.setBigDecimal(6, o.getUnusualRangeDef());
            ps.setBigDecimal(7, o.getUnusualRange2Def());
            ps.setBigDecimal(8, o.getExcellentRangeDef());
            ps.setBigDecimal(9, o.getUsualRangeDef());
            ps.setBigDecimal(10, o.getInferiorRangeDef());
            ps.setString(11, o.getIndexCode());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return flag
     */
    public boolean del(String ids[]) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from " + DB_MAPPING + " where " + DB_IDENTITY + " = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (String id : ids) {
                ps.setString(1, id);
                ps.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

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
        List<IndexInitConfig> list = new ArrayList<IndexInitConfig>();

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
                IndexInitConfig temp = objectMapping(rs);
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
    public IndexInitConfig getById(String id) {
        IndexInitConfig o = null;
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
    public IndexInitConfig objectMapping(ResultSet rs) {
        IndexInitConfig o = new IndexInitConfig();
        try {
            o.setUnusualRange(rs.getBigDecimal("ycqj1"));
            o.setUnusualRange2(rs.getBigDecimal("ycqj2"));
            o.setExcellentRange(rs.getBigDecimal("ysqj"));
            o.setInferiorRange(rs.getBigDecimal("lsqj"));
            o.setUsualRange(rs.getBigDecimal("zcqj"));
            o.setUnusualRangeDef(rs.getBigDecimal("ycqj1_qs"));
            o.setUnusualRange2Def(rs.getBigDecimal("ycqj2_qs"));
            o.setExcellentRangeDef(rs.getBigDecimal("ysqj_qs"));
            o.setInferiorRangeDef(rs.getBigDecimal("lsqj_qs"));
            o.setUsualRangeDef(rs.getBigDecimal("zcqj_qs"));
            o.setIndexCode(rs.getString("fxzb_dm"));
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
        relations.put("indexCode", "fxzb_dm");
        relations.put("unusualRange", "ycqj1");
        relations.put("unusualRange2", "ycqj2");
        relations.put("excellentRange", "ysqj");
        relations.put("usualRange", "zcqj");
        relations.put("inferiorRange", "lsqj");
        relations.put("unusualRangeDef", "ycqj1_qs");
        relations.put("unusualRange2Def", "ycqj2_qs");
        relations.put("excellentRangeDef", "ysqj_qs");
        relations.put("usualRangeDef", "zcqj_qs");
        relations.put("inferiorRangeDef", "lsqj_qs");

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
