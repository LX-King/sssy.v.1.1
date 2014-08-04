package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.BigIndustryDao;
import com.tyut.sssy.base.domain.BigIndustry;

public class BigIndustryService {

	/**
	 * 行业大类列表
	 * @return
	 */
	public List<BigIndustry> getBigIndustryList() {
		
		BigIndustryDao bigIndustryDao = new BigIndustryDao();
		List<BigIndustry> bigIndustryList = bigIndustryDao.getBigIndustryList();
		
		return bigIndustryList;
	}

	public BigIndustry getBigIndustryById(String hy) {
		BigIndustryDao bigIndustryDao = new BigIndustryDao();
		BigIndustry bigIndustry = bigIndustryDao.getBigIndustryById(hy);
		return bigIndustry;
	}

	/**
	 * 获取主要行业
	 * @return
	 */
	public List<BigIndustry> getMainIndustryList() {
		BigIndustryDao bigIndustryDao = new BigIndustryDao();
		List<BigIndustry> bigIndustryList = bigIndustryDao.getMainIndustryList();
		
		return bigIndustryList;
	}
	
}
