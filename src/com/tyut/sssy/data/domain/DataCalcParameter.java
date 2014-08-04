package com.tyut.sssy.data.domain;

/**
 * 
 * 项目名称：sssy20120419
 * 类名称：DataCalcParameter  
 * 类描述：数据分析参数类（丁）  
 * 创建人：梁斌  
 * 创建时间：2012-4-22 下午12:32:44  
 * 修改人：梁斌  
 * 修改时间：2012-4-22 下午12:32:44  
 * 修改备注：  
 * @version 
 *
 */
public class DataCalcParameter {

	private String fxqNd;	// 分析期年度
	
	private String fxqSssqQ;	// 分析期所属时起
	
	private String fxqSssqZ;	// 分析期所属时止
	
	public String getFxqNd() {
		return fxqNd;
	}

	public void setFxqNd(String fxqNd) {
		this.fxqNd = fxqNd;
	}

	public String getFxqSssqQ() {
		return fxqSssqQ;
	}

	public void setFxqSssqQ(String fxqSssqQ) {
		this.fxqSssqQ = fxqSssqQ;
	}

	public String getFxqSssqZ() {
		return fxqSssqZ;
	}

	public void setFxqSssqZ(String fxqSssqZ) {
		this.fxqSssqZ = fxqSssqZ;
	}
}
