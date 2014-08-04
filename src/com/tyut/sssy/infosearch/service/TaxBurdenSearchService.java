package com.tyut.sssy.infosearch.service;

import java.util.List;

import com.tyut.sssy.infosearch.dao.TaxBurdenSearchDao;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTable;
import com.tyut.sssy.taxburden.domain.TaxBurdenAnalysisSearchTableParameter;

public class TaxBurdenSearchService {

	/**
	 * 税负分析查询表
	 * @param taxBurdenAnalysisSearchTableParameter
	 * @return
	 */
	public List<TaxBurdenAnalysisSearchTable> getTaxBurdenAnalysisSearchTable(
			TaxBurdenAnalysisSearchTableParameter taxBurdenAnalysisSearchTableParameter) {
		
		TaxBurdenSearchDao burdenSearchDao = new TaxBurdenSearchDao();
		List<TaxBurdenAnalysisSearchTable> tableList = burdenSearchDao.getTaxBurdenAnalysisSearchTable(taxBurdenAnalysisSearchTableParameter);
		return tableList;
	}

}
