package com.tyut.sssy.taxresource.domain;

/**
 * 
 * 项目名称：sssy20120523
 * 类名称：TaxResourceSumChangeTableParameter  
 * 类描述：税源总量增减变动情况表  
 * 创建人：梁斌  
 * 创建时间：2012-5-24 下午03:06:16  
 * 修改人：梁斌  
 * 修改时间：2012-5-24 下午03:06:16  
 * 修改备注：  
 * @version 
 *
 */
public class TaxResourceSumChangeTableParameter {

	private String nd;	// 分析期年度
	
	private String sssqQ;	// 所属时期起
	
	private String sssqZ;	// 所属时期止
	
	private String nsrsbh;	// 纳税人识别号
	
	private String swjgDm;	// 税务机关代码
	
	private String jedw;	// 金额单位
	
	private String qyzclx;	// 企业注册类型
	
	private String cy;		// 产业
	
	private String hydl;	// 行业
	
	private String xmbz;	// 项目标志
	
	private String dszsbz;	// 地税征收标志

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

	public String getJedw() {
		return jedw;
	}

	public void setJedw(String jedw) {
		this.jedw = jedw;
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

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getXmbz() {
		return xmbz;
	}

	public void setXmbz(String xmbz) {
		this.xmbz = xmbz;
	}

	public String getDszsbz() {
		return dszsbz;
	}

	public void setDszsbz(String dszsbz) {
		this.dszsbz = dszsbz;
	}
}
