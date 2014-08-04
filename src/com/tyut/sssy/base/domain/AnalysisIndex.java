package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：AnalysisIndex  
 * 类描述：分析指标  
 * 创建人：梁斌  
 * 创建时间：2012-5-11 上午11:08:32  
 * 修改人：梁斌  
 * 修改时间：2012-5-11 上午11:08:32  
 * 修改备注：  
 * @version 
 *
 */
public class AnalysisIndex {

	private String fxzbDm;		// 分析指标代码
	
	private String mc;			// 指标名称
	
	private String yxbz;		// ???

	public String getFxzbDm() {
		return fxzbDm;
	}

	public void setFxzbDm(String fxzbDm) {
		this.fxzbDm = fxzbDm;
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
