package com.tyut.sssy.task.domain;

/**
 * ClassName:RiskMicroTaskExtractParameter
 * Function:风险微观任务的抽取参数
 * Author: LiuXiang
 * Date: 13-7-9
 * Mail:LXiang.tyut@gmail.com
 * Company:和信至诚
 */
public class RiskMicroTaskExtractParameter {

	private String fxqNd;	// 分析期年度
	
	private String fxqSjq;	// 分析期时间起
	
	private String fxqSjz;	// 分析期时间止
	
	private String swjgDm;	// 税务机关代码
	
	private String nsrsbh;	// 纳税人识别号
	
	private String hydlDm;	// 行业大类代码
	
	private String qyzclxDm;	// 企业注册类型代码
	
	/*private String fxzbDm;		// 分析指标代码*/
	
	private String rwztDm;		// 任务状态代码

    private String cyDm ; //产业代码
	
	private String type;		// 排序类型
	
	private String order;		// 排序（升，降）

	public String getFxqNd() {
		return fxqNd;
	}

	public void setFxqNd(String fxqNd) {
		this.fxqNd = fxqNd;
	}

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public String getHydlDm() {
		return hydlDm;
	}

	public void setHydlDm(String hydlDm) {
		this.hydlDm = hydlDm;
	}

	public String getQyzclxDm() {
		return qyzclxDm;
	}

	public void setQyzclxDm(String qyzclxDm) {
		this.qyzclxDm = qyzclxDm;
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

	public String getRwztDm() {
		return rwztDm;
	}

	public void setRwztDm(String rwztDm) {
		this.rwztDm = rwztDm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

    public String getCyDm() {
        return cyDm;
    }

    public void setCyDm(String cyDm) {
        this.cyDm = cyDm;
    }
}
