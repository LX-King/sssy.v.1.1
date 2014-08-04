package com.tyut.sssy.taxcollection.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionFinishTable  
 * 类描述：待解税金余额输入参数
 * 创建人：梁斌  
 * 创建时间：2012-5-9 下午03:08:46  
 * 修改人：梁斌  
 * 修改时间：2012-5-9 下午03:08:46  
 * 修改备注：  
 * @version 
 *
 */
public class ToResolveTaxBalanceTableParameter {
	
	private String xsxm;	// 显示项目
	
	private String swjgDm;	// 税务机关代码
	
	private String nd;		// 年度
	
	private String sqZ;		// 时期止
	
	private String nsrsbh;		// 纳税人编号 
	
	private String moneyUnit;	// 金额单位
	
	private String type;		
	
	private String order;

	public String getXsxm() {
		return xsxm;
	}

	public void setXsxm(String xsxm) {
		this.xsxm = xsxm;
	}

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getSqZ() {
		return sqZ;
	}

	public void setSqZ(String sqZ) {
		this.sqZ = sqZ;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getMoneyUnit() {
		return moneyUnit;
	}

	public void setMoneyUnit(String moneyUnit) {
		this.moneyUnit = moneyUnit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
