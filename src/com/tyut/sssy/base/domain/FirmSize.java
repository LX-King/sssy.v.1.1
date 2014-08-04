package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：FirmSize  
 * 类描述：企业规模  
 * 创建人：梁斌  
 * 创建时间：2012-5-17 下午03:07:30  
 * 修改人：梁斌  
 * 修改时间：2012-5-17 下午03:07:30  
 * 修改备注：  
 * @version 
 *
 */
public class FirmSize {

	private String qygmDm;	// 企业规模代码
	
	private String qygmMc;	// 企业规模名称
	
	private String seQ;		// 起
	
	private String seZ;		// 止

	public String getQygmDm() {
		return qygmDm;
	}

	public void setQygmDm(String qygmDm) {
		this.qygmDm = qygmDm;
	}

	public String getQygmMc() {
		return qygmMc;
	}

	public void setQygmMc(String qygmMc) {
		this.qygmMc = qygmMc;
	}

	public String getSeQ() {
		return seQ;
	}

	public void setSeQ(String seQ) {
		this.seQ = seQ;
	}

	public String getSeZ() {
		return seZ;
	}

	public void setSeZ(String seZ) {
		this.seZ = seZ;
	}
}
