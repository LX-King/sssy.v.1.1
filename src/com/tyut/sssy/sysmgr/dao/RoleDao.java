package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.Role;
import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * ClassName:RoleDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-3-21
 * Time: 10:58:45
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class RoleDao {


    /**
     * 添加
     *
     * @param role
     */
    public boolean add(Role role) {
        Connection conn = null;
        String sql = "insert into dm_js(mc ,cdid,jsdm) values (?, ?, ?)";
        PreparedStatement ps = null;
        boolean flag;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getCategorys());
            ps.setString(3, role.getRoleCode());

            ps.execute();
            flag = true;
        } catch (
                SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }

        return flag;
    }

    /**
     * 返回所有的角色
     *
     * @return Map<String , Object>
     */

    public Map<String, Object> getAllRoles() {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> list = new ArrayList<Role>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            //拼接查询总数的SQL
            sqlTotal.append("select count(*) from dm_js  ");

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;

            //拼接分页SQL查询
            sqlPaging.append("select  * from dm_js order by jsdm ");


            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Role temp = objectMapping(rs);
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


    public Map<String, Object> getRoleListByCon(int pageSize, int pageNo, String roleName, String menuIds[]) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> list = new ArrayList<Role>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            //拼接查询总数的SQL
            if (roleName != null) {
                sqlTotal.append("select count(*) from dm_js where mc like '%" + roleName + "%'");
                if (menuIds != null && menuIds.length != 0) {
                    sqlTotal.append(" and cdid in( ");
                    for (String j : menuIds) {
                        sqlTotal.append("'" + j + "'");
                        sqlTotal.append(",");
                    }
                    sqlTotal.deleteCharAt(sqlTotal.lastIndexOf(","));
                    sqlTotal.append(") ");
                }
            } else {
                if (menuIds != null && menuIds.length != 0) {
                    sqlTotal.append("select count(*) from dm_js where ");
                    sqlTotal.append(" cdid in( ");
                    for (String j : menuIds) {
                        sqlTotal.append("'" + j + "'");
                        sqlTotal.append(",");
                    }
                    sqlTotal.deleteCharAt(sqlTotal.lastIndexOf(","));
                    sqlTotal.append(") ");
                } else {
                    sqlTotal.append("select count(*) from dm_js");
                }
            }

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;
            //拼接分页SQL查询
            String sqlTotalStr = sqlTotal.toString();
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from dm_js where jsdm NOT IN (  ");

            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" jsdm from dm_js ");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where")));
            }
            sqlPaging.append(" order by jsdm asc )");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(" and ");
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where") + 5));
            }
            sqlPaging.append("order by jsdm ASC  ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Role temp = objectMapping(rs);
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
     * 通过角色-功能表主键获取该类
     *
     * @param id
     */
    public Role getRoleById(String id) {
        Connection conn = null;
        String sql = "select * from dm_js where jsdm = ?";
        PreparedStatement ps = null;
        Role role = null;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                role = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return role;
    }

    /**
     * 修改Role
     *
     * @param role
     * @return flag
     */
    public boolean modify(Role role) {
        Connection conn = null;
        String sql = "update dm_js  set mc = ? ,cdid = ? where jsdm = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        ResultSet rs = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, role.getRoleName());
            ps.setString(2, role.getCategorys());
            ps.setString(3  , role.getRoleCode());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return flag;
    }


    public boolean del(String idArrs[]) {
        Connection conn = null;
        String sql = "delete from dm_js where jsdm = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (String id : idArrs) {
                ps.setString(1, id);
                ps.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return flag;
    }


    /**
     * OR-MAPPING 将返回的RS封装成类
     *
     * @param rs
     * @return
     */
    public Role objectMapping(ResultSet rs) {
        Role rac = new Role();
        try {
            rac.setRoleCode(rs.getString("jsdm"));
            rac.setRoleName(rs.getString("mc"));
            rac.setCategorys(rs.getString("cdid"));
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return rac;
    }

    public String getMaxId() {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String maxId = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            sql.append("select Max(jsdm) from dm_js ");

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

        String prefix = "1";
        String temp = prefix + maxId;
        Integer num = Integer.valueOf(temp);
        num += 1;
        return num.toString().substring(1);

        
    }


}