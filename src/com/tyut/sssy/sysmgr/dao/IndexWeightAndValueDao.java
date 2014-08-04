package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.IndexWeightAndValue;
import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * ClassName:IndexWeightAndValueDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 22:50:59
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexWeightAndValueDao {

    private static String DB_MAPPING = "dm_fxzb_fz";

    private static String DB_IDENTITY = "fxzb_dm";


    /**
     * 添加
     *
     * @param o
     * @return Boolean
     */
    public boolean add(IndexWeightAndValue o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into " + DB_MAPPING + " (fxzb_dm ,ycqj1 , ycqj2 , ysqj , zcqj ,lsqj ,qz ) values( ? , ? ,? ,? ,? ,? ,? )";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, o.getIndexCode());
            ps.setBigDecimal(2, o.getUnusualRange());
            ps.setBigDecimal(3, o.getUnusualRange2());
            ps.setBigDecimal(4, o.getExcellentRange());
            ps.setBigDecimal(5, o.getUsualRange());
            ps.setBigDecimal(6, o.getInferiorRange());
            ps.setInt(7, o.getWeight());
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
     * @return Boolean
     */
    public boolean modify(IndexWeightAndValue o) {
        boolean flag = true;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = " update " + DB_MAPPING + " set ycqj1 = ? , ycqj2=? ,ysqj=? , lsqj = ? , zcqj = ? ,qz = ?  where fxzb_dm = ? ";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, o.getUnusualRange());
            ps.setBigDecimal(2, o.getUnusualRange2());
            ps.setBigDecimal(3, o.getExcellentRange());
            ps.setBigDecimal(4, o.getUsualRange());
            ps.setBigDecimal(5, o.getInferiorRange());
            ps.setInt(6, o.getWeight());
            ps.setString(7, o.getIndexCode());
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
     * @return Boolean
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
        List<IndexWeightAndValue> list = new ArrayList<IndexWeightAndValue>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            /*判断参数是否为空*/
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
            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                IndexWeightAndValue temp = objectMapping(rs);
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

    public IndexWeightAndValue getById(String indexCode) {
        Connection conn = null;
        String sql = "select * from " + DB_MAPPING + " where " + DB_IDENTITY + " = ?";
        IndexWeightAndValue indexWeightAndValue = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, indexCode);
            rs = ps.executeQuery();
            while (rs.next()) {
                indexWeightAndValue = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        return indexWeightAndValue;
    }


    /**
     * 获取最大的ID
     *
     * @return String
     */
    public String getMaxId() {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxId = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            sql.append("select Max(fxzb_dm) from " + DB_MAPPING);

            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return maxId;
    }


    /**
     * 实现ORM功能
     *
     * @param rs
     * @return CompanyScale
     */
    public IndexWeightAndValue objectMapping(ResultSet rs) {
        IndexWeightAndValue index = new IndexWeightAndValue();
        try {
            index.setIndexCode(rs.getString("fxzb_dm"));
            index.setUnusualRange(rs.getBigDecimal("ycqj1"));
            index.setUnusualRange2(rs.getBigDecimal("ycqj2"));
            index.setExcellentRange(rs.getBigDecimal("ysqj"));
            index.setInferiorRange(rs.getBigDecimal("lsqj"));
            index.setUsualRange(rs.getBigDecimal("zcqj"));
            index.setWeight(rs.getInt("qz"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
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
        relations.put("indexName", "fxzb_mc");
        relations.put("unusualRange", "ycqj1");
        relations.put("unusualRange2", "ycqj2");
        relations.put("usualRange", "zcqj");
        relations.put("inferiorRange", "lsqj");
        relations.put("excellentRange", "ysqj1");
        relations.put("weight", "qz");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            for (Map.Entry<String, String> entry2 : relations.entrySet()) {
                if (key.equals(entry2.getKey())) {
                    params.put(entry2.getValue(), entry.getValue());
                    break;
                }
            }
        }
        return params;
    }

}
