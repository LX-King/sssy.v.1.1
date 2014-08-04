package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：Industry  
 * 类描述：产业  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午08:43:14  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午08:43:14  
 * 修改备注：  
 * @version 
 *
 */
public class Industry {

	private String cyDm;		// 产业代码
	
	private String mc;			// 产业名称

	public String getCyDm() {
		return cyDm;
	}

	public void setCyDm(String cyDm) {
		this.cyDm = cyDm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
}
