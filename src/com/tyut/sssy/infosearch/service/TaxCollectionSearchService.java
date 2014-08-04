package com.tyut.sssy.infosearch.service;

import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.service.*;
import com.tyut.sssy.infosearch.dao.TaxCollectionSearchDao;
import com.tyut.sssy.taxcollection.domain.*;

import java.math.BigDecimal;
import java.util.List;

public class TaxCollectionSearchService {

	/**
	 * 收入完成情况表
	 * @param taxCollectionFinishTableParameter
	 * @return
	 */
	public List<TaxCollectionFinishTable> getFinishTable(
			TaxCollectionFinishTableParameter taxCollectionFinishTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<TaxCollectionFinishTable> taxCollectionFinishTable = taxCollectionSearchDao.getFinishTable(taxCollectionFinishTableParameter);
		
		// 单位选择万元
		if (taxCollectionFinishTableParameter.getMoneyUnit().equals("wyuan")) {
			
				for (TaxCollectionFinishTable table : taxCollectionFinishTable) {
					table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//					table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA5(table.getA5().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA6(table.getA6().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//					table.setA7(table.getA7().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//					table.setA8(table.getA8().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		if (null != taxCollectionFinishTable) {
			// 产业
			if (taxCollectionFinishTable.get(0).getFl().equals("01")) {
				IndustryService industryService = new IndustryService();
				for (TaxCollectionFinishTable table : taxCollectionFinishTable) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(industryService.getIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 行业大类
			if (taxCollectionFinishTable.get(0).getFl().equals("02")) {
				BigIndustryService bigIndustryService = new BigIndustryService();
				for (TaxCollectionFinishTable table : taxCollectionFinishTable) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(bigIndustryService.getBigIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 注册类型
			if (taxCollectionFinishTable.get(0).getFl().equals("03")) {
				FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
				for (TaxCollectionFinishTable table : taxCollectionFinishTable) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(firmRegTypeService.getFirmRegTypeById(xm).getMc());
					}
					
				}
			}

			// 税种
			if (taxCollectionFinishTable.get(0).getFl().equals("05")) {
				TaxTypeService taxTypeService = new TaxTypeService();
				for (TaxCollectionFinishTable table : taxCollectionFinishTable) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxTypeService.getTaxTypeById(xm).getMc());
					}
					
				}
			}
		}
		
		return taxCollectionFinishTable;
	}

	/**
	 * 税收收入结构变化表
	 * @param taxCollectionStructureChangeTableParameter
	 * @return
	 */
	public List<TaxCollectionStructureChangeTable> getStructureChangeTable(
			TaxCollectionStructureChangeTableParameter taxCollectionStructureChangeTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<TaxCollectionStructureChangeTable> tableList = taxCollectionSearchDao.getStructureChangeTable(taxCollectionStructureChangeTableParameter);
		
		// 单位选择万元
		if (taxCollectionStructureChangeTableParameter.getMoneyUnit().equals("wyuan")) {
			for (TaxCollectionStructureChangeTable table : tableList) {
				table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//				table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//				table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//				table.setA5(table.getA5().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		if (null != tableList) {
			// 产业
			if (tableList.get(0).getFl().equals("01")) {
				IndustryService industryService = new IndustryService();
				for (TaxCollectionStructureChangeTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(industryService.getIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 行业大类
			if (tableList.get(0).getFl().equals("02")) {
				BigIndustryService bigIndustryService = new BigIndustryService();
				for (TaxCollectionStructureChangeTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(bigIndustryService.getBigIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 注册类型
			if (tableList.get(0).getFl().equals("03")) {
				FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
				for (TaxCollectionStructureChangeTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(firmRegTypeService.getFirmRegTypeById(xm).getMc());
					}
					
				}
			}
			
			/*
			// 预算级次
			if (tableList.get(0).getFl().equals("04")) {
				PreCalLevelService preCalLevelService = new PreCalLevelService();
				for (TaxCollectionStructureChangeTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(preCalLevelService.getPreCalLevelById(xm).getMc());
					}
					
				}
			}
			*/
			
			// 税种
			if (tableList.get(0).getFl().equals("05")) {
				TaxTypeService taxTypeService = new TaxTypeService();
				for (TaxCollectionStructureChangeTable table : tableList) {
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
	 * 待解税金余额表
	 * @param toResolveBalanceTableParameter
	 * @return
	 */
	public List<ToResolveTaxBalanceTable> getToResolveBalanceTable(
			ToResolveTaxBalanceTableParameter toResolveBalanceTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<ToResolveTaxBalanceTable> tableList = taxCollectionSearchDao.getToResolveBalanceTable(toResolveBalanceTableParameter);
		
		// 单位选择万元
		if (toResolveBalanceTableParameter.getMoneyUnit().equals("wyuan")) {
			if (null != tableList) {
				for (ToResolveTaxBalanceTable table : tableList) {
					table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA5(table.getA5().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA6(table.getA6().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA7(table.getA7().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA8(table.getA8().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA9(table.getA9().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA10(table.getA10().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
					table.setA11(table.getA11().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				}
			}
		}
		
		if (null != tableList) {
			// 管理机关
			if (tableList.get(0).getXsxm().equals("G")) {
				TaxUnitService taxUnitService = new TaxUnitService();
				for (ToResolveTaxBalanceTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxUnitService.getTaxUnitById(xm).getMc());
					}
					
				}
			}
			
			// 纳税人
			if (tableList.get(0).getXsxm().equals("N")) {
				TaxPayerService taxPayerService = new TaxPayerService();
				for (ToResolveTaxBalanceTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxPayerService.getTaxPayerByCode(xm).getNsrmc());
					}
					
				}
			}
			
		}
		
		return tableList;
	}

	/**
	 * 待解税金变动情况表
	 * @param toResolveTaxChangeTableParameter
	 * @return
	 */
	public List<ToResolveTaxChangeTable> getToResolveChangeTable(
			ToResolveTaxChangeTableParameter toResolveTaxChangeTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<ToResolveTaxChangeTable> tableList = taxCollectionSearchDao.getToResolveChangeTable(toResolveTaxChangeTableParameter);
		
		// 单位选择万元
		if (toResolveTaxChangeTableParameter.getMoneyUnit().equals("wyuan")) {
			for (ToResolveTaxChangeTable table : tableList) {
				table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA5(table.getA5().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA6(table.getA6().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA7(table.getA7().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA8(table.getA8().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA9(table.getA9().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA10(table.getA10().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA11(table.getA11().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA12(table.getA12().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		TaxTypeService taxTypeService = new TaxTypeService();
		for (ToResolveTaxChangeTable table : tableList) {
			String xm = table.getXm().trim();
			if (!xm.equals("合计")) {
				table.setXm(taxTypeService.getTaxTypeById(xm).getMc());
			}
		}
		
		return tableList;
	}

	/**
	 * 欠缴税金明细表
	 * @param ownPayTaxDetailTableParameter
	 * @return
	 */
	public List<OwnPayTaxDetailTable> getOwnPayTaxDetailTable(
			OwnPayTaxDetailTableParameter ownPayTaxDetailTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<OwnPayTaxDetailTable> tableList = taxCollectionSearchDao.getOwnPayTaxDetailTable(ownPayTaxDetailTableParameter);
		
		// 单位选择万元
		if (ownPayTaxDetailTableParameter.getMoneyUnit().equals("wyuan")) {
			for (OwnPayTaxDetailTable table : tableList) {
				table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		if (tableList.size() != 0) {
			// 管理机关
			if (tableList.get(0).getFl().equals("jg")) {
				TaxUnitService taxUnitService = new TaxUnitService();
				for (OwnPayTaxDetailTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxUnitService.getTaxUnitById(xm).getMc());
					}
					
				}
			}
			
			// 纳税人
			if (tableList.get(0).getFl().equals("qy")) {
				TaxPayerService taxPayerService = new TaxPayerService();
				for (OwnPayTaxDetailTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(taxPayerService.getTaxPayerByCode(xm).getNsrmc());
					}
					
				}
			}
			
			// 行业大类
			if (tableList.get(0).getFl().equals("hy")) {
				BigIndustryService bigIndustryService = new BigIndustryService();
				for (OwnPayTaxDetailTable table : tableList) {
					String xm = table.getXm().trim();
					if (!xm.equals("合计")) {
						table.setXm(bigIndustryService.getBigIndustryById(xm).getMc());
					}
					
				}
			}
			
			// 税种
			if (tableList.get(0).getFl().equals("sz")) {
				TaxTypeService taxTypeService = new TaxTypeService();
				for (OwnPayTaxDetailTable table : tableList) {
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
	 * 重点企业实缴税收变化表
	 * @param firmPayTaxChangeTableParameter
	 * @return
	 */
	public List<FirmPayTaxChangeTable> getFirmPayTaxChangeTable(
			FirmPayTaxChangeTableParameter firmPayTaxChangeTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<FirmPayTaxChangeTable> tableList = taxCollectionSearchDao.getFirmPayTaxChangeTable(firmPayTaxChangeTableParameter);
		
		// 单位选择万元
		if (firmPayTaxChangeTableParameter.getMoneyUnit().equals("wyuan")) {
			for (FirmPayTaxChangeTable table : tableList) {
				table.setA1(table.getA1().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA2(table.getA2().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//				table.setA3(table.getA3().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA4(table.getA4().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA5(table.getA5().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA6(table.getA6().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
//				table.setA7(table.getA7().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA8(table.getA8().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = new TaxPayer();
		for (FirmPayTaxChangeTable table : tableList) {
			String xm = table.getXm().trim();
			if (!xm.equals("合计")) {
				taxPayer = taxPayerService.getTaxPayerByCode(xm);
				if (null != taxPayer) {
					table.setXm(taxPayerService.getTaxPayerByCode(xm).getNsrmc());
				} else {
					table.setXm(xm);
				}
				
			}
		}
		
		return tableList;
	}

	public List<FirmTaxAccountTable> getFirmTaxAccountTable(
			FirmTaxAccountTableParameter firmTaxAccountTableParameter) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<FirmTaxAccountTable> tableList = taxCollectionSearchDao.getFirmTaxAccountTable(firmTaxAccountTableParameter);
		
		// 单位选择万元
		if (firmTaxAccountTableParameter.getMoneyUnit().equals("wyuan")) {
			for (FirmTaxAccountTable table : tableList) {
				table.setA11(table.getA11().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA12(table.getA12().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA13(table.getA13().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA21(table.getA21().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA22(table.getA22().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA23(table.getA23().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA31(table.getA31().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA32(table.getA32().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA33(table.getA33().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA41(table.getA41().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA42(table.getA42().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA43(table.getA43().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA51(table.getA51().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA52(table.getA52().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA53(table.getA53().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA61(table.getA61().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA62(table.getA62().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA63(table.getA63().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA71(table.getA71().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA72(table.getA72().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA73(table.getA73().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA81(table.getA81().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA82(table.getA82().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA83(table.getA83().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA91(table.getA91().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA92(table.getA92().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA93(table.getA93().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA101(table.getA101().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA102(table.getA102().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA103(table.getA103().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA111(table.getA111().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA112(table.getA112().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA113(table.getA113().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA121(table.getA121().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA122(table.getA122().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA123(table.getA123().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA131(table.getA131().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA132(table.getA132().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
				table.setA133(table.getA133().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
		// 纳税人
		TaxPayerService taxPayerService = new TaxPayerService();
		TaxPayer taxPayer = new TaxPayer();
		for (FirmTaxAccountTable table : tableList) {
			String xm = table.getXm().trim();
			if (!xm.equals("合计")) {
				taxPayer = taxPayerService.getTaxPayerByCode(xm);
				if (null != taxPayer) {
					table.setXm(taxPayerService.getTaxPayerByCode(xm).getNsrmc());
				} else {
					table.setXm(xm);
				}
				
			}
			
		}
		
		return tableList;
	}

	/**
	 * 税收收入实时入库查询
	 * @param nd
	 * @return
	 */
	public List<TaxCollectionRealTimeSearchTable> TaxCollectionRealTimeSearchTable(
			String nd) {
		TaxCollectionSearchDao taxCollectionSearchDao = new TaxCollectionSearchDao();
		List<TaxCollectionRealTimeSearchTable> tableList = taxCollectionSearchDao.TaxCollectionRealTimeSearchTable(nd);
		return tableList;
	}

}
