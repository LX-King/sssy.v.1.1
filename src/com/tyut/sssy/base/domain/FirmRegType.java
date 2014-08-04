package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：FirmRegType  
 * 类描述：企业注册类型  
 * 创建人：梁斌  
 * 创建时间：2012-5-10 下午08:34:58  
 * 修改人：梁斌  
 * 修改时间：2012-5-10 下午08:34:58  
 * 修改备注：  
 * @version 
 *
 */
public class FirmRegType {

	private String qyzclxDm;	// 企业注册类型代码
	
	private String mc;			// 企业注册类型名称

	public String getQyzclxDm() {
		return qyzclxDm;
	}

	public void setQyzclxDm(String qyzclxDm) {
		this.qyzclxDm = qyzclxDm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
}
