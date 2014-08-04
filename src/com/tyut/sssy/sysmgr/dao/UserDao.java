package com.tyut.sssy.sysmgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

	import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.utils.db.C3P0Util;

public class UserDao {


    /**
     * 添加用户
     *
     * @param user 用户对象
     */
    public boolean add(User user) {
        Connection conn = null;
        String sql = "insert into dm_czry(czdm , mc , password , jsdm , yxbz,swjg_dm) values (?, ?, ?, ?, ?,?)";
        PreparedStatement ps = null;
        boolean flag ;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getCode());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRoleCode());
            ps.setString(5, user.getIsExpired());
            ps.setString(6,user.getSwjgdm());

            ps.execute();
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, null, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    /**
     * 根据id删除用户
     *
     * @param userId 用户id
     */
    public void deleteById(Integer userId) {
        Connection conn = null;
        String sql = "delete from dm_czry where id = ?";
        PreparedStatement ps = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, null, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId 用户id
     * @return 用户对象
     */
    public User getById(Integer userId) {
        Connection conn = null;
        String sql = "select * from dm_czry where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, userId);

            rs = ps.executeQuery();

            while (rs.next()) {
                user = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return user;
    }

    /**
     * 根据用户名、密码 查询用户
     *
     * @param code     用户名
     * @param password 密码
     * @return 用户对象
     */
    public User getByCodeAndPwd(String code, String password) {
        Connection conn = null;
        String sql = "select * from dm_czry where czdm = ? and password = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
        	
            ps = conn.prepareStatement(sql);

            ps.setString(1, code);
            ps.setString(2, password);

            rs = ps.executeQuery();
            while (rs.next()) {
                user = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
        	C3P0Util.close(ps, rs, conn);
//        	JdbcUtil.close(ps, rs, conn);
        }

        return user;
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    public List<User> getList() {
        Connection conn = null;
        String sql = "select * from dm_czry where yxbz <> 'n' ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();

        User user = null;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                user = objectMapping(rs);

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    /**
     * 获取用户分页列表
     *
     * @param start
     * @param limit
     * @param dir
     * @return
     */
    public List<User> getPageList(int start, int limit, String dir) {
        Connection conn = null;

        //查询第m条到第n条记录
        /*
          Select top (n-(m-1)) * from [tablename]
          where [parimary key] not in
          (select top (m-1) [parimary key] from [tablename] order by [排序字段及排序方法])
          order by [排序字段及排序方法];
          */

        String sql = "select top " + limit + " * from dm_czry" +
                " where id not in " +
                "(select top " + start + " id from id order by id " + dir + ") " +
                "order by id " + dir;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> list = new ArrayList<User>();

        User user = null;
        try {
            // JDBC 创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                user = objectMapping(rs);

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, rs, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }


    /**
     * 根据用户名跟角色来查询
     *
     * @param name
     * @param roleCode
     * @return Map
     */
    public Map<String, Object> getPageListByCon(int pageNo, int pageSize, String name, String roleCode) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> list = new ArrayList<User>();


        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;
            if (name != null && !name.equals("")) {
                sqlTotal.append("select count(*) from dm_czry where mc like '%" + name + "%' ");
                if (roleCode != null && !roleCode.equals("")) {
                    sqlTotal.append(" and jsdm = " + roleCode);
                }
            }else{
                if(roleCode != null && !roleCode.equals("")){
                    sqlTotal.append("select count(*) from dm_czry where jsdm = "+roleCode);
                }else{
                    sqlTotal.append("select count(*) from dm_czry ");
                }
            }
            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;
            String sqlTotalStr = sqlTotal.toString();
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from dm_czry where id NOT IN (  ");

            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" id from dm_czry ");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where")));
            }
            sqlPaging.append(" order by id asc )");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(" and ");
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where") + 5));
            }
            sqlPaging.append("order by id ASC  ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                User temp = objectMapping(rs);
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
     * 更新用户
     *
     * @param user 用户对象
     */
    public boolean update(User user) {
        Connection conn = null;
        String sql = "update dm_czry set mc = ?, password = ?, czdm = ?, jsdm = ?, yxbz = ?, swjg_dm = ? where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            // JDBC创建连接
//			conn = JdbcUtil.getConnection();

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);


            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getCode());
            ps.setString(4, user.getRoleCode());
            ps.setString(5, user.getIsExpired());
            ps.setString(6, user.getSwjgdm());
            ps.setInt(7, user.getId());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            // JDBC关闭连接
//			JdbcUtil.close(ps, null, conn);

            // C3P0关闭连接
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }


    public User objectMapping(ResultSet rs) {
        User user = null;
        try {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setCode(rs.getString("czdm"));
            user.setName(rs.getString("mc"));
            user.setRoleCode(rs.getString("jsdm"));
            user.setPassword(rs.getString("password"));
            user.setIsExpired(rs.getString("yxbz"));
            user.setSwjgdm(rs.getString("swjg_dm"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

	public User getByCode(String code) {
		Connection conn = null;
        String sql = "select * from dm_czry where czdm = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = objectMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return user;
	}
}