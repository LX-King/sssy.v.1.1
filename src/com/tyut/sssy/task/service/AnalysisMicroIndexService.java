package com.tyut.sssy.task.service;

import java.util.List;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.dao.AnalysisMicroIndexDao;
import com.tyut.sssy.task.domain.AnalysisMicroIndex;


public class AnalysisMicroIndexService {

	/**
	 * 按纳税人识别号和分析期获取列表
	 * @param taxPayerCode
	 * @param dataCalcParameter
	 * @return
	 */
	public List<AnalysisMicroIndex> getListByTaxPayerCodeAndFxq(
			String taxPayerCode, DataCalcParameter dataCalcParameter) {
		AnalysisMicroIndexDao analysisMicroIndexDao = new AnalysisMicroIndexDao();
		List<AnalysisMicroIndex> analysisMicroIndexList = analysisMicroIndexDao.getListByTaxPayerCodeAndFxq(taxPayerCode, dataCalcParameter);
		return analysisMicroIndexList;
	}

	/**
	 * 根据参数获得分析微观指标
	 * @param nsrsbh
	 * @param fxqNd
	 * @param fxqSjq
	 * @param fxqSjz
	 * @param fxzbDm
	 * @return
	 */
	public AnalysisMicroIndex getForProcess(String nsrsbh, String fxqNd,
			String fxqSjq, String fxqSjz, String fxzbDm) {
		AnalysisMicroIndexDao analysisMicroIndexDao = new AnalysisMicroIndexDao();
		AnalysisMicroIndex analysisMicroIndex = analysisMicroIndexDao.getForProcess(nsrsbh, fxqNd, fxqSjq, fxqSjz, fxzbDm);
		return analysisMicroIndex;
	}


}
