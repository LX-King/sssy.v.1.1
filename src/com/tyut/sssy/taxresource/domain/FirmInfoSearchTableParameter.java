package com.tyut.sssy.taxresource.domain;

/**
 * 
 * 项目名称：sssy20120523
 * 类名称：FirmInfoSearchTableParameter  
 * 类描述：企业经营信息表输入参数  
 * 创建人：梁斌  
 * 创建时间：2012-5-24 下午02:07:59  
 * 修改人：梁斌  
 * 修改时间：2012-5-24 下午02:07:59  
 * 修改备注：  
 * @version 
 *
 */
public class FirmInfoSearchTableParameter {

	private String nsrsbh;	// 纳税人编号
	
	private String nd;		// 年度
	
	private String sssqZ;	// 所属时期止

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
