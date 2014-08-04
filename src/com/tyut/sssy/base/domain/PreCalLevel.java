package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120601
 * 类名称：PreCalLevel  
 * 类描述：预算级次  
 * 创建人：梁斌  
 * 创建时间：2011-6-1 上午08:57:04  
 * 修改人：梁斌  
 * 修改时间：2011-6-1 上午08:57:04  
 * 修改备注：  
 * @version 
 *
 */
public class PreCalLevel {

	private String ysjcDm;		// 代码
	
	private String mc;			// 名称
	
	private String yxbz;		// 有效标志

	public String getYsjcDm() {
		return ysjcDm;
	}

	public void setYsjcDm(String ysjcDm) {
		this.ysjcDm = ysjcDm;
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
