package com.tyut.sssy.decisionanalysis.domain;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：TaxGrowTaxResourceDevelopIndexTableParameter  
 * 类描述：税收增长与税源发展指数分析查询输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-17 上午10:07:11  
 * 修改人：梁斌  
 * 修改时间：2012-5-17 上午10:07:11  
 * 修改备注：  
 * @version 
 *
 */
public class TaxGrowTaxResourceDevelopIndexTableParameter {

	private String nd;		// 年度
	
	private String sqQ;		// 时期起
	
	private String sqZ;		// 时期止

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getSqQ() {
		return sqQ;
	}

	public void setSqQ(String sqQ) {
		this.sqQ = sqQ;
	}

	public String getSqZ() {
		return sqZ;
	}

	public void setSqZ(String sqZ) {
		this.sqZ = sqZ;
	}
	
}
