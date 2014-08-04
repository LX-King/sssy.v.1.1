package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.GCZHBDao;
import com.tyut.sssy.sysmgr.domain.GCZHB;

import java.util.List;

/**
 * ClassName:GCZHBService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-31
 * Time: 16:30:08
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class GCZHBService {

    private GCZHBDao dao  = null ;


    public GCZHBService(){
        dao = new GCZHBDao();
    }


    /**
     * 返回所有的记录
     * @return list
     */
    public List<GCZHB> getAllRecords(){
        return this.dao.getAllRecords();
    }


    /**
     * 根据年度返回记录
     * @param year
     * @return list
     */
    public List<GCZHB> getRecordsbyTime(String year , String seasonBegin, String seasonEnd ){
        return this.dao.getRecordsbyTime(year , seasonBegin, seasonEnd);
    }

    /**
     * 修改
     * @param o
     * @return
     */
    public boolean modify(GCZHB o){
         return this.dao.modify(o);
    }

}
