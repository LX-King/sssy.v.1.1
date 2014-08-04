package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.Index;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.*;

/**
 * ClassName:IndexDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-23
 * Time: 18:29:28
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexDao {
    /*映射的表名*/
    private static String DB_MAPPING = "dm_fxzb";

    /*映射的表主键*/
    private static String DB_IDENTITY = "fxzb_dm";

    /**
     * 添加
     *
     * @param o
     * @return flag
     */
    public boolean add(Index o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + "(fxzb_dm , mc , yxbz)values(?,?,'Y')";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getIndexCode());
            ps.setString(2, o.getIndexName());
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
    public boolean modify(Index o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update " + DB_MAPPING;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    /*根据ID选择Index*/
    public Index getById(String indexCode) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from " + DB_MAPPING + " where " + DB_IDENTITY + " = ?";
        Index index = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, indexCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                index = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return index;
    }

    /*根据ID选择Index*/
    public Index getByName(String indexName) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from " + DB_MAPPING + " where  mc like ?";
        Index index = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+indexName+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                index = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return index;


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
    public Map<String, Object> queryByCon(HashMap<String, Object> params, int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Index> list = new ArrayList<Index>();

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
                Index temp = objectMapping(rs);
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
     * @param rs
     * @return flag
     */
    public Index objectMapping(ResultSet rs) {
        Index o = new Index();
        try {
            o.setIndexCode(rs.getString("fxzb_dm"));
            o.setIndexName(rs.getString("mc"));
            o.setUse(rs.getString("yxbz"));
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
        relations.put("indexName", "mc");
        relations.put("isUse", "yxbz");

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

    /**
     * 获取指标列表
     * @return
     */
	public List<Index> getIndexList() {
		Connection conn = null;
        String sql = "select * from dm_fxzb";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Index> list = new ArrayList<Index>();

        Index index = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
            	index = new Index();
            	index.setIndexCode(rs.getString("fxzb_dm"));
            	index.setIndexName(rs.getString("mc"));
            	index.setUse(rs.getString("yxbz"));
            	
                list.add(index);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
	}

}
