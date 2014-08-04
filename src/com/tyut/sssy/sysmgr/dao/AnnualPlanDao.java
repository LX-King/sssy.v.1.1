package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.AnnualPlan;
import com.tyut.sssy.utils.StringUtil;
import com.tyut.sssy.utils.db.C3P0Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnualPlanDao extends BaseDaoJdbcImpl {


    /**
     * 添加
     *
     * @param annualPlan
     * @return Boolean
     */
    public boolean insert(AnnualPlan annualPlan) {

        //拼装SQL
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";
        sql.append(" insert into ");
        sql.append(tableName);
        sql.append(" (id,nd , fl , fl_mx, ndjh,zjh,swjg_dm ) values(");
        sql.append(annualPlan.getId()+",");
        sql.append("'" + annualPlan.getNd() + "',");
        sql.append("'" + annualPlan.getFl() + "',");
        sql.append("'" + annualPlan.getFlMx() + "',");
        sql.append(annualPlan.getNdjh() + ",");
        sql.append(annualPlan.getZjh() + ",");
        sql.append("'" + annualPlan.getSwjgDm() + "')");

        //执行父类方法
        boolean flag = this.add(sql.toString());

        //返回结果
        return flag;
    }

    public boolean batchInsert(List<AnnualPlan> list) {
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";
        for (AnnualPlan annualPlan : list) {
            sql.append(" insert into ");
            sql.append(tableName);
            sql.append(" (id,nd , fl , fl_mx, ndjh,zjh,swjg_dm ) values(");
            sql.append(annualPlan.getId()+",");
            sql.append("'" + annualPlan.getNd() + "',");
            sql.append("'" + annualPlan.getFl() + "',");
            sql.append("'" + annualPlan.getFlMx() + "',");
            sql.append(annualPlan.getNdjh() + ",");
            sql.append(annualPlan.getZjh() + ",");
            sql.append("'" + annualPlan.getSwjgDm() + "');\n");
        }

        boolean flag = this.add(sql.toString());
        return flag;

    }

    /**
     * 修改
     *
     * @param annualPlan
     * @return Boolean
     */
    public boolean update(AnnualPlan annualPlan) {

        //拼装SQL
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";
        sql.append("update ");
        sql.append(tableName);
        sql.append(" set ");
        sql.append(" nd = '" + annualPlan.getNd() + "',");
        sql.append(" fl = '" + annualPlan.getFl() + "',");
        sql.append(" fl_mx = '" + annualPlan.getFlMx() + "',");
        sql.append(" ndjh = " + annualPlan.getNdjh() + ",");
        sql.append(" zjh = " + annualPlan.getZjh() + ",");
        sql.append(" swjg_dm = '" + annualPlan.getSwjgDm() + "' ");
        sql.append(" where id = " + annualPlan.getId());

        boolean flag = this.modify(sql.toString());

        return flag;
    }


    /**
     * 删除
     *
     * @param annualPlan
     * @return Boolean
     */
    public boolean delete(AnnualPlan annualPlan) {

        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";

        //拼装SQL
        sql.append("delete from ");
        sql.append(tableName);
        sql.append(" where id = " + annualPlan.getId());

        boolean flag = this.del(sql.toString());

        return flag;
    }

    public boolean delByYear(String year) {
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";
        sql.append("delete from ");
        sql.append(tableName);
        sql.append(" where nd = '" + year.trim() + "'");
        boolean flag = this.del(sql.toString());
        return flag;
    }

    public List<AnnualPlan> queryByYear(String year) {
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";

        //拼装SQL
        sql.append("select * from ");
        sql.append(tableName);
        sql.append(" where nd like '%" + year + "%'");

        List<AnnualPlan> list = this.queryByListWithCon(sql.toString());
        return list;
    }

    /**
     * 返回所有
     *
     * @return List
     */
    public List<AnnualPlan> queryAll() {
        StringBuffer sql = new StringBuffer();
        String tableName = "jk_ndjh";

        //拼装SQL
        sql.append("select * from ");
        sql.append(tableName);
        sql.append(" order by nd desc");

        List<AnnualPlan> list = this.queryAll(sql.toString());

        return list;

    }

    public AnnualPlan queryBySwjgDmSingle(String swjgDm) {
        String tableName = "jk_ndjh";
        StringBuffer sql = new StringBuffer();

        //拼装SQL
        sql.append("select top 1 * from ");
        sql.append(tableName);
        sql.append(" where swjg_dm = '" + swjgDm + "'");

        AnnualPlan annualPlan = (AnnualPlan) this.querySingleObject(sql.toString());
        return annualPlan;
    }

    public List<AnnualPlan> queryBySwjgDmAll(String swjgDm) {
        String tableName = "jk_ndjh";
        StringBuffer sql = new StringBuffer();

        //拼装SQL
        sql.append("select * from ");
        sql.append(tableName);
        sql.append(" where swjg_dm like '%" + swjgDm + "%'");

        List<AnnualPlan> list = this.queryAll(sql.toString());

        return list;
    }

    public int getMaxId() {
        StringBuffer sql = new StringBuffer();
        sql.append("select max(id) as id from jk_ndjh ");

        return this.queryMaxId(sql.toString());
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return AnnualPlan
     */
    public AnnualPlan queryById(int id) {
        String tableName = "jk_ndjh";
        StringBuffer sql = new StringBuffer();

        //拼装SQL
        sql.append(" select * from ");
        sql.append(tableName);
        sql.append(" where id = " + id);

        AnnualPlan annualPlan = (AnnualPlan) this.querySingleObject(sql.toString());
        return annualPlan;

    }

    /**
     * 按条件查询
     *
     * @param pageSize
     * @param pageNo
     * @param nd
     * @param swjgDm
     * @param fl
     * @return list
     */
    public Map<String, Object> queryByCon(int pageSize, int pageNo, String nd, String swjgDm, String fl) {

        String tableName = "jk_ndjh";
        String identity = "id";
        Connection conn = null;
        StringBuffer sqlTotal = new StringBuffer();
        StringBuffer sqlPaging = new StringBuffer();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, Object> map = new HashMap<String, Object>();
        List<AnnualPlan> list = new ArrayList<AnnualPlan>();

        try {
            // C3P0创建连接
            conn = C3P0Util.getConnection();

            sqlTotal.append(" select count(*) from ");
            sqlTotal.append(tableName);
            sqlTotal.append(" where ");
            if (!StringUtil.isNullOrEmp(nd))
                sqlTotal.append(" nd like '%" + nd + "%'");
            else
                sqlTotal.append(" nd like '%' ");

            if (!StringUtil.isNullOrEmp(fl))
                sqlTotal.append(" and fl like '%" + fl + "%'");
            else
                sqlTotal.append(" and fl like '%' ");

            if (!StringUtil.isNullOrEmp(swjgDm))
                sqlTotal.append(" and swjg_dm like '%" + swjgDm + "%'");
            else
                sqlTotal.append(" and swjg_dm like '%' ");


            String sqlTotalStr = sqlTotal.toString();

            ps = conn.prepareStatement(sqlTotalStr);
            rs = ps.executeQuery();
            int totalProperty = 0;
            if (rs.next())
                totalProperty = rs.getInt(1);
            map.put("totalProperty", totalProperty);

            //拼接分页SQL查询
            sqlPaging.append("select top ");
            sqlPaging.append(pageSize);
            sqlPaging.append(" * from " + tableName + " where " + identity + " > (select ISNULL(MAX(" + identity + "),0)  from (  ");
            sqlPaging.append("select top ");
            sqlPaging.append((pageNo - 1) * pageSize);
            sqlPaging.append(" * from " + tableName + " ");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where")));
            }
            sqlPaging.append("  )as A )");
            if (sqlTotalStr.indexOf("where") != -1) {
                sqlPaging.append(" and ");
                sqlPaging.append(sqlTotalStr.substring(sqlTotalStr.indexOf("where") + 5));
            }
            sqlPaging.append("order by " + identity + " ASC ");
            ps = conn.prepareStatement(sqlPaging.toString());
            rs = ps.executeQuery();

            while (rs.next()) {
                AnnualPlan temp = objectRelationMapping(rs);
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


    @Override
    public AnnualPlan objectRelationMapping(ResultSet rs) {
        AnnualPlan annualPlan = new AnnualPlan();

        try {
            annualPlan.setId(rs.getInt("id"));
            annualPlan.setNd(rs.getString("nd"));
            annualPlan.setFl(rs.getString("fl"));
            annualPlan.setFlMx(rs.getString("fl_mx"));
            annualPlan.setNdjh(rs.getBigDecimal("ndjh"));
            annualPlan.setZjh(rs.getBigDecimal("zjh"));
            annualPlan.setSwjgDm(rs.getString("swjg_dm"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return annualPlan;
    }
}
