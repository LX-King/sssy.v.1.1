package com.tyut.sssy.taxburden.domain;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：TaxBurdenAnalysisSearchTableParameter  
 * 类描述：税负分析查询表 输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-23 上午10:30:47  
 * 修改人：梁斌  
 * 修改时间：2012-5-23 上午10:30:47  
 * 修改备注：  
 * @version 
 *
 */
public class TaxBurdenAnalysisSearchTableParameter {

	private String nd;	// 年度
	
	private String sssqQ;	// 所属时期起
	
	private String sssqZ;	// 所属时期止
	
	private String swjgDm;	// 税务机关代码
	
	private String qyzclx;	// 企业注册类型
	
	private String cy;		// 产业
	
	private String hydl;	// 行业
	
	private String sflx;	// 税负类型

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getSssqQ() {
		return sssqQ;
	}

	public void setSssqQ(String sssqQ) {
		this.sssqQ = sssqQ;
	}

	public String getSssqZ() {
		return sssqZ;
	}

	public void setSssqZ(String sssqZ) {
		this.sssqZ = sssqZ;
	}

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getQyzclx() {
		return qyzclx;
	}

	public void setQyzclx(String qyzclx) {
		this.qyzclx = qyzclx;
	}

	public String getCy() {
		return cy;
	}

	public void setCy(String cy) {
		this.cy = cy;
	}

	public String getHydl() {
		return hydl;
	}

	public void setHydl(String hydl) {
		this.hydl = hydl;
	}

	public String getSflx() {
		return sflx;
	}

	public void setSflx(String sflx) {
		this.sflx = sflx;
	}
}
