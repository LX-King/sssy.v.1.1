package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.MajorIndustry;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName:MajorIndustryDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 14:40:52
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class MajorIndustryDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_hydl";

    /*映射的表主键*/
    private static String DB_IDENTITY = "hydl_dm";

    /**
     * 添加
     *
     * @return flag
     * @parma o
     */
    public boolean add(MajorIndustry o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(hydl_dm ,mc,jc,cy_dm,gylx_dm)values(?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getMajorIndustryCode());
            ps.setString(2, o.getMajorIndustryName());
            ps.setString(3,o.getJc());
            ps.setString(4, o.getIndustryCode());
            ps.setString(5, o.getGmlxDm());
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
     * 修改
     *
     * @return flag
     * @parama o
     */
    public boolean modify(MajorIndustry o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set cy_dm = ? , gylx_dm = ? where hydl_dm = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getIndustryCode());
            ps.setString(2, o.getGmlxDm());
            ps.setString(3, o.getMajorIndustryCode());
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
     * @return flag
     * @parama ids
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
        List<MajorIndustry> list = new ArrayList<MajorIndustry>();

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
                MajorIndustry temp = objectMapping(rs);
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
     * @return Major
     */
    public MajorIndustry getById(String id) {
        MajorIndustry o = null;
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

    public List<MajorIndustry> queryAll() {
        MajorIndustry o = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from " + DB_MAPPING;
        List<MajorIndustry> list = new ArrayList<MajorIndustry>();
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                o = objectMapping(rs);
                list.add(o);
            }
        } catch (Exception e) {
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
     * @return flag
     */
    public MajorIndustry objectMapping(ResultSet rs) {
        MajorIndustry o = new MajorIndustry();
        try {
            o.setMajorIndustryCode(rs.getString("hydl_dm"));
            o.setMajorIndustryName(rs.getString("mc"));
            o.setJc(rs.getString("jc"));
            o.setIndustryCode(rs.getString("cy_dm"));
            o.setGmlxDm(rs.getString("gylx_dm"));
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
        relations.put("majorIndustryCode", "hydl_dm");
        relations.put("majorIndustryName", "mc");
        relations.put("industryCode", "cy_dm");
        relations.put("jc", "jc");
        relations.put("gmlxDm", "gylx_dm");


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
