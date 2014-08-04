package com.tyut.sssy.task.domain;

/**
 * 
 * 项目名称：sssy20120713
 * 类名称：TaskThreshold  
 * 类描述：任务阈值  
 * 创建人：梁斌  
 * 创建时间：2012-7-20 下午03:19:09  
 * 修改人：梁斌  
 * 修改时间：2012-7-20 下午03:19:09  
 * 修改备注：  
 * @version 
 *
 */
public class TaskThreshold {

	private String rwLx;	// 任务类型
	
	private int rwYz;		// 任务阈值

	public String getRwLx() {
		return rwLx;
	}

	public void setRwLx(String rwLx) {
		this.rwLx = rwLx;
	}

	public int getRwYz() {
		return rwYz;
	}

	public void setRwYz(int rwYz) {
		this.rwYz = rwYz;
	}
	
}
