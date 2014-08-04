package com.tyut.sssy.task.service;

import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.task.dao.AnalysisMacroIndexDao;
import com.tyut.sssy.task.domain.AnalysisMacroIndex;

import java.util.List;

public class AnalysisMacroIndexService {

	public List<AnalysisMacroIndex> getListByFxqAndFxzblxMx(
			DataCalcParameter dataCalcParameter, String fxzblxMx) {
		AnalysisMacroIndexDao analysisMacroIndexDao = new AnalysisMacroIndexDao();
		List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexDao.getListByFxqAndFxzblxMx(dataCalcParameter, fxzblxMx);
		return analysisMacroIndexList;
	}

	/**
	 * 根据分析期，分析指标类型明细，税务机关代码获得指标列表
	 * @param dataCalcParameter
	 * @param fxzblxMx
	 * @return
	 */
	public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndSwjgDm(
			DataCalcParameter dataCalcParameter, String fxzblxMx, String swjgDm) {
		AnalysisMacroIndexDao analysisMacroIndexDao = new AnalysisMacroIndexDao();
		List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexDao.getListByFxqAndFxzblxMxAndSwjgDm(dataCalcParameter, fxzblxMx, swjgDm);
		return analysisMacroIndexList;
	}

	/**
	 * 根据分析期，分析指标类型明细，行业大类代码 获得指标列表
	 * @param dataCalcParameter
	 * @param fxzblxMx
	 * @return
	 */
	public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndHydlDm(
			DataCalcParameter dataCalcParameter, String fxzblxMx, String hydlDm) {
		AnalysisMacroIndexDao analysisMacroIndexDao = new AnalysisMacroIndexDao();
		List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexDao.getListByFxqAndFxzblxMxAndHydlDm(dataCalcParameter, fxzblxMx, hydlDm);
		return analysisMacroIndexList;
	}

	/**
	 * 根据分析期，分析指标类型明细，注册类型代码 获得指标列表
	 * @param dataCalcParameter
	 * @param fxzblxMx
	 * @return
	 */
	public List<AnalysisMacroIndex> getListByFxqAndFxzblxMxAndQyzclxDm(
			DataCalcParameter dataCalcParameter, String fxzblxMx,
			String qyzclxDm) {
		AnalysisMacroIndexDao analysisMacroIndexDao = new AnalysisMacroIndexDao();
		List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexDao.getListByFxqAndFxzblxMxAndQyzclxDm(dataCalcParameter, fxzblxMx, qyzclxDm);
		return analysisMacroIndexList;
	}

    public AnalysisMacroIndex getForProcess(String fxqNd, String fxqSjq, String fxqSjz, String fxzbDm,String tzqj,String fxzblx , String fxzblxMxDm , String zgswjgdm) {
        AnalysisMacroIndexDao analysisMacroIndexDao = new AnalysisMacroIndexDao();
        AnalysisMacroIndex analysisMacroIndex = analysisMacroIndexDao.getForProcess(fxqNd,fxqSjq,fxqSjz,fxzbDm,tzqj,fxzblx , fxzblxMxDm , zgswjgdm);
        return analysisMacroIndex;
    }
}
