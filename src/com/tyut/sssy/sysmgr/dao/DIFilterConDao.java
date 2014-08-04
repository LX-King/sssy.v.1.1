package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.DIFilterCon;
import com.tyut.sssy.sysmgr.domain.DevelopIndexWeight;
import com.tyut.sssy.sysmgr.domain.IndexInitConfig;
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
 * ClassName:DIFilterCon
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 19:19:14
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DIFilterConDao {

     /*映射的表名*/
    private static String DB_MAPPING = "dm_fzzs_gltj";

    /*映射的表主键*/
    private static String DB_IDENTITY = "id";

    /**
     * 添加 发展指数过滤条件
     *
     * @param filterCon
     * @return Boolean
     */
    public boolean add(DIFilterCon filterCon) {
        Connection conn = null;
        String sql = "insert into dm_fzzs_gltj(zdz,zxz ,zdz_qs ,zxz_qs) values (?, ?, ? , ? )";
        PreparedStatement ps = null;
        boolean flag = true;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, filterCon.getMaxValue());
            ps.setBigDecimal(2, filterCon.getMinValue());
            ps.setBigDecimal(3, filterCon.getMaxValueDef());
            ps.setBigDecimal(4, filterCon.getMinValueDef());
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
     * 修改 发展指数过滤条件
     *
     * @param filterCon
     * @return boolean
     */
    public boolean modify(DIFilterCon filterCon) {
        Connection conn = null;
        String sql = "update dm_fzzs_gltj set zdz = ?,zxz = ? ,zdz_qs = ? ,zxz_qs = ? where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, filterCon.getMaxValue());
            ps.setBigDecimal(2, filterCon.getMinValue());
            ps.setBigDecimal(3, filterCon.getMaxValueDef());
            ps.setBigDecimal(4, filterCon.getMinValueDef());
            ps.setInt(5, filterCon.getId());
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
     * 删除 发展指数过滤条件
     *
     * @param ids
     * @return boolean
     */
    public boolean del(int ids[]) {
        Connection conn = null;
        String sql = "del from dm_fzzs_gltj where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (int id : ids) {
                ps.setInt(1, id);
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
     * 实现ORM
     *
     * @param id
     * @return a
     */
    public DIFilterCon getById(int id) {
        DIFilterCon o = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select *  from " + DB_MAPPING + " where " + DB_IDENTITY + " = ?";
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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
     * 查询 发展指数过滤条件
     *
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<DIFilterCon> list = new ArrayList<DIFilterCon>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            sqlTotal.append("select count(*) from dm_fzzs_gltj ");
            
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
            sqlPaging.append(" * from dm_fzzs_gltj where id > (select ISNULL(MAX(id),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_fzzs_gltj ");

            sqlPaging.append(" order by id ASC )A )");
            sqlPaging.append("order by id ASC ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                DIFilterCon temp = objectMapping(rs);
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
     * @return DIFilterCon
     */
    public DIFilterCon objectMapping(ResultSet rs) {
        DIFilterCon d = new DIFilterCon();
        try {
            d.setId(rs.getInt("id"));
            d.setMaxValue(rs.getBigDecimal("zdz"));
            d.setMinValue(rs.getBigDecimal("zxz"));
            d.setMaxValueDef(rs.getBigDecimal("zdz_qs"));
            d.setMinValueDef(rs.getBigDecimal("zxz_qs"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }
}
