package com.tyut.sssy.report.service;

import java.math.BigDecimal;
import java.util.List;

import com.tyut.sssy.base.service.TaxPayerService;
import com.tyut.sssy.report.dao.FirmTaxCollectionSituationDao;
import com.tyut.sssy.report.domain.FirmTaxCollectionSituation;

/**
 * 
 * 项目名称：sssy20120729
 * 类名称：FirmTaxCollectionSituationService  
 * 类描述：重点税源大户实现税收收入情况 service  
 * 创建人：梁斌  
 * 创建时间：2012-8-2 下午02:26:59  
 * 修改人：梁斌  
 * 修改时间：2012-8-2 下午02:26:59  
 * 修改备注：  
 * @version 
 *
 */
public class FirmTaxCollectionSituationService {

	/**
	 * 取得前10的记录
	 * @param year
	 * @param sqQ
	 * @param sqZ
	 * @return
	 */
	public List<FirmTaxCollectionSituation> getTopTenRecords(String year,
			String sqQ, String sqZ, String swjgDm) {
		FirmTaxCollectionSituationDao firmTaxCollectionSituationDao = new FirmTaxCollectionSituationDao();
		List<FirmTaxCollectionSituation> topTenRecords = firmTaxCollectionSituationDao.getTopTenRecords(year, sqQ, sqZ, swjgDm);
		
		if (topTenRecords.size() != 0 && topTenRecords != null) {
			TaxPayerService taxPayerService = new TaxPayerService();
			for (FirmTaxCollectionSituation firmTaxCollectionSituation : topTenRecords) {
				firmTaxCollectionSituation.setTaxPayer(taxPayerService.getTaxPayerByCode(firmTaxCollectionSituation.getNsrsbh().trim()));
			}
			
			if ("12".equals(sqZ)) {
				// 本期是四季度
				for (FirmTaxCollectionSituation firmTaxCollectionSituation : topTenRecords) {
					String nsrsbh = firmTaxCollectionSituation.getNsrsbh();
					
					// 本期--应缴税收(12月累计)
					BigDecimal bqYjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "12", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(12月累计)
					BigDecimal bqSjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "12", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(12月累计)
					BigDecimal bqQjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "12", swjgDm, nsrsbh, "bqQjss");
					
					
					// 本期--应缴税收(9月累计)
					BigDecimal bqYjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(9月累计)
					BigDecimal bqSjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(9月累计)
					BigDecimal bqQjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqQjss");
					
					// 计算差值
					BigDecimal bqYjss = bqYjss1.subtract(bqYjss2);
					
					BigDecimal bqSjss = bqSjss1.subtract(bqSjss2);
					
					BigDecimal bqQjss = bqQjss1.subtract(bqQjss2);
					
					// 赋值
					firmTaxCollectionSituation.setBqYjss(bqYjss);
					firmTaxCollectionSituation.setBqSjss(bqSjss);
					firmTaxCollectionSituation.setBqQjss(bqQjss);
				}
			} else if ("09".equals(sqZ)) {
				// 本期是三季度
				for (FirmTaxCollectionSituation firmTaxCollectionSituation : topTenRecords) {
					String nsrsbh = firmTaxCollectionSituation.getNsrsbh();
					
					// 本期--应缴税收(9月累计)
					BigDecimal bqYjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(9月累计)
					BigDecimal bqSjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(9月累计)
					BigDecimal bqQjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "09", swjgDm, nsrsbh, "bqQjss");
					
					
					// 本期--应缴税收(6月累计)
					BigDecimal bqYjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(6月累计)
					BigDecimal bqSjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(6月累计)
					BigDecimal bqQjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqQjss");
					
					// 计算差值
					BigDecimal bqYjss = bqYjss1.subtract(bqYjss2);
					
					BigDecimal bqSjss = bqSjss1.subtract(bqSjss2);
					
					BigDecimal bqQjss = bqQjss1.subtract(bqQjss2);
					
					// 赋值
					firmTaxCollectionSituation.setBqYjss(bqYjss);
					firmTaxCollectionSituation.setBqSjss(bqSjss);
					firmTaxCollectionSituation.setBqQjss(bqQjss);
				}
			} else if ("06".equals(sqZ)) {
				// 本期是二季度
				for (FirmTaxCollectionSituation firmTaxCollectionSituation : topTenRecords) {
					String nsrsbh = firmTaxCollectionSituation.getNsrsbh();
					
					// 本期--应缴税收(6月累计)
					BigDecimal bqYjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(6月累计)
					BigDecimal bqSjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(6月累计)
					BigDecimal bqQjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "06", swjgDm, nsrsbh, "bqQjss");
					
					
					// 本期--应缴税收(3月累计)
					BigDecimal bqYjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(3月累计)
					BigDecimal bqSjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(3月累计)
					BigDecimal bqQjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqQjss");
					
					// 计算差值
					BigDecimal bqYjss = bqYjss1.subtract(bqYjss2);
					
					BigDecimal bqSjss = bqSjss1.subtract(bqSjss2);
					
					BigDecimal bqQjss = bqQjss1.subtract(bqQjss2);
					
					// 赋值
					firmTaxCollectionSituation.setBqYjss(bqYjss);
					firmTaxCollectionSituation.setBqSjss(bqSjss);
					firmTaxCollectionSituation.setBqQjss(bqQjss);
				}
			} else if ("03".equals(sqZ)) {
				// 本期是一季度
				for (FirmTaxCollectionSituation firmTaxCollectionSituation : topTenRecords) {
					String nsrsbh = firmTaxCollectionSituation.getNsrsbh();
					
					// 本期--应缴税收(3月累计)
					BigDecimal bqYjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(3月累计)
					BigDecimal bqSjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(3月累计)
					BigDecimal bqQjss1 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqQjss");
					
					/*
					// 本期--应缴税收(3月累计)
					BigDecimal bqYjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqYjss");
					
					// 本期--实缴税收(3月累计)
					BigDecimal bqSjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqSjss");
					
					// 本期--欠缴税收(3月累计)
					BigDecimal bqQjss2 = firmTaxCollectionSituationDao.getRecordByNsrsbh(year, "03", swjgDm, nsrsbh, "bqQjss");
					
					// 计算差值
					BigDecimal bqYjss = bqYjss1.subtract(bqYjss2);
					
					BigDecimal bqSjss = bqSjss1.subtract(bqSjss2);
					
					BigDecimal bqQjss = bqQjss1.subtract(bqQjss2);
					*/
					
					// 赋值
					firmTaxCollectionSituation.setBqYjss(bqYjss1);
					firmTaxCollectionSituation.setBqSjss(bqSjss1);
					firmTaxCollectionSituation.setBqQjss(bqQjss1);
				}
			}
		}
		
		return topTenRecords;
	}

}
