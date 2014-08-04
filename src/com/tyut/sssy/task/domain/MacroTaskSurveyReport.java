package com.tyut.sssy.task.domain;

import com.tyut.sssy.sysmgr.domain.User;

/**
 * 
 * 项目名称：sssy20120618
 * 类名称：MicroTaskSurveyReport  
 * 类描述：微观任务调查报告  
 * 创建人：梁斌  
 * 创建时间：2012-6-22 下午11:39:52  
 * 修改人：梁斌  
 * 修改时间：2012-6-22 下午11:39:52  
 * 修改备注：  
 * @version 
 *
 */
public class MacroTaskSurveyReport {

	private String rwbgDm;		// 任务报告代码
	
	private String fxqNd;		// 分析期年度
	
	private String fxqSjq;		// 分析期时间起
	
	private String fxqSjz;		// 分析期时间止
	
	private String fxzblxMx;	// 分析指标类型明细
	
	private String flMc;		// 分量名称
	
	private String fxzbDm;		// 分析指标代码
	
	private String tzqj;		// 特征区间
	
	private String jbqk;		// 基本情况
	
	private String dcqk;		// 调查情况
	
	private String czwt;		// 存在问题
	
	private String lscs;		// 落实措施
	
	private String rwfbryDm;	// 任务发布人员代码
	
	private User rwfbry;		// 任务发布人员对象
	
	private String rwzxryDm;	// 任务执行人员代码
	
	private User rwzxry;		// 任务执行人员对象
	
	private String dcrqQ;		// 调查日期起
	
	private String dcrqZ;		// 调查日期止
	
	private String sjfkRq;		// 实际反馈日期

	public String getRwbgDm() {
		return rwbgDm;
	}

	public void setRwbgDm(String rwbgDm) {
		this.rwbgDm = rwbgDm;
	}

	public String getFxqNd() {
		return fxqNd;
	}

	public void setFxqNd(String fxqNd) {
		this.fxqNd = fxqNd;
	}

	public String getFxqSjq() {
		return fxqSjq;
	}

	public void setFxqSjq(String fxqSjq) {
		this.fxqSjq = fxqSjq;
	}

	public String getFxqSjz() {
		return fxqSjz;
	}

	public void setFxqSjz(String fxqSjz) {
		this.fxqSjz = fxqSjz;
	}

	public String getFxzblxMx() {
		return fxzblxMx;
	}

	public void setFxzblxMx(String fxzblxMx) {
		this.fxzblxMx = fxzblxMx;
	}

	public String getFlMc() {
		return flMc;
	}

	public void setFlMc(String flMc) {
		this.flMc = flMc;
	}

	public String getFxzbDm() {
		return fxzbDm;
	}

	public void setFxzbDm(String fxzbDm) {
		this.fxzbDm = fxzbDm;
	}

	public String getTzqj() {
		return tzqj;
	}

	public void setTzqj(String tzqj) {
		this.tzqj = tzqj;
	}

	public String getJbqk() {
		return jbqk;
	}

	public void setJbqk(String jbqk) {
		this.jbqk = jbqk;
	}

	public String getDcqk() {
		return dcqk;
	}

	public void setDcqk(String dcqk) {
		this.dcqk = dcqk;
	}

	public String getCzwt() {
		return czwt;
	}

	public void setCzwt(String czwt) {
		this.czwt = czwt;
	}

	public String getLscs() {
		return lscs;
	}

	public void setLscs(String lscs) {
		this.lscs = lscs;
	}

	public String getRwfbryDm() {
		return rwfbryDm;
	}

	public void setRwfbryDm(String rwfbryDm) {
		this.rwfbryDm = rwfbryDm;
	}

	public User getRwfbry() {
		return rwfbry;
	}

	public void setRwfbry(User rwfbry) {
		this.rwfbry = rwfbry;
	}

	public String getRwzxryDm() {
		return rwzxryDm;
	}

	public void setRwzxryDm(String rwzxryDm) {
		this.rwzxryDm = rwzxryDm;
	}

	public User getRwzxry() {
		return rwzxry;
	}

	public void setRwzxry(User rwzxry) {
		this.rwzxry = rwzxry;
	}

	public String getDcrqQ() {
		return dcrqQ;
	}

	public void setDcrqQ(String dcrqQ) {
		this.dcrqQ = dcrqQ;
	}

	public String getDcrqZ() {
		return dcrqZ;
	}

	public void setDcrqZ(String dcrqZ) {
		this.dcrqZ = dcrqZ;
	}

	public String getSjfkRq() {
		return sjfkRq;
	}

	public void setSjfkRq(String sjfkRq) {
		this.sjfkRq = sjfkRq;
	}
}
