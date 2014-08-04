package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120523
 * 类名称：TaxPayer  
 * 类描述：纳税人类  
 * 创建人：梁斌  
 * 创建时间：2012-5-27 上午11:38:11  
 * 修改人：梁斌  
 * 修改时间：2012-5-27 上午11:38:11  
 * 修改备注：  select * from jc_jbxxb_zdqy
 * @version 
 *
 */
public class TaxPayer {

	private String nsrsbh;	// 纳税人编号
	
	private String nsrmc;	// 纳税人名称
	
	private String swjgDm;	// 税务机关代码
	
	private TaxUnit taxUnit;	// 税务机关对象
	
	private String nd;		// 年度
	
	private String sssqQ;	// 所属时期起
	
	private String sssqZ;	// 所属时期止

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getNsrmc() {
		return nsrmc;
	}

	public void setNsrmc(String nsrmc) {
		this.nsrmc = nsrmc;
	}

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public TaxUnit getTaxUnit() {
		return taxUnit;
	}

	public void setTaxUnit(TaxUnit taxUnit) {
		this.taxUnit = taxUnit;
	}

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
}
