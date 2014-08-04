package com.tyut.sssy.taxburden.domain;

import java.math.BigDecimal;

public class TaxBurdenAnalysisSearchTable {

	private String dm;	// 纳税人编码
	
	private String mc;	// 纳税人名称
	
	private BigDecimal sshj1;
	
	private BigDecimal dqsf;
	
	private BigDecimal tqsf;
	
	private BigDecimal sfbdl;

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public BigDecimal getSshj1() {
		return sshj1;
	}

	public void setSshj1(BigDecimal sshj1) {
		this.sshj1 = sshj1;
	}

	public BigDecimal getDqsf() {
		return dqsf;
	}

	public void setDqsf(BigDecimal dqsf) {
		this.dqsf = dqsf;
	}

	public BigDecimal getTqsf() {
		return tqsf;
	}

	public void setTqsf(BigDecimal tqsf) {
		this.tqsf = tqsf;
	}

	public BigDecimal getSfbdl() {
		return sfbdl;
	}

	public void setSfbdl(BigDecimal sfbdl) {
		this.sfbdl = sfbdl;
	}
}
