package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.sysmgr.dao.AnnualPlanDao;
import com.tyut.sssy.sysmgr.domain.AnnualPlan;

import java.util.List;

public class AnnualPlanService {

    private AnnualPlanDao annualPlanDao = null;

    public AnnualPlanService() {
        this.annualPlanDao = new AnnualPlanDao();
    }

   public int getMaxId(){
       return this.annualPlanDao.getMaxId();
   }
    public boolean insert(AnnualPlan annualPlan) {
        return annualPlanDao.insert(annualPlan);
    }

    /**
     * 批量插入
     * @param list
     * @return Boolean
     */
    public boolean batchInsert(List<AnnualPlan> list){
        return this.annualPlanDao.batchInsert(list);
    }

    public boolean modify(AnnualPlan annualPlan) {
        return annualPlanDao.update(annualPlan);
    }

    public boolean del(AnnualPlan annualPlan) {
        return annualPlanDao.delete(annualPlan);
    }

    public boolean delByYear(String year){
        return annualPlanDao.delByYear(year);
    }
    public List<AnnualPlan> queryAll() {
        TaxUnitService taxUnitService = new TaxUnitService();
        List<AnnualPlan> list = annualPlanDao.queryAll();
        for (AnnualPlan annualPlan : list) {
            annualPlan.setTaxUnit(taxUnitService.getTaxUnitById(annualPlan.getSwjgDm()));
        }

        return list;
    }


    public List<AnnualPlan> queryByYear(String year) {
        TaxUnitService taxUnitService = new TaxUnitService();
        List<AnnualPlan> list = annualPlanDao.queryByYear(year);
        for (AnnualPlan annualPlan : list) {
            annualPlan.setTaxUnit(taxUnitService.getTaxUnitById(annualPlan.getSwjgDm()));
        }

        return list;
    }


    public AnnualPlan queryById(int id) {
        AnnualPlan annualPlan = annualPlanDao.queryById(id);
        TaxUnitService taxUnitService = new TaxUnitService();
        annualPlan.setTaxUnit(taxUnitService.getTaxUnitById(annualPlan.getSwjgDm()));
        return annualPlan;
    }
}
