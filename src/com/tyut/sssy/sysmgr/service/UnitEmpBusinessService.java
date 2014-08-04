package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.domain.UnitEmpBusiness;
import com.tyut.sssy.sysmgr.dao.UnitEmpBusinessDao;

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
public class UnitEmpBusinessService {

    private UnitEmpBusinessDao unitEmpBusinessDao;

    public UnitEmpBusinessService() {
        this.unitEmpBusinessDao = new UnitEmpBusinessDao();
    }

    public boolean add(UnitEmpBusiness unitEmpBusiness) {
        return this.unitEmpBusinessDao.add(unitEmpBusiness);
    }

    public boolean modify(UnitEmpBusiness unitEmpBusiness) {
        return this.unitEmpBusinessDao.modify(unitEmpBusiness);

    }

    public boolean del(String unitEmpBusinesses[]) {
        boolean flag;
        flag = this.unitEmpBusinessDao.del(unitEmpBusinesses);
        return flag;
    }

    public Map<String, Object> queryByCon(String ebName, int pageSize, int pageNo) {
        return this.unitEmpBusinessDao.queryByCon(ebName, pageSize, pageNo);
    }

    public String getMaxId() {
        return this.unitEmpBusinessDao.getMaxId();
    }

    public UnitEmpBusiness getById(String ebCode) {
        return this.unitEmpBusinessDao.getById(ebCode);
    }
}