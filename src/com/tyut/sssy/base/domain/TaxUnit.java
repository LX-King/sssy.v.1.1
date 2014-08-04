package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxUnit  
 * 类描述：税务机关类  
 * 创建人：梁斌  
 * 创建时间：2012-5-8 下午09:09:40  
 * 修改人：梁斌  
 * 修改时间：2012-5-8 下午09:09:40  
 * 修改备注：  
 * @version 
 *
 */
public class TaxUnit {

	private String swjgDm;		// 税务机关代码
	
	private String sjswjgDm;	// 上级税务机关代码
	
	private String mc;			// 税务机关名称
	
	private int px;				// ???
	
	private String gljgbz;		// 管理机关标识

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getSjswjgDm() {
		return sjswjgDm;
	}

	public void setSjswjgDm(String sjswjgDm) {
		this.sjswjgDm = sjswjgDm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public int getPx() {
		return px;
	}

	public void setPx(int px) {
		this.px = px;
	}

	public String getGljgbz() {
		return gljgbz;
	}

	public void setGljgbz(String gljgbz) {
		this.gljgbz = gljgbz;
	}
	
}
