package com.tyut.sssy.base.service;

import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.base.dao.TaxPayerDao;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;

public class TaxPayerService {

	/**
	 * 根据税务机关代码取纳税人列表
	 * @param swjgDm
	 * @return
	 */
	public List<TaxPayer> getTaxPayerListByUnit(String swjgDm) {
		
		TaxPayerDao taxPayerDao = new TaxPayerDao();
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
		
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgDm);
		if (taxUnit.getGljgbz().equals("H")) {
			// 如果是管理机关
			taxPayerList = taxPayerDao.getTaxPayerList();
		} else {
			// 不是管理机关
			taxPayerList = taxPayerDao.getTaxPayerListByUnit(swjgDm);
		}
		
		if (taxPayerList != null) {
			for (TaxPayer taxPayer : taxPayerList) {
				taxPayer.setTaxUnit(taxUnitService.getTaxUnitById(taxPayer.getSwjgDm()));
			}
		}
		
		return taxPayerList;
	}
	
	/**
	 * 根据税务机关代码取纳税人列表（涉及待解欠缴）
	 * @param swjgDm
	 * @return
	 */
	public List<TaxPayer> getDjTaxPayerListByUnit(String swjgDm) {
		
		TaxPayerDao taxPayerDao = new TaxPayerDao();
		TaxUnitService taxUnitService = new TaxUnitService();
		List<TaxPayer> taxPayerList = new ArrayList<TaxPayer>();
		List<TaxPayer> dJtaxPayerList = new ArrayList<TaxPayer>();
		
		TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgDm);
		if (taxUnit.getGljgbz().equals("H")) {
			// 如果是管理机关
			taxPayerList = taxPayerDao.getTaxPayerList();
			dJtaxPayerList = taxPayerDao.getDjTaxPayerList();
			for (TaxPayer dJtaxPayer : dJtaxPayerList) {
				taxPayerList.add(dJtaxPayer);
			}
		} else {
			// 不是管理机关
			taxPayerList = taxPayerDao.getTaxPayerListByUnit(swjgDm);
			dJtaxPayerList = taxPayerDao.getDjTaxPayerListByUnit(swjgDm);
			for (TaxPayer dJtaxPayer : dJtaxPayerList) {
				taxPayerList.add(dJtaxPayer);
			}
		}
		
		if (taxPayerList != null) {
			for (TaxPayer taxPayer : taxPayerList) {
				taxPayer.setTaxUnit(taxUnitService.getTaxUnitById(taxPayer.getSwjgDm()));
			}
		}
		
		return taxPayerList;
	}

	/**
	 * 根据纳税人编号查询纳税人
	 * @param swjgDm
	 * @return
	 */
	public TaxPayer getTaxPayerByCode(String nsrsbh) {
		TaxPayerDao taxPayerDao = new TaxPayerDao();
		TaxPayer taxPayer = taxPayerDao.getTaxPayerByCode(nsrsbh);
		return taxPayer;
	}
	
}
