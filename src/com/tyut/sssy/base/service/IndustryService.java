package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.IndustryDao;
import com.tyut.sssy.base.domain.Industry;

public class IndustryService {

	public List<Industry> getIndustryList() {
		IndustryDao industryDao = new IndustryDao();
		List<Industry> industryList = industryDao.getIndustryList();
		return industryList;
	}
	
	public Industry getIndustryById(String cyDm) {
		IndustryDao industryDao = new IndustryDao();
		Industry industry = industryDao.getIndustryById(cyDm);
		return industry;
	}
	
}
