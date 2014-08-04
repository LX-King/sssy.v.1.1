package com.tyut.sssy.base.dao;

import com.tyut.sssy.base.domain.BudgetLevel;
import com.tyut.sssy.sysmgr.dao.BaseDaoJdbcImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BudgetLevelDao extends BaseDaoJdbcImpl {

    public List<BudgetLevel> queryAll() {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from dm_ysjc_01 ");
        List<BudgetLevel> list = this.queryAll(sql.toString());
        return list;
    }

    public BudgetLevel queryById(String ysjcDm) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from dm_ysjc_01 where ysjc_dm = '"+ysjcDm.trim()+"'");
        BudgetLevel budgetLevel = (BudgetLevel)this.querySingleObject(sql.toString());
        return budgetLevel;
    }

    @Override
    public BudgetLevel objectRelationMapping(ResultSet rs) {
        BudgetLevel budgetLevel = new BudgetLevel();
        try {
            budgetLevel.setYsjcDm(rs.getString("ysjc_dm"));
            budgetLevel.setMc(rs.getString("mc"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgetLevel;
    }
}
