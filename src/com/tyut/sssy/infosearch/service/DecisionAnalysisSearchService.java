package com.tyut.sssy.infosearch.service;

import java.util.List;

import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.IndustryService;
import com.tyut.sssy.base.service.TaxTypeService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxBurdenSubIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionStructureAnalysisTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxCollectionSubIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxGrowTaxResourceDevelopIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceAllIndexTableParameter;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceSubIndexTable;
import com.tyut.sssy.decisionanalysis.domain.TaxResourceSubIndexTableParameter;
import com.tyut.sssy.infosearch.dao.DecisionAnalysisSearchDao;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：DecisionAnalysisSearchService  
 * 类描述：决策分析类查询  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午09:36:50  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午09:36:50  
 * 修改备注：  
 * @version 
 *
 */
public class DecisionAnalysisSearchService {

	/**
	 * 税收结构分析表
	 * @param taxCollectionStructureAnalysisTableParameter
	 * @return
	 */
	public List<TaxCollectionStructureAnalysisTable> getTaxCollectionStructureAnalysisTable(
			TaxCollectionStructureAnalysisTableParameter taxCollectionStructureAnalysisTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxCollectionStructureAnalysisTable> tableList = decisionAnalysisSearchDao.getTaxCollectionStructureAnalysisTable(taxCollectionStructureAnalysisTableParameter);
		
		if (null != tableList) {
			// 产业
			if (tableList.get(0).getXsxm().equals("cy")) {
				IndustryService industryService = new IndustryService();
				for (TaxCollectionStructureAnalysisTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(industryService.getIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 行业大类
			if (tableList.get(0).getXsxm().equals("hy")) {
				BigIndustryService bigIndustryService = new BigIndustryService();
				for (TaxCollectionStructureAnalysisTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(bigIndustryService.getBigIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 注册类型
			if (tableList.get(0).getXsxm().equals("zc")) {
				FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
				for (TaxCollectionStructureAnalysisTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(firmRegTypeService.getFirmRegTypeById(xm).getMc());
					}
					
				}
			}
			
			// 管理机关
			if (tableList.get(0).getXsxm().equals("jg")) {
				TaxUnitService taxUnitService = new TaxUnitService();
				for (TaxCollectionStructureAnalysisTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxUnitService.getTaxUnitById(xm).getMc());
					}
					
				}
			}
			
			// 税种
			if (tableList.get(0).getXsxm().equals("sz")) {
				TaxTypeService taxTypeService = new TaxTypeService();
				for (TaxCollectionStructureAnalysisTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxTypeService.getTaxTypeById(xm).getMc());
					}
					
				}
			}
		}
		
		return tableList;
	}

	/**
	 * 税收类所有指标分析查询表
	 * @param taxCollectionAllIndexTableParameter
	 * @return
	 */
	public List<TaxCollectionAllIndexTable> getTaxCollectionAllIndexTable(
			TaxCollectionAllIndexTableParameter taxCollectionAllIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxCollectionAllIndexTable> tableList = decisionAnalysisSearchDao.getTaxCollectionAllIndexTable(taxCollectionAllIndexTableParameter);
		return tableList;
	}

	/**
	 * 税收类分指标分析查询表
	 * @param taxCollectionSubIndexTableParameter
	 * @return
	 */
	public List<TaxCollectionSubIndexTable> getTaxCollectionSubIndexTable(
			TaxCollectionSubIndexTableParameter taxCollectionSubIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxCollectionSubIndexTable> tableList = decisionAnalysisSearchDao.getTaxCollectionSubIndexTable(taxCollectionSubIndexTableParameter);
		return tableList;
	}

	public TaxGrowTaxResourceDevelopIndexTable getTaxGrowTaxResourceDevelopIndexTable(
			TaxGrowTaxResourceDevelopIndexTableParameter taxGrowTaxResourceDevelopIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		TaxGrowTaxResourceDevelopIndexTable table = decisionAnalysisSearchDao.getTaxGrowTaxResourceDevelopIndexTable(taxGrowTaxResourceDevelopIndexTableParameter);
		return table;
	}

	/**
	 * 查询指数结构
	 * @param nd
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public TaxGrowTaxResourceDevelopIndexTable getIndexStructureTable(
			String nd, String sqQ, String sqZ) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		TaxGrowTaxResourceDevelopIndexTable table = decisionAnalysisSearchDao.getIndexStructureTable(nd, sqQ, sqZ);
		return table;
	}

	/**
	 * 税源类所有指标查询
	 * @param taxResourceAllIndexTableParameter
	 * @return
	 */
	public List<TaxResourceAllIndexTable> getTaxResourceAllIndexTable(
			TaxResourceAllIndexTableParameter taxResourceAllIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxResourceAllIndexTable> tableList = decisionAnalysisSearchDao.getTaxResourceAllIndexTable(taxResourceAllIndexTableParameter);
		return tableList;
	}

	/**
	 * 税源类分指标查询
	 * @param taxResourceSubIndexTableParameter
	 * @return
	 */
	public List<TaxResourceSubIndexTable> getTaxResourceSubIndexTable(
			TaxResourceSubIndexTableParameter taxResourceSubIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxResourceSubIndexTable> tableList = decisionAnalysisSearchDao.getTaxResourceSubIndexTable(taxResourceSubIndexTableParameter);
		return tableList;
	}

	/**
	 * 税负类所有指标查询
	 * @param taxBurdenAllIndexTableParameter
	 * @return
	 */
	public List<TaxBurdenAllIndexTable> getTaxBurdenAllIndexTable(
			TaxBurdenAllIndexTableParameter taxBurdenAllIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxBurdenAllIndexTable> tableList = decisionAnalysisSearchDao.getTaxBurdenAllIndexTable(taxBurdenAllIndexTableParameter);
		return tableList;
	}

	/**
	 * 税负类分指标查询
	 * @param taxBurdenSubIndexTableParameter
	 * @return
	 */
	public List<TaxBurdenSubIndexTable> getTaxBurdenSubIndexTable(
			TaxBurdenSubIndexTableParameter taxBurdenSubIndexTableParameter) {
		DecisionAnalysisSearchDao decisionAnalysisSearchDao = new DecisionAnalysisSearchDao();
		List<TaxBurdenSubIndexTable> tableList = decisionAnalysisSearchDao.getTaxBurdenSubIndexTable(taxBurdenSubIndexTableParameter);
		return tableList;
	}
}
