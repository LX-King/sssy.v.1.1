package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.FirmSizeDao;
import com.tyut.sssy.base.domain.FirmSize;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：FirmSizeService  
 * 类描述：企业规模service  
 * 创建人：梁斌  
 * 创建时间：2012-5-17 下午03:09:08  
 * 修改人：梁斌  
 * 修改时间：2012-5-17 下午03:09:08  
 * 修改备注：  
 * @version 
 *
 */
public class FirmSizeService {
	
	/**
	 * 企业规模列表
	 * @return
	 */
	public List<FirmSize> getFirmSizeList() {
		FirmSizeDao firmSizeDao = new FirmSizeDao();
		List<FirmSize> firmSizeList = firmSizeDao.getFirmSizeList();
		return firmSizeList;
	}
	
}
