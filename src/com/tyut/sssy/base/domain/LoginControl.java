package com.tyut.sssy.base.domain;

/**
 * 
 * 项目名称：sssy20120805
 * 类名称：AccessControl
 * 类描述：登录控制类
 * 创建人：Robin
 * 创建时间：2012-8-13 下午04:34:08
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version
 *
 */
public class LoginControl {
	
	private String swjgDm;	// 税务机关代码
	
	private String yyDz;	// 数据库地址
	
	private String ipQ;		// IP地址起
	
	private String ipZ;		// IP地址止
	
	private String mc;		// 名称 

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public String getYyDz() {
		return yyDz;
	}

	public void setYyDz(String yyDz) {
		this.yyDz = yyDz;
	}

	public String getIpQ() {
		return ipQ;
	}

	public void setIpQ(String ipQ) {
		this.ipQ = ipQ;
	}

	public String getIpZ() {
		return ipZ;
	}

	public void setIpZ(String ipZ) {
		this.ipZ = ipZ;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}
}
