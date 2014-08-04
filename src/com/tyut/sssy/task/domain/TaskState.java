package com.tyut.sssy.task.domain;

/**
 * 
 * 项目名称：sssy20120618
 * 类名称：TaskState  
 * 类描述：任务状态  
 * 创建人：梁斌  
 * 创建时间：2012-6-18 下午03:38:32  
 * 修改人：梁斌  
 * 修改时间：2012-6-18 下午03:38:32  
 * 修改备注：  
 * @version 
 *
 */
public class TaskState {

	private String rwztDm;	// 任务状态代码
	
	private String rwztMc;	// 任务状态名称

	public String getRwztDm() {
		return rwztDm;
	}

	public void setRwztDm(String rwztDm) {
		this.rwztDm = rwztDm;
	}

	public String getRwztMc() {
		return rwztMc;
	}

	public void setRwztMc(String rwztMc) {
		this.rwztMc = rwztMc;
	}
}
