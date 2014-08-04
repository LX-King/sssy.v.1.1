package com.tyut.sssy.task.domain;

import com.tyut.sssy.sysmgr.domain.User;

/**
 * 
 * 项目名称：sssy20120713
 * 类名称：TaskTest  
 * 类描述：任务考核类  
 * 创建人：梁斌  
 * 创建时间：2012-7-20 下午08:51:53  
 * 修改人：梁斌  
 * 修改时间：2012-7-20 下午08:51:53  
 * 修改备注：  
 * @version 
 *
 */
public class TaskTest {

	private String rwkhDm;		// 任务考核代码
	
	private String sjQ;			// 任务执行时间起
	
	private String sjZ;			// 任务执行时间止
	
	private String khRq;		// 考核日期
	
	private String khSsq;		// 考核所属期
	
	private String bkhryDm;		// 被考核人员代码
	
	private User bkhry;			// 被考核人员对象
	
	private String khryDm;		// 考核人员代码
	
	private User khry;			// 考核人员
	
	private float rwjsDf;		// 任务接收得分
	
	private float rwfkDf;		// 任务反馈得分
	
	private float rwzxDf;		// 任务执行得分
	
	private String zhkhPj;		// 综合考核评价
	
	private float rwZdf;		// 任务总得分
	
	private String khrwLx;		// 考核任务类型

	public String getRwkhDm() {
		return rwkhDm;
	}

	public void setRwkhDm(String rwkhDm) {
		this.rwkhDm = rwkhDm;
	}


	public String getKhRq() {
		return khRq;
	}

	public void setKhRq(String khRq) {
		this.khRq = khRq;
	}

	public String getBkhryDm() {
		return bkhryDm;
	}

	public void setBkhryDm(String bkhryDm) {
		this.bkhryDm = bkhryDm;
	}

	public float getRwjsDf() {
		return rwjsDf;
	}

	public void setRwjsDf(float rwjsDf) {
		this.rwjsDf = rwjsDf;
	}

	public float getRwfkDf() {
		return rwfkDf;
	}

	public void setRwfkDf(float rwfkDf) {
		this.rwfkDf = rwfkDf;
	}

	public float getRwzxDf() {
		return rwzxDf;
	}

	public void setRwzxDf(float rwzxDf) {
		this.rwzxDf = rwzxDf;
	}

	public String getZhkhPj() {
		return zhkhPj;
	}

	public void setZhkhPj(String zhkhPj) {
		this.zhkhPj = zhkhPj;
	}

	public String getKhrwLx() {
		return khrwLx;
	}

	public void setKhrwLx(String khrwLx) {
		this.khrwLx = khrwLx;
	}

	public String getKhSsq() {
		return khSsq;
	}

	public void setKhSsq(String khSsq) {
		this.khSsq = khSsq;
	}

	public String getSjQ() {
		return sjQ;
	}

	public void setSjQ(String sjQ) {
		this.sjQ = sjQ;
	}

	public String getSjZ() {
		return sjZ;
	}

	public void setSjZ(String sjZ) {
		this.sjZ = sjZ;
	}

	public String getKhryDm() {
		return khryDm;
	}

	public void setKhryDm(String khryDm) {
		this.khryDm = khryDm;
	}

	public User getBkhry() {
		return bkhry;
	}

	public void setBkhry(User bkhry) {
		this.bkhry = bkhry;
	}

	public User getKhry() {
		return khry;
	}

	public void setKhry(User khry) {
		this.khry = khry;
	}

	public float getRwZdf() {
		return rwZdf;
	}

	public void setRwZdf(float rwZdf) {
		this.rwZdf = rwZdf;
	}
	
}