package com.tyut.sssy.taxcollection.domain;

import java.math.BigDecimal;

/**
 * 
 * 项目名称：sssy20120601
 * 类名称：TaxCollectionRealTimeSearchTable  
 * 类描述：税收收入实时入库查询表  
 * 创建人：梁斌  
 * 创建时间：2012-6-13 下午05:17:53  
 * 修改人：梁斌  
 * 修改时间：2012-6-13 下午05:17:53  
 * 修改备注：  
 * @version 
 *
 */
public class TaxCollectionRealTimeSearchTable {

	private String xm;
	
	private BigDecimal a1;
	
	private int a2;

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public BigDecimal getA1() {
		return a1;
	}

	public void setA1(BigDecimal a1) {
		this.a1 = a1;
	}

	public int getA2() {
		return a2;
	}

	public void setA2(int a2) {
		this.a2 = a2;
	}
}
