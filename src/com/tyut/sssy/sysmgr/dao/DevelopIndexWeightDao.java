package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.DevelopIndexWeight;
import com.tyut.sssy.utils.db.C3P0Util;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName:DevelopIndexWeightDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-15
 * Time: 14:25:44
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DevelopIndexWeightDao {

    /**
     * 添加操作
     *
     * @param developIndexWeight
     * @return boolean
     */
    public boolean add(DevelopIndexWeight developIndexWeight) {
        boolean flag = true;
        Connection conn = null;
        String sql = "insert into dm_fzzsqz (zb_dm ,zb_mc,zb_qz) values(?,?,?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, developIndexWeight.getIndexCode());
            ps.setString(2, developIndexWeight.getIndexName());
            ps.setBigDecimal(3, developIndexWeight.getIndexWeight());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
        return flag;
    }


    /**
     * 修改操作
     *
     * @param developIndexWeight
     * @return boolean
     */
    public boolean modify(DevelopIndexWeight developIndexWeight) {
        boolean flag = true;
        Connection conn = null;
        String sql = "update  dm_fzzsqz set zb_dm = ? ,zb_mc = ?, zb_qz = ? where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, developIndexWeight.getIndexCode());
            ps.setString(2, developIndexWeight.getIndexName());
            ps.setBigDecimal(3, developIndexWeight.getIndexWeight());
            ps.setInt(4, developIndexWeight.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
        return flag;
    }

    /**
     * 通过 ID 查询
     *
     * @param id
     * @return developIndexWeight
     */
    public DevelopIndexWeight getById(int id) {
        Connection conn = null;
        String sql = "select * from dm_fzzsqz where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        DevelopIndexWeight developIndexWeight = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                developIndexWeight = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
        return developIndexWeight;
    }

    /**
     * 删除操作
     *
     * @param ids
     * @return boolean
     */
    public boolean del(int ids[]) {
        boolean flag = true;
        Connection conn = null;
        String sql = "delete  from dm_fzzsqz where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (int id : ids) {
                ps.setInt(1, id);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }
        return flag;
    }


    /**
     * 返回查询结果
     *
     * @param indexName
     * @param pageSize
     * @param pageSize
     * @return map
     */
    public Map<String, Object> queryByCon(String indexName, int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<DevelopIndexWeight> list = new ArrayList<DevelopIndexWeight>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            /*判断参数是否为空*/
            if (indexName == null || indexName.equals(""))
                indexName = "%";

            if (!indexName.equals("%"))
                sqlTotal.append("select count(*) from dm_fzzsqz where zb_mc like '%" + indexName + "%' ");
            else
                sqlTotal.append("select count(*) from dm_fzzsqz ");

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;
            //拼接分页SQL查询
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from dm_fzzsqz where id > (select ISNULL(MAX(id),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_fzzsqz where zb_mc like '%" + indexName + "%' ");

            sqlPaging.append(" order by id ASC )A ");
            sqlPaging.append(") and zb_mc like '%" + indexName + "%' ");
            sqlPaging.append("order by id ASC ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                DevelopIndexWeight temp = objectMapping(rs);
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
     * 实现ORM功能
     *
     * @param rs
     * @return DevelopIndexWeight
     */
    public DevelopIndexWeight objectMapping(ResultSet rs) {
        DevelopIndexWeight d = new DevelopIndexWeight();
        try {
            d.setId(rs.getInt("id"));
            d.setIndexCode(rs.getString("zb_dm"));
            d.setIndexName(rs.getString("zb_mc"));
            d.setIndexWeight(rs.getBigDecimal("zb_qz"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }
}
