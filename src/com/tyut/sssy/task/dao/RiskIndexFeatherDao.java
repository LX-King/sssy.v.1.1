package com.tyut.sssy.task.dao;

import com.tyut.sssy.task.domain.RiskIndexFeather;
import com.tyut.sssy.utils.db.BaseDaoJdbcImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 项目名称：sssyrisk
 * 类名称：${CLASS}
 * 类描述：微观任务类
 * 创建人：刘翔
 * 创建时间：
 * 修改人：刘翔
 * 修改时间：13-7-11
 * 修改备注：
 */
public class RiskIndexFeatherDao extends BaseDaoJdbcImpl {

    @Override
    public Object objectRelationMapping(ResultSet rs) {
        RiskIndexFeather riskIndexFeather = new RiskIndexFeather();
        try{
            riskIndexFeather.setIndex(rs.getString("zb"));
            riskIndexFeather.setTz1(rs.getString("tz1"));
            riskIndexFeather.setTz2(rs.getString("tz2"));
            riskIndexFeather.setTz3(rs.getString("tz3"));
            riskIndexFeather.setTz4(rs.getString("tz4"));
            riskIndexFeather.setTz5(rs.getString("tz5"));
            riskIndexFeather.setTz6(rs.getString("tz6"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return riskIndexFeather;
    }

    public RiskIndexFeather getRiskIndexFeatherById(String zb){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from dm_tzms ");
        if(zb != null && !zb.equals(""))
            sql.append("where ltrim(rtrim(zb)) = '"+zb.trim()+"'");
        RiskIndexFeather riskIndexFeather  = (RiskIndexFeather)querySingleObject(sql.toString());
        return riskIndexFeather;
    }
}
