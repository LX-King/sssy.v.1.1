package com.tyut.sssy.report.domain;

import java.math.BigDecimal;

import com.tyut.sssy.base.domain.TaxPayer;

/**
 * 
 * 项目名称：sssy20120729
 * 类名称：FirmTaxCollectionSituation  
 * 类描述：重点税源大户实现税收情况  
 * 创建人：梁斌  
 * 创建时间：2012-8-2 下午01:44:46  
 * 修改人：梁斌  
 * 修改时间：2012-8-2 下午01:44:46  
 * 修改备注：  
 * @version 
 *
 */
public class FirmTaxCollectionSituation {

	private String nsrsbh;		// 纳税人识别号
	
	private TaxPayer taxPayer;	// 纳税人对象
	
	private BigDecimal bqYjss;	// 本期应缴税收
	
	private BigDecimal bqSjss;	// 本期实缴税收
	
	private BigDecimal bqQjss;	// 本期欠缴税收
	
	private BigDecimal bnYjss;	// 本年应缴税收
	
	private BigDecimal bnSjss;	// 本年实缴税收
	
	private BigDecimal bnQjss;	// 本年欠缴税收

	public String getNsrsbh() {
		return nsrsbh;
	}

	public void setNsrsbh(String nsrsbh) {
		this.nsrsbh = nsrsbh;
	}

	public TaxPayer getTaxPayer() {
		return taxPayer;
	}

	public void setTaxPayer(TaxPayer taxPayer) {
		this.taxPayer = taxPayer;
	}

	public BigDecimal getBqYjss() {
		return bqYjss;
	}

	public void setBqYjss(BigDecimal bqYjss) {
		this.bqYjss = bqYjss;
	}

	public BigDecimal getBqSjss() {
		return bqSjss;
	}

	public void setBqSjss(BigDecimal bqSjss) {
		this.bqSjss = bqSjss;
	}

	public BigDecimal getBqQjss() {
		return bqQjss;
	}

	public void setBqQjss(BigDecimal bqQjss) {
		this.bqQjss = bqQjss;
	}

	public BigDecimal getBnYjss() {
		return bnYjss;
	}

	public void setBnYjss(BigDecimal bnYjss) {
		this.bnYjss = bnYjss;
	}

	public BigDecimal getBnSjss() {
		return bnSjss;
	}

	public void setBnSjss(BigDecimal bnSjss) {
		this.bnSjss = bnSjss;
	}

	public BigDecimal getBnQjss() {
		return bnQjss;
	}

	public void setBnQjss(BigDecimal bnQjss) {
		this.bnQjss = bnQjss;
	}
}
