package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.Index;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName:IndexFeatureDBDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-26
 * Time: 15:47:55
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexFeatureDBDao {

    /*映射的表名*/
    private static String DB_MAPPING = "dm_fxzb_wzms";

    /**
     * 添加
     *
     * @return flag
     * @parm o
     */
    public boolean add(IndexFeatureDB o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(fxzb_dm , fxzb_lx,tz,yd,jy )values(?,?,?,?,?)";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getIndexCode());
            ps.setString(2, o.getType());
            ps.setString(3, o.getFeature());
            ps.setString(4, o.getQuestion());
            ps.setString(5, o.getOption());
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
     * @param o
     * @return flag
     */
    public boolean modify(IndexFeatureDB o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING + " set tz = ?,yd =?,jy=? where fxzb_dm = ? and fxzb_lx = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getFeature());
            ps.setString(2, o.getQuestion());
            ps.setString(3, o.getOption());
            ps.setString(4, o.getIndexCode());
            ps.setString(5, o.getType());
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
     * 根据参数查询
     *
     * @param indexCode
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String,Object> queryByCon(String indexCode, int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IndexFeatureDB> list = new ArrayList<IndexFeatureDB>();
        Map<String,Object> map = new HashMap<String, Object>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            if(indexCode != null && !indexCode.equals(""))
                sqlTotal.append("select count(*) from " + DB_MAPPING + " where fxzb_dm  like '%" + indexCode + "%'");
            else
                sqlTotal.append("select count(*) from " + DB_MAPPING + " where fxzb_dm  like '%'");
            String sqlTotalStr = sqlTotal.toString();

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            while(rs.next()){
                map.put("totalProperty",rs.getInt(1)/5);
            }


            //拼接分页SQL查询
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from " + DB_MAPPING + " where fxzb_dm > (select ISNULL(MAX(fxzb_dm),0)  from (  ");
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
            sqlPaging.append(" order by fxzb_dm ");
//            System.out.println(sqlPaging.toString());
            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                IndexFeatureDB temp = objectMapping(rs);
                list.add(temp);
            }
            map.put("list",list);
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
     * @param indexCode , type
     * @return a
     */
    public IndexFeatureDB getByCodeAndType(String indexCode, String type) {
        IndexFeatureDB o = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from " + DB_MAPPING + " where fxzb_dm = ? and fxzb_lx = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, indexCode);
            ps.setString(2, type);
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
    public IndexFeatureDB objectMapping(ResultSet rs) {
        IndexFeatureDB o = new IndexFeatureDB();
        try {
            o.setIndexCode(rs.getString("fxzb_dm"));
            o.setType(rs.getString("fxzb_lx"));
            o.setFeature(rs.getString("tz"));
            o.setQuestion(rs.getString("yd"));
            o.setOption(rs.getString("jy"));
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
        relations.put("type", "fxzb_lx");
        relations.put("feature", "tz");
        relations.put("question", "yd");
        relations.put("option", "jy");

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
