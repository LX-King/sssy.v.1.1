package com.tyut.sssy.sysmgr.dao;


import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoJdbcImpl<T> {

    /**
     * 添加操作
     *
     * @param sql
     * @return Boolean
     * @throws java.sql.SQLException
     */
    public boolean add(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean flag = true;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return flag;
    }

    /**
     * 修改
     *
     * @param sql
     * @return Boolean
     */
    public boolean modify(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean flag = true;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            flag = false;
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return flag;
    }

    /**
     * 返回单一个体
     *
     * @param sql
     * @return
     */
    public T querySingleObject(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T t = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = objectRelationMapping(rs);
            }
            queryHook(t);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return t;
    }

    public List<T> queryByListWithCon(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        T t = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = objectRelationMapping(rs);
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return list;
    }

    /**
     * 返回单一个体
     *
     * @param sql
     * @return
     */
    public T querySingleObjectNonHook(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T t = null;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                t = objectRelationMapping(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, null, conn);
        }

        return t;
    }


    /**
     * 返回List所有结果
     *
     * @param sql
     * @return list
     */
    public List<T> queryAll(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                T o = objectRelationMapping(rs);
                list.add(o);
            }
            for (T t : list) {
                queryHook(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return list;
    }

    /**
     * 批量删除
     *
     * @param sql
     * @return boolean
     */
    public boolean del(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean flag = true;

        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } finally {
            C3P0Util.close(ps, null, conn);
        }
        return flag;
    }

    public int queryMaxId(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int maxId = 0;
        try {
            conn = C3P0Util.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
                maxId = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            C3P0Util.close(ps, rs, conn);
        }
        return maxId;
    }




    /**
     * 实现映射
     *
     * @param rs
     * @return object
     */
    public abstract T objectRelationMapping(ResultSet rs);


    public void queryHook(T t) {
        //do nothing
    }
}
