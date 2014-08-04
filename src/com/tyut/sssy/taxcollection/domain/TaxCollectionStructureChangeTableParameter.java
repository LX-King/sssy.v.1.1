package com.tyut.sssy.taxcollection.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionFinishTable  
 * 类描述：税收收入结构变化输入参数
 * 创建人：梁斌  
 * 创建时间：2012-5-9 下午03:08:46  
 * 修改人：梁斌  
 * 修改时间：2012-5-9 下午03:08:46  
 * 修改备注：  
 * @version 
 *
 */
public class TaxCollectionStructureChangeTableParameter {
	
	private String swjg;	// 税务机关
	
	private String sjlx;	// 数据类型
	
	private String nd;		// 年度
	
	private String pd;		// 频度
	
	private String fl;		// 分类
	
	private String yf;		// 月份
	
	private String moneyUnit;
	
	private String type;
	
	private String order;

	public String getSwjg() {
		return swjg;
	}

	public void setSwjg(String swjg) {
		this.swjg = swjg;
	}

	public String getSjlx() {
		return sjlx;
	}

	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public String getYf() {
		return yf;
	}

	public void setYf(String yf) {
		this.yf = yf;
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
