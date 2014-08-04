package com.tyut.sssy.taxcollection.domain;

import java.math.BigDecimal;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：TaxCollectionFinishTable  
 * 类描述：税收收入结构变化表
 * 创建人：梁斌  
 * 创建时间：2012-5-9 下午03:08:46  
 * 修改人：梁斌  
 * 修改时间：2012-5-9 下午03:08:46  
 * 修改备注：  
 * @version 
 *
 */
public class TaxCollectionStructureChangeTable {

	private String xm;	// 项目列
	
	private BigDecimal a1;
	
	private BigDecimal a2;
	
	private BigDecimal a3;
	
	private BigDecimal a4;
	
	private BigDecimal a5;
	
	private String fl;
	
	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public BigDecimal getA1() {
		return a1;
	}

	public void setA1(BigDecimal a1) {
		this.a1 = a1;
	}

	public BigDecimal getA2() {
		return a2;
	}

	public void setA2(BigDecimal a2) {
		this.a2 = a2;
	}

	public BigDecimal getA3() {
		return a3;
	}

	public void setA3(BigDecimal a3) {
		this.a3 = a3;
	}

	public BigDecimal getA4() {
		return a4;
	}

	public void setA4(BigDecimal a4) {
		this.a4 = a4;
	}

	public BigDecimal getA5() {
		return a5;
	}

	public void setA5(BigDecimal a5) {
		this.a5 = a5;
	}

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}
}
