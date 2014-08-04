package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:CategoryDao
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-3-21
 * Time: 10:41:47
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CategoryDao {


    /**
     * 添加 一个 菜单
     *
     * @param category
     */
    public boolean add(Category category) {
        Connection conn = null;
        String sql = "insert into dm_cd(text,icon , url ,href,href_target,is_leaf,ms,parent_id , id,show_order ) values (?, ?, ? , ? ,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        boolean flag = true;

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, category.getText());
            ps.setString(2, category.getIcon());
            ps.setString(3, category.getUrl());
            ps.setString(4, category.getHref());
            ps.setString(5, category.getHrefTarget());
            ps.setString(6, category.getLeaf());
            ps.setString(7, category.getDesc());
            ps.setInt(8, category.getParentId());
            ps.setInt(9, category.getId());
            ps.setInt(10, category.getOrder());

            ps.execute();
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
     * 根据ID 查询
     *
     * @param id
     */
    public Category getById(int id) {

        Connection conn = null;
        String sql = "select * from dm_cd where id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        Category category = null;
        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                category = objectMapping(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // C3P0关闭连接
            C3P0Util.close(ps, rs, conn);
        }

        return category;

    }

    /**
     * 修改 菜单
     *
     * @param category
     */
    public boolean modify(Category category) {
        Connection conn = null;
        String sql = "update dm_cd set text = ?, icon = ?, url = ?, href = ?, href_target = ? , is_leaf = ? , ms = ? , parent_id = ?,show_order = ? where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, category.getText());
            ps.setString(2, category.getIcon());
            ps.setString(3, category.getUrl());
            ps.setString(4, category.getHref());
            ps.setString(5, category.getHrefTarget());
            ps.setString(6, category.getLeaf());
            ps.setString(7, category.getDesc());
            ps.setInt(8, category.getParentId());
            ps.setInt(9, category.getId());
            ps.setInt(10, category.getOrder());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return flag;
    }

    public boolean del(int idArrs[]) {
        Connection conn = null;
        String sql = "delete from dm_cd where id = ?";
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            for (int id : idArrs) {
                ps.setInt(1, id);
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

    public List<Category> getByParentId(int parentId) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Category> list = new ArrayList<Category>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            if(parentId != 6)
            sql.append("select * from dm_cd where parent_id = ? order by show_order");
            else
                sql.append("select * from dm_cd where parent_id = ? order by is_leaf desc, show_order");

            ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, parentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Category temp = objectMapping(rs);
                list.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }

    /**
     * 查询所有 菜单，返回List
     *
     * @param ids
     * @return
     */
    public List<Category> getListByIds(int[] ids) {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Category> list = new ArrayList<Category>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            if (ids != null && ids.length != 0) {
                sql.append("select * from dm_cd where id in ( ");
                for (int i : ids) {
                    sql.append(i);
                    sql.append(",");
                }
                sql.deleteCharAt(sql.lastIndexOf(","));
                sql.append(") ");
                sql.append("order by  id");
            } else {
                sql.append("select * from dm_cd ");
            }

            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Category temp = objectMapping(rs);
                list.add(temp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }

        return list;
    }


    public Map<String, Object> getPagingList(int pageNo, int pageSize, String dir, HashMap<String, String> conditions) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list = new ArrayList<Category>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;
            int len = conditions.size();

            //拼接查询总数的SQL
            sqlTotal.append("select count(*) from dm_cd ");
            if (len > i) {
                sqlTotal.append("where ");
                for (Map.Entry<String, String> entry : conditions.entrySet()) {

                    sqlTotal.append(entry.getKey());
                    sqlTotal.append(" like ");
                    sqlTotal.append("%" + entry.getValue() + "%");
                    i++;
                    if (len > i) {
                        sqlTotal.append(" and ");
                    }

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
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from dm_cd where id > (select ISNULL(MAX(id),0)  from (  ");

            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_cd ");
            if (len > i) {
                sqlPaging.append(" where ");
                for (Map.Entry<String, String> entry : conditions.entrySet()) {

                    sqlPaging.append(entry.getKey());
                    sqlPaging.append(" like ");
                    sqlPaging.append("%" + entry.getValue() + "%");
                    i++;
                    if (len > i) {
                        sqlPaging.append(" and ");
                    }

                }
            }
            sqlPaging.append(" order by id " + dir + ")A ");
            sqlPaging.append(") ");
            sqlPaging.append("order by id " + dir);


            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Category temp = objectMapping(rs);
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
     * 返回所有的父节点
     *
     * @param pageSize
     * @param pageNo
     * @return
     */
    public Map<String, Object> getAllParentCategory(int pageSize, int pageNo) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list = new ArrayList<Category>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            //拼接查询总数的SQL
            sqlTotal.append("select count(*) from dm_cd where is_leaf = 'n' ");

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
            sqlPaging.append(" * from dm_cd where id > (select ISNULL(MAX(id),0)  from (  ");

            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from dm_cd where is_leaf = 'n' ");

            sqlPaging.append(" order by id ASC )A ");
            sqlPaging.append(") and is_leaf = 'n' ");
            sqlPaging.append("order by id ASC ");

            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                Category temp = objectMapping(rs);
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
     * @param pageSize
     * @param pageNo
     * @param menuName
     * @param type
     * @param parentId
     * @return Map
     */
    public Map<String, Object> getCategoryListByCon(int pageSize, int pageNo, String menuName, String type, Integer parentId) {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list = new ArrayList<Category>();

        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            //拼接查询总数的SQL
            if (parentId != null) {
                sqlTotal.append("select count(*) from dm_cd where parent_id = '" + parentId + "'");
                if (type != null && !type.equals("")) {
                    sqlTotal.append(" and is_leaf = '" + type + "'");
                } else if (menuName != null && !menuName.equals("")) {
                    sqlTotal.append(" and text like '%" + menuName + "%'");
                }
            } else {
                if (type != null && !type.equals("")) {
                    sqlTotal.append(" select count(*) from dm_cd where is_leaf = '" + type + "'");
                    if (menuName != null && !menuName.equals("")) {
                        sqlTotal.append(" and text like'%" + menuName + "%'");
                    }
                } else {

                    if (menuName != null && !menuName.equals("")) {
                        sqlTotal.append(" select count(*) from dm_cd where text like'%" + menuName + "%'");
                    } else {
                        sqlTotal.append(" select count(*) from dm_cd");
                    }
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
            sqlPaging.append(" * from dm_cd where id NOT IN (  ");

            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" id from dm_cd ");
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
                Category temp = objectMapping(rs);
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
     * 返回所有的菜单
     *
     * @return map
     */
    public Map<String, Object> getAllCategorys() {
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list = new ArrayList<Category>();
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            int i = 0;

            sqlTotal.append(" select count(*) from dm_cd");

            ps = conn.prepareStatement(sqlTotal.toString());
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);
            i = 0;

            //拼接分页SQL查询
            ps = conn.prepareStatement("select * from dm_cd");
            rs = ps.executeQuery();

            while (rs.next()) {
                Category temp = objectMapping(rs);
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
     * 返回最大的ID
     *
     * @return
     */
    public int getMaxId() {
        Connection conn = null;
        StringBuffer sql = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {

            // C3P0创建连接
            conn = C3P0Util.getConnection();
            sql.append("select Max(id) from dm_cd ");

            ps = conn.prepareStatement(sql.toString());
            rs = ps.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt(1);
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
     * @return
     */
    public Category objectMapping(ResultSet rs) {
        Category category = new Category();
        try {
            category.setId(rs.getInt("id"));
            category.setText(rs.getString("text"));
            category.setIcon(rs.getString("icon"));
            category.setUrl(rs.getString("url"));
            category.setHref(rs.getString("Href"));
            category.setHrefTarget(rs.getString("href_target"));
            category.setLeaf(rs.getString("is_leaf"));
            category.setDesc(rs.getString("ms"));
            category.setParentId(rs.getInt("parent_id"));
            category.setOrder(rs.getInt("show_order"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}