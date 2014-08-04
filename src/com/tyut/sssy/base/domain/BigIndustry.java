package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120506
 * 类名称：BigIndustry  
 * 类描述：行业大类  
 * 创建人：梁斌  
 * 创建时间：2012-5-10 下午07:46:21  
 * 修改人：梁斌  
 * 修改时间：2012-5-10 下午07:46:21  
 * 修改备注：  
 * @version 
 *
 */
public class BigIndustry {

	private String hydlDm;		// 行业大类代码
	
	private String mc;			// 名称

    private String jc ; //
	
	private String cyDm;		// ???

    private String zdhybz;

    private String gmlxdm;		// 规模类型代码

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc;
    }

    public String getHydlDm() {
		return hydlDm;
	}

	public void setHydlDm(String hydlDm) {
		this.hydlDm = hydlDm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getCyDm() {
		return cyDm;
	}

	public void setCyDm(String cyDm) {
		this.cyDm = cyDm;
	}

    public String getGmlxdm() {
        return gmlxdm;
    }

    public void setGmlxdm(String gmlxdm) {
        this.gmlxdm = gmlxdm;
    }

    public String getZdhybz() {
        return zdhybz;
    }

    public void setZdhybz(String zdhybz) {
        this.zdhybz = zdhybz;
    }
}
