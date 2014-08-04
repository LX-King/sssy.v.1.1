package com.tyut.sssy.infosearch.service;

import java.util.List;

import com.tyut.sssy.infosearch.dao.TaxResourceSearchDao;
import com.tyut.sssy.taxresource.domain.AssetLiabilityTable;
import com.tyut.sssy.taxresource.domain.AssetLiabilityTableParameter;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTable;
import com.tyut.sssy.taxresource.domain.FirmInfoSearchTableParameter;
import com.tyut.sssy.taxresource.domain.FirmRunInfoTable;
import com.tyut.sssy.taxresource.domain.FirmRunInfoTableParameter;
import com.tyut.sssy.taxresource.domain.InterestTable;
import com.tyut.sssy.taxresource.domain.InterestTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTable;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorFirmDetailTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTable;
import com.tyut.sssy.taxresource.domain.TaxResourceMonitorTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTable;
import com.tyut.sssy.taxresource.domain.TaxResourceQualityChangeTableParameter;
import com.tyut.sssy.taxresource.domain.TaxResourceSumChangeTable;
import com.tyut.sssy.taxresource.domain.TaxResourceSumChangeTableParameter;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxResourceSearchService  
 * 类描述：税源类查询 service  
 * 创建人：梁斌  
 * 创建时间：2012-5-15 上午10:14:28  
 * 修改人：梁斌  
 * 修改时间：2012-5-15 上午10:14:28  
 * 修改备注：  
 * @version 
 *
 */
public class TaxResourceSearchService {

	/**
	 * 重点税源监控企业明细表
	 * @param taxResourceMonitorFirmDetailTableParameter
	 * @return
	 */
	public List<TaxResourceMonitorFirmDetailTable> getTaxResourceMonitorFirmDetailTable(
			TaxResourceMonitorFirmDetailTableParameter taxResourceMonitorFirmDetailTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<TaxResourceMonitorFirmDetailTable> tableList = taxResourceSearchDao.getTaxResourceMonitorFirmDetailTable(taxResourceMonitorFirmDetailTableParameter);
		return tableList;
	}
	
	
	/**
	 * 重点税源监测户数统计表
	 * @param taxResourceMonitorTableParameter
	 * @return
	 */
	public List<TaxResourceMonitorTable> getTaxResourceMonitorTable(
			TaxResourceMonitorTableParameter taxResourceMonitorTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<TaxResourceMonitorTable> tableList = taxResourceSearchDao.getTaxResourceMonitorTable(taxResourceMonitorTableParameter);
		return tableList;
	}

	
	/**
	 * 企业基础信息查询表
	 * @param firmInfoSearchTableParameter
	 * @return
	 */
	public FirmInfoSearchTable getFirmInfoSearchTable(
			FirmInfoSearchTableParameter firmInfoSearchTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		FirmInfoSearchTable table = taxResourceSearchDao.getFirmInfoSearchTable(firmInfoSearchTableParameter);
		return table;
	}


	/**
	 * 企业经营信息表
	 * @param firmRunInfoTableParameter
	 * @return
	 */
	public FirmRunInfoTable getFirmRunInfoTable(
			FirmRunInfoTableParameter firmRunInfoTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		FirmRunInfoTable table = taxResourceSearchDao.getFirmRunInfoTable(firmRunInfoTableParameter);
		return table;
	}

	/**
	 * 税源总量增减变动情况表
	 * @param taxResourceSumChangeTableParameter
	 * @return
	 */
	public List<TaxResourceSumChangeTable> getTaxResourceSumChangeTable(
			TaxResourceSumChangeTableParameter taxResourceSumChangeTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<TaxResourceSumChangeTable> tableList = taxResourceSearchDao.getTaxResourceSumChangeTable(taxResourceSumChangeTableParameter);
		return tableList;
	}

	/**
	 * 税源质量变动情况表
	 * @param taxResourceQualityChangeTableParameter
	 * @return
	 */
	public List<TaxResourceQualityChangeTable> getTaxResourceQualityChangeTable(
			TaxResourceQualityChangeTableParameter taxResourceQualityChangeTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<TaxResourceQualityChangeTable> tableList = taxResourceSearchDao.getTaxResourceQualityChangeTable(taxResourceQualityChangeTableParameter);
		return tableList;
	}


	/**
	 * 利润表
	 * @param interestTableParameter
	 * @return
	 */
	public List<InterestTable> getInterestTable(
			InterestTableParameter interestTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<InterestTable> tableList = taxResourceSearchDao.getInterestTable(interestTableParameter);
		return tableList;
	}

	/**
	 * 获取新旧利润表
	 * @param interestTableParameter
	 * @return
	 */
	public String getInterestTableCwzbgs(
			InterestTableParameter interestTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		String cwzbgs = taxResourceSearchDao.getInterestTableCwzbgs(interestTableParameter);
		return cwzbgs;
	}


	/**
	 * 获取新旧资产负债表
	 * @param assetLiabilityTableParameter
	 * @return
	 */
	public String getAssetLiabilityTableCwzbgs(
			AssetLiabilityTableParameter assetLiabilityTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		String cwzbgs = taxResourceSearchDao.getAssetLiabilityTableCwzbgs(assetLiabilityTableParameter);
		return cwzbgs;
	}

	/**
	 * 资产负债表
	 * @param assetLiabilityTableParameter
	 * @return
	 */
	public List<AssetLiabilityTable> getAssetLiabilityTable(
			AssetLiabilityTableParameter assetLiabilityTableParameter) {
		TaxResourceSearchDao taxResourceSearchDao = new TaxResourceSearchDao();
		List<AssetLiabilityTable> tableList = taxResourceSearchDao.getAssetLiabilityTable(assetLiabilityTableParameter);
		return tableList;
	}

}
