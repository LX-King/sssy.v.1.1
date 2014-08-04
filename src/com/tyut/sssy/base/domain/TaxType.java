package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120531
 * 类名称：TaxType  
 * 类描述：税种  
 * 创建人：梁斌  
 * 创建时间：2012-5-31 下午10:53:23  
 * 修改人：梁斌  
 * 修改时间：2012-5-31 下午10:53:23  
 * 修改备注：  
 * @version 
 *
 */
public class TaxType {

	private String szDm;	// 税种代码
	
	private String mc;		// 名称
	
	private String yxbz;	// 有效标志

	public String getSzDm() {
		return szDm;
	}

	public void setSzDm(String szDm) {
		this.szDm = szDm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getYxbz() {
		return yxbz;
	}

	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	
}
