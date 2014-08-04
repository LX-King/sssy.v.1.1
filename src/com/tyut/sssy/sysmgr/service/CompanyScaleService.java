package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.domain.CompanyScale;
import com.tyut.sssy.sysmgr.dao.CompanyScaleDao;

import java.util.Map;
import java.util.List;

/**
 * ClassName:CompanyScaleService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-13
 * Time: 9:56:21
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CompanyScaleService {

    private CompanyScaleDao companyScaleDao;

    public CompanyScaleService() {
        this.companyScaleDao = new CompanyScaleDao();
    }

    public boolean add(CompanyScale companyScale) {
        return this.companyScaleDao.add(companyScale);
    }

    public boolean modify(CompanyScale companyScale) {
        return this.companyScaleDao.modify(companyScale);

    }

    public boolean del(String companyScales[]) {
        boolean flag = this.companyScaleDao.del(companyScales);
        return flag;
    }

    public Map<String, Object> queryByCon(String csName, int pageSize, int pageNo) {
        return this.companyScaleDao.queryByCon(csName, pageSize, pageNo);
    }

    public List<CompanyScale> getAllRecords(){
        return this.companyScaleDao.getAllRecords();
    }

    public String getMaxId() {
        return this.companyScaleDao.getMaxId();
    }

    public CompanyScale getById(String csCode) {
        return this.companyScaleDao.getById(csCode);
    }
}
