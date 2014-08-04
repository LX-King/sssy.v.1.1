package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.FirmRegTypeDao;
import com.tyut.sssy.base.domain.FirmRegType;

public class FirmRegTypeService {

	public List<FirmRegType> getFirmRegTypeList() {
		FirmRegTypeDao firmRegTypeDao = new FirmRegTypeDao();
		List<FirmRegType> firmRegTypeList = firmRegTypeDao.getFirmRegTypeList();
		return firmRegTypeList;
	}
	
	public FirmRegType getFirmRegTypeById(String qyzclxDm) {
		FirmRegTypeDao firmRegTypeDao = new FirmRegTypeDao();
		FirmRegType firmRegType = firmRegTypeDao.getFirmRegTypeById(qyzclxDm);
		return firmRegType;
	}
}
