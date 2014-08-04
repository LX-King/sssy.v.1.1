package com.tyut.sssy.report.domain;

import java.math.BigDecimal;

/**
 * 
 * 项目名称：sssy20120729
 * 类名称：BigIndustryTaxCollectionSituation  
 * 类描述：主要行业税收完成情况  
 * 创建人：梁斌  
 * 创建时间：2012-8-4 下午01:00:46  
 * 修改人：梁斌  
 * 修改时间：2012-8-4 下午01:00:46  
 * 修改备注：  
 * @version 
 *
 */
public class BigIndustryTaxCollectionSituation {

	private String hydlMc;	// 行业大类名称
	
	private BigDecimal wcsr;	// 完成收入
	
	private BigDecimal zsntq;	// 占上年同期
	
	private BigDecimal zl;		// 增量

	public String getHydlMc() {
		return hydlMc;
	}

	public void setHydlMc(String hydlMc) {
		this.hydlMc = hydlMc;
	}

	public BigDecimal getWcsr() {
		return wcsr;
	}

	public void setWcsr(BigDecimal wcsr) {
		this.wcsr = wcsr;
	}

	public BigDecimal getZsntq() {
		return zsntq;
	}

	public void setZsntq(BigDecimal zsntq) {
		this.zsntq = zsntq;
	}

	public BigDecimal getZl() {
		return zl;
	}

	public void setZl(BigDecimal zl) {
		this.zl = zl;
	}
}
