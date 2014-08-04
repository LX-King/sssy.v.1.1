package com.tyut.sssy.decisionanalysis.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionStructureAnalysisTableParameter  
 * 类描述：税收结构分析表输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午09:29:23  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午09:29:23  
 * 修改备注：  
 * @version 
 *
 */
public class TaxCollectionStructureAnalysisTableParameter {

	private String swjg;		// 税务机关
	
	private String nsrsbh;		// 纳税人编号
	
	private String qyzclx;		// 企业注册类型
	
	private String cy;			// 产业
	
	private String hy;			// 行业
	
	private String sklx;		// 税款类型
	
	private String nd;			// 年度
	
	private String sqZ;			// 时期止
	
	private String xsxm;		// 显示项目

	public String getSwjg() {
		return swjg;
	}

	public void setSwjg(String swjg) {
		this.swjg = swjg;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
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

	public String getHy() {
		return hy;
	}

	public void setHy(String hy) {
		this.hy = hy;
	}

	public String getSklx() {
		return sklx;
	}

	public void setSklx(String sklx) {
		this.sklx = sklx;
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

	public String getXsxm() {
		return xsxm;
	}

	public void setXsxm(String xsxm) {
		this.xsxm = xsxm;
	}
}
