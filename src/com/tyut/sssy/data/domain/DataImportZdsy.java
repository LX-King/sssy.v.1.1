package com.tyut.sssy.data.domain;

/**
 * 
 * 项目名称：sssy20120617
 * 类名称：DataImportZdsy  
 * 类描述：数据导入（重点税源）  
 * 创建人：梁斌  
 * 创建时间：2012-6-17 上午09:56:20  
 * 修改人：梁斌  
 * 修改时间：2012-6-17 上午09:56:20  
 * 修改备注：  
 * @version 
 *
 */
public class DataImportZdsy {

	private String fxqNd;		// 分析期年度
	
	private String fxqSssqQ;	// 分析期起
	
	private String fxqSssqZ;	// 分析期止

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
