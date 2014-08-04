package com.tyut.sssy.decisionanalysis.domain;

import java.math.BigDecimal;

/**
 * 
 * 项目名称：sssy20120517
 * 类名称：TaxGrowTaxResourceDevelopIndexTable  
 * 类描述：税收增长与发展指数分析查询表  
 * 创建人：梁斌  
 * 创建时间：2012-5-22 下午09:05:48  
 * 修改人：梁斌  
 * 修改时间：2012-5-22 下午09:05:48  
 * 修改备注：  
 * @version 
 *
 */
public class TaxGrowTaxResourceDevelopIndexTable {

	private String nd;
	
	private String sqQ;
	
	private String sqZ;
	
	private BigDecimal sjss;	// 报表期实缴税收
	
	private BigDecimal zzl;		// 税收同比增长率
	
	private BigDecimal fzzs;	// 税源发展指数
	
	private BigDecimal txfx;		// 弹性分析
	
	private BigDecimal yysrzzl;		// 营业收入增长率
	
	private BigDecimal lrzezzl;			// 利润总额增长率
	
	private BigDecimal scjyzk;		// 生产经营状况指标值
	
	private BigDecimal yycbtx;		// 营业收入与营业成本成本弹性
	
	private BigDecimal yyfytx;			// 营业收入与营业费用成本弹性
	
	private BigDecimal jzcsrl;			// 净资产收入率变动率
	
	private BigDecimal mllbdl;			// 毛利率变动率
	
	private BigDecimal lssszzl;			// 零散税收、个体税收增长率

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getSqQ() {
		return sqQ;
	}

	public void setSqQ(String sqQ) {
		this.sqQ = sqQ;
	}

	public String getSqZ() {
		return sqZ;
	}

	public void setSqZ(String sqZ) {
		this.sqZ = sqZ;
	}

	public BigDecimal getSjss() {
		return sjss;
	}

	public void setSjss(BigDecimal sjss) {
		this.sjss = sjss;
	}

	public BigDecimal getZzl() {
		return zzl;
	}

	public void setZzl(BigDecimal zzl) {
		this.zzl = zzl;
	}

	public BigDecimal getFzzs() {
		return fzzs;
	}

	public void setFzzs(BigDecimal fzzs) {
		this.fzzs = fzzs;
	}

	public BigDecimal getTxfx() {
		return txfx;
	}

	public void setTxfx(BigDecimal txfx) {
		this.txfx = txfx;
	}

	public BigDecimal getYysrzzl() {
		return yysrzzl;
	}

	public void setYysrzzl(BigDecimal yysrzzl) {
		this.yysrzzl = yysrzzl;
	}

	public BigDecimal getLrzezzl() {
		return lrzezzl;
	}

	public void setLrzezzl(BigDecimal lrzezzl) {
		this.lrzezzl = lrzezzl;
	}

	public BigDecimal getScjyzk() {
		return scjyzk;
	}

	public void setScjyzk(BigDecimal scjyzk) {
		this.scjyzk = scjyzk;
	}

	public BigDecimal getYycbtx() {
		return yycbtx;
	}

	public void setYycbtx(BigDecimal yycbtx) {
		this.yycbtx = yycbtx;
	}

	public BigDecimal getYyfytx() {
		return yyfytx;
	}

	public void setYyfytx(BigDecimal yyfytx) {
		this.yyfytx = yyfytx;
	}

	public BigDecimal getJzcsrl() {
		return jzcsrl;
	}

	public void setJzcsrl(BigDecimal jzcsrl) {
		this.jzcsrl = jzcsrl;
	}

	public BigDecimal getMllbdl() {
		return mllbdl;
	}

	public void setMllbdl(BigDecimal mllbdl) {
		this.mllbdl = mllbdl;
	}

	public BigDecimal getLssszzl() {
		return lssszzl;
	}

	public void setLssszzl(BigDecimal lssszzl) {
		this.lssszzl = lssszzl;
	}
}
