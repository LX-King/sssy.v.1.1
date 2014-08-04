package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.AnalysisIndexDao;
import com.tyut.sssy.base.domain.AnalysisIndex;

public class AnalysisIndexService {

	public List<AnalysisIndex> getAnalysisIndexList() {
		AnalysisIndexDao analysisIndexDao = new AnalysisIndexDao();
		List<AnalysisIndex> analysisIndexList = analysisIndexDao.getAnalysisIndexList();
		return analysisIndexList;
	}
	
	public AnalysisIndex getAnalysisIndexById(String fxzbDm) {
		AnalysisIndexDao analysisIndexDao = new AnalysisIndexDao();
		AnalysisIndex analysisIndex = analysisIndexDao.getAnalysisIndexById(fxzbDm);
		return analysisIndex;
	}
	
}
