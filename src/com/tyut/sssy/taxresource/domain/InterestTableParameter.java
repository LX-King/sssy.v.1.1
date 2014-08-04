package com.tyut.sssy.taxresource.domain;

/**
 * 
 * 项目名称：sssy20120523
 * 类名称：InterestTableParameter  
 * 类描述：利润表输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-29 下午03:08:11  
 * 修改人：梁斌  
 * 修改时间：2012-5-29 下午03:08:11  
 * 修改备注：  
 * @version 
 *
 */
public class InterestTableParameter {

	private String nsrsbh;	// 纳税人识别号
	
	private String nd;		// 年度
	
	private String sssqZ;	// 时期止

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getSssqZ() {
		return sssqZ;
	}

	public void setSssqZ(String sssqZ) {
		this.sssqZ = sssqZ;
	}
}
