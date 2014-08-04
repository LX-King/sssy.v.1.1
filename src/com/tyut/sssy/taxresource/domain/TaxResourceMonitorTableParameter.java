package com.tyut.sssy.taxresource.domain;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：TaxResourceMonitorTableParameter  
 * 类描述：重点税源检测户数统计表 输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-23 上午11:54:57  
 * 修改人：梁斌  
 * 修改时间：2012-5-23 上午11:54:57  
 * 修改备注：  
 * @version 
 *
 */
public class TaxResourceMonitorTableParameter {

	private String ssjgDm;	// 税收机关代码
	
	private String qygm;	// 企业规模
	
	private String nd;		// 年度
	
	private String sssqQ;	// 时期起
	
	private String sssqZ;	// 时期止
	
	private String hydlDm;	// 行业大类代码
	
	private String qyzclx;	// 企业注册类型代码
	
	private String xmbz;	// 项目标志
	
	private String dszsbz;	// 地税征收标志

	public String getSsjgDm() {
		return ssjgDm;
	}

	public void setSsjgDm(String ssjgDm) {
		this.ssjgDm = ssjgDm;
	}

	public String getQygm() {
		return qygm;
	}

	public void setQygm(String qygm) {
		this.qygm = qygm;
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

	public String getHydlDm() {
		return hydlDm;
	}

	public void setHydlDm(String hydlDm) {
		this.hydlDm = hydlDm;
	}

	public String getQyzclx() {
		return qyzclx;
	}

	public void setQyzclx(String qyzclx) {
		this.qyzclx = qyzclx;
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
