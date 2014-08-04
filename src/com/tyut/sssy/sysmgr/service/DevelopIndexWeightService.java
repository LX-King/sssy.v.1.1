package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.domain.DevelopIndexWeight;
import com.tyut.sssy.sysmgr.dao.DevelopIndexWeightDao;

import java.util.Map;

/**
 * ClassName:CompanyScaleService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-13
 * Time: 9:56:21
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DevelopIndexWeightService {

    private DevelopIndexWeightDao developIndexWeightDao;

    public DevelopIndexWeightService() {
        this.developIndexWeightDao = new DevelopIndexWeightDao();
    }

    public boolean add(DevelopIndexWeight developIndexWeight) {
        return this.developIndexWeightDao.add(developIndexWeight);
    }

    public boolean modify(DevelopIndexWeight developIndexWeight) {
        return this.developIndexWeightDao.modify(developIndexWeight);

    }

    public boolean del(int ids[]) {
        boolean flag = this.developIndexWeightDao.del(ids);
        return flag;
    }

    public Map<String, Object> queryByCon(String indexName, int pageSize, int pageNo) {
        return this.developIndexWeightDao.queryByCon(indexName, pageSize, pageNo);
    }


    public DevelopIndexWeight getById(int id) {
        return this.developIndexWeightDao.getById(id);
    }
}