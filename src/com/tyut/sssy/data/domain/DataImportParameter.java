package com.tyut.sssy.data.domain;

/**
 * 
 * 项目名称：sssy20120419
 * 类名称：SoftwareData  
 * 类描述：数据导入参数类  
 * 创建人：梁斌  
 * 创建时间：2012-4-19 下午08:04:12  
 * 修改人：梁斌  
 * 修改时间：2012-4-19 下午08:04:12  
 * 修改备注：  
 * @version 
 *
 */
public class DataImportParameter {

	private String fxqNd;	// 分析期年度
	
	private String fxqYf;	// 分析期月份
	
	private String fxqSssqQ;	// 分析期所属时期起
	
	private String fxqSssqZ;	// 分析期所属时期止
	
	private String pd;			// 频度
	
	private String sxdmbz;	// 刷新代码标志
	
	private String sxdjxx;	// 刷新登记信息
	
	private String rkbz;	// 入库税收标志
	
	private String sjbz;	// 实缴税收标志
	
	private String djbz;	// 待解税收标志
	
	private String qjbz;	// 欠缴税收标志

	public DataImportParameter() {
		this.fxqNd = "";
		this.fxqYf = "";
		this.fxqSssqQ = "";
		this.fxqSssqZ = "";
		this.pd = "";
		this.sxdmbz = "";
		this.sxdjxx = "";
		this.rkbz = "";
		this.sjbz = "";
		this.djbz = "";
		this.qjbz = "";
	}
	
	public String getFxqNd() {
		return fxqNd;
	}

	public void setFxqNd(String fxqNd) {
		this.fxqNd = fxqNd;
	}

	public String getFxqYf() {
		return fxqYf;
	}

	public void setFxqYf(String fxqYf) {
		this.fxqYf = fxqYf;
	}

	public String getFxqSssqQ() {
		return fxqSssqQ;
	}

	public void setFxqSssqQ(String fxqSssqQ) {
		this.fxqSssqQ = fxqSssqQ;
	}

	public String getFxqSssqZ() {
		return fxqSssqZ;
	}

	public void setFxqSssqZ(String fxqSssqZ) {
		this.fxqSssqZ = fxqSssqZ;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getSxdmbz() {
		return sxdmbz;
	}

	public void setSxdmbz(String sxdmbz) {
		this.sxdmbz = sxdmbz;
	}

	public String getSxdjxx() {
		return sxdjxx;
	}

	public void setSxdjxx(String sxdjxx) {
		this.sxdjxx = sxdjxx;
	}

	public String getRkbz() {
		return rkbz;
	}

	public void setRkbz(String rkbz) {
		this.rkbz = rkbz;
	}

	public String getSjbz() {
		return sjbz;
	}

	public void setSjbz(String sjbz) {
		this.sjbz = sjbz;
	}

	public String getDjbz() {
		return djbz;
	}

	public void setDjbz(String djbz) {
		this.djbz = djbz;
	}

	public String getQjbz() {
		return qjbz;
	}

	public void setQjbz(String qjbz) {
		this.qjbz = qjbz;
	}
	
}
