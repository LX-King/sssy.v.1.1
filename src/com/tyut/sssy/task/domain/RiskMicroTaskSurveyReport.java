package com.tyut.sssy.task.domain;

import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.sysmgr.domain.User;

import java.math.BigDecimal;

/**
 * ClassName:RiskMicroTaskSurveyReport
 * Function:微观任务 报告
 * Author: LiuXiang
 * Date: 13-7-13
 * Mail:LXiang.tyut@gmail.com
 * Company:
 *
 */
public class RiskMicroTaskSurveyReport {

	private String rwbgDm;		// 任务报告代码
	
	private String fxqNd;		// 分析期年度
	
	private String fxqSjq;		// 分析期时间起
	
	private String fxqSjz;		// 分析期时间止
	
	private String nsrsbh;		// 纳税人识别号

    private BigDecimal fxz;
	
	private TaxPayer taxPayer;	// 纳税人对象
	

	private String jbqk;		// 基本情况
	
	private String dcqk;		// 调查情况
	
	private String ytfx;		// 约谈分析

    private String xgfx ;       //评估分析
	
	private String lscs;		// 落实措施
	
	private String rwfbryDm;	// 任务发布人员代码
	
	private User rwfbry;		// 任务发布人员对象
	
	private String rwzxryDm;	// 任务执行人员代码
	
	private User rwzxry;		// 任务执行人员对象

    private String rwshryMc   ;// 审核人员名称

    private String rwshryDm ;   //审核人员代码
	
	private String dcrqQ;		// 调查日期起
	
	private String dcrqZ;		// 调查日期止
	
	private String sjfkRq;		// 实际反馈日期

    private String jbjb ;

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

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
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

    public String getYtfx() {
        return ytfx;
    }

    public void setYtfx(String ytfx) {
        this.ytfx = ytfx;
    }

    public String getXgfx() {
        return xgfx;
    }

    public void setXgfx(String xgfx) {
        this.xgfx = xgfx;
    }

    public String getLscs() {
		return lscs;
	}

	public void setLscs(String lscs) {
		this.lscs = lscs;
	}

	public String getRwzxryDm() {
		return rwzxryDm;
	}

	public void setRwzxryDm(String rwzxryDm) {
		this.rwzxryDm = rwzxryDm;
	}

	public String getSjfkRq() {
		return sjfkRq;
	}

	public void setSjfkRq(String sjfkRq) {
		this.sjfkRq = sjfkRq;
	}

	public String getRwfbryDm() {
		return rwfbryDm;
	}

	public void setRwfbryDm(String rwfbryDm) {
		this.rwfbryDm = rwfbryDm;
	}

	public TaxPayer getTaxPayer() {
		return taxPayer;
	}

	public void setTaxPayer(TaxPayer taxPayer) {
		this.taxPayer = taxPayer;
	}

	public User getRwfbry() {
		return rwfbry;
	}

	public void setRwfbry(User rwfbry) {
		this.rwfbry = rwfbry;
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


    public BigDecimal getFxz() {
        return fxz;
    }

    public void setFxz(BigDecimal fxz) {
        this.fxz = fxz;
    }

    public String getJbjb() {
        return jbjb;
    }

    public void setJbjb(String jbjb) {
        this.jbjb = jbjb;
    }

    public String getRwshryMc() {
        return rwshryMc;
    }

    public void setRwshryMc(String rwshryMc) {
        this.rwshryMc = rwshryMc;
    }


    public String getRwshryDm() {
        return rwshryDm;
    }

    public void setRwshryDm(String rwshryDm) {
        this.rwshryDm = rwshryDm;
    }
}
