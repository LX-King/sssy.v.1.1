package com.tyut.sssy.base.service;

import com.tyut.sssy.base.dao.TaxUnitDao;
import com.tyut.sssy.base.domain.TaxUnit;

import java.util.List;

public class TaxUnitService {

    /**
     * Server层调用返回所有子级税务机关
     * @param sjswjgDm
     * @return 税务机关列表
     */
    public List<TaxUnit> getTaxUnitListByLevel(String sjswjgDm){
        TaxUnitDao dao = new TaxUnitDao();
        if(sjswjgDm == null || sjswjgDm.trim().equals("")) return null;
        else{
            List<TaxUnit> list = dao.getTaxUnitListByLevel(sjswjgDm);
            return list;
        }
    }

	/**
	 * 获得管理机关列表
	 * @return
	 */
	public List<TaxUnit> getTaxUnitList() {
		
		TaxUnitDao taxUnitDao = new TaxUnitDao();
		List<TaxUnit> taxUnitList = taxUnitDao.getTaxUnitList();
		return taxUnitList;
	}

	/**
	 * 根据机关代码获取税务机关
	 * @param swjgDm
	 * @return
	 */
	public TaxUnit getTaxUnitById(String swjgDm) {
		TaxUnitDao taxUnitDao = new TaxUnitDao();
		TaxUnit taxUnit = taxUnitDao.getTaxUnitById(swjgDm);
		return taxUnit;
	}

	/**
	 * 根据单位类型查找单位
	 * @param unitType
	 * @return
	 */
	public List<TaxUnit> getTaxUnitListByType(String unitType) {
		TaxUnitDao taxUnitDao = new TaxUnitDao();
		List<TaxUnit> taxUnitList = taxUnitDao.getTaxUnitListByType(unitType);
		return taxUnitList;
	}

}
