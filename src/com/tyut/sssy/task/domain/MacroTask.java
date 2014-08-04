package com.tyut.sssy.task.domain;

import java.math.BigDecimal;

import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.User;

/**
 * 
 * 项目名称：sssy20120618
 * 类名称：MacroTask  
 * 类描述：总/分量任务类  
 * 创建人：梁斌  
 * 创建时间：2012-6-18 下午03:24:53  
 * 修改人：梁斌  
 * 修改时间：2012-6-18 下午03:24:53  
 * 修改备注：  
 * @version 
 *
 */
public class MacroTask {

	private int id;				// 任务id
	
	private String nsrsbh;		// 纳税人识别号
	
	private TaxPayer taxPayer;	// 纳税人对象
	
	private String swjgDm;		// 税务机关代码
	
	private TaxUnit taxUnit;	// 税务机关对象
	
	private String fxqNd;		// 分析期年度
	
	private String fxqSjq;		// 分析期时间起
	
	private String fxqSjz;		// 分析期时间止
	
	private String fxzbDm;		// 分析指标代码
	
	private AnalysisIndex analysisIndex;		// 指标对象
	
	private String fxzblxMx;	// 分析指标类型明细
	
	private String fxzblxMxDm;	// 分析指标类型明细代码
	
	private String flMc;		// 分量名称
	
	private String tzqj;		// 特征区间
	
	private IndexFeatureDB indexFeatureDB;	// 分析指标特征描述对象
	
	private BigDecimal bdl;		// 变动率
	
	private int tzfz;			// 特征分值
	
	private BigDecimal zfz;			// 总分值
	
	private String rwztDm;		// 任务状态代码
	
	private TaskState taskState;	// 任务状态对象
	
	private String rwtqryDm;	// 任务提取人员代码
	
	private User rwtqry;		// 任务提取人员
	
	private String rwtqRq;		// 任务提取日期
	
	private String rwfbryDm;	// 任务发布人员代码
	
	private User rwfbry;		// 任务发布人员
	
	private String rwfbRq;		// 任务发布日期
	
	private String rwzxryDm;	// 任务执行人员代码
	
	private User rwzxry;		// 任务执行人员
	
	private String bzjsRq;		// 标准接收日期
	
	private String sjjsRq;		// 实际接收日期
	
	private String bzfkRq;		// 标准反馈日期
	
	private String sjfkRq;		// 实际反馈日期
	
	private String rwbgDm;		// 任务报告代码
	
	private MacroTaskSurveyReport macroTaskSurveyReport;	// 任务调查报告
	
	private String glpj;		// 管理评价
	
	private String zfpj;		// 执法评价
	
	private String zhpj;		// 综合评价
	
	private String pjryDm;		// 评价人员代码
	
	private User pjry;			// 评价人员对象
	
	private String rwpjRq;		// 任务评价日期
	
	private int rwjsDf;			// 任务接收得分
	
	private int rwfkDf;			// 任务反馈得分
	
	private int rwzxDf;			// 任务执行得分

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getSwjgDm() {
		return swjgDm;
	}

	public void setSwjgDm(String swjgDm) {
		this.swjgDm = swjgDm;
	}

	public TaxUnit getTaxUnit() {
		return taxUnit;
	}

	public void setTaxUnit(TaxUnit taxUnit) {
		this.taxUnit = taxUnit;
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

	public String getFxzbDm() {
		return fxzbDm;
	}

	public void setFxzbDm(String fxzbDm) {
		this.fxzbDm = fxzbDm;
	}

	public AnalysisIndex getAnalysisIndex() {
		return analysisIndex;
	}

	public void setAnalysisIndex(AnalysisIndex analysisIndex) {
		this.analysisIndex = analysisIndex;
	}

	public String getFxzblxMx() {
		return fxzblxMx;
	}

	public void setFxzblxMx(String fxzblxMx) {
		this.fxzblxMx = fxzblxMx;
	}

	public String getFxzblxMxDm() {
		return fxzblxMxDm;
	}

	public void setFxzblxMxDm(String fxzblxMxDm) {
		this.fxzblxMxDm = fxzblxMxDm;
	}

	public String getFlMc() {
		return flMc;
	}

	public void setFlMc(String flMc) {
		this.flMc = flMc;
	}

	public String getTzqj() {
		return tzqj;
	}

	public void setTzqj(String tzqj) {
		this.tzqj = tzqj;
	}

	public IndexFeatureDB getIndexFeatureDB() {
		return indexFeatureDB;
	}

	public void setIndexFeatureDB(IndexFeatureDB indexFeatureDB) {
		this.indexFeatureDB = indexFeatureDB;
	}

	public BigDecimal getBdl() {
		return bdl;
	}

	public void setBdl(BigDecimal bdl) {
		this.bdl = bdl;
	}

	public int getTzfz() {
		return tzfz;
	}

	public void setTzfz(int tzfz) {
		this.tzfz = tzfz;
	}

	public BigDecimal getZfz() {
		return zfz;
	}

	public void setZfz(BigDecimal zfz) {
		this.zfz = zfz;
	}

	public String getRwztDm() {
		return rwztDm;
	}

	public void setRwztDm(String rwztDm) {
		this.rwztDm = rwztDm;
	}

	public TaskState getTaskState() {
		return taskState;
	}

	public void setTaskState(TaskState taskState) {
		this.taskState = taskState;
	}

	public String getRwtqryDm() {
		return rwtqryDm;
	}

	public void setRwtqryDm(String rwtqryDm) {
		this.rwtqryDm = rwtqryDm;
	}

	public User getRwtqry() {
		return rwtqry;
	}

	public void setRwtqry(User rwtqry) {
		this.rwtqry = rwtqry;
	}

	public String getRwtqRq() {
		return rwtqRq;
	}

	public void setRwtqRq(String rwtqRq) {
		this.rwtqRq = rwtqRq;
	}

	public String getRwfbryDm() {
		return rwfbryDm;
	}

	public void setRwfbryDm(String rwfbryDm) {
		this.rwfbryDm = rwfbryDm;
	}

	public User getRwfbry() {
		return rwfbry;
	}

	public void setRwfbry(User rwfbry) {
		this.rwfbry = rwfbry;
	}

	public String getRwfbRq() {
		return rwfbRq;
	}

	public void setRwfbRq(String rwfbRq) {
		this.rwfbRq = rwfbRq;
	}

	public String getRwzxryDm() {
		return rwzxryDm;
	}

	public void setRwzxryDm(String rwzxryDm) {
		this.rwzxryDm = rwzxryDm;
	}

	public User getRwzxry() {
		return rwzxry;
	}

	public void setRwzxry(User rwzxry) {
		this.rwzxry = rwzxry;
	}

	public String getBzjsRq() {
		return bzjsRq;
	}

	public void setBzjsRq(String bzjsRq) {
		this.bzjsRq = bzjsRq;
	}

	public String getSjjsRq() {
		return sjjsRq;
	}

	public void setSjjsRq(String sjjsRq) {
		this.sjjsRq = sjjsRq;
	}

	public String getBzfkRq() {
		return bzfkRq;
	}

	public void setBzfkRq(String bzfkRq) {
		this.bzfkRq = bzfkRq;
	}

	public String getSjfkRq() {
		return sjfkRq;
	}

	public void setSjfkRq(String sjfkRq) {
		this.sjfkRq = sjfkRq;
	}

	public String getRwbgDm() {
		return rwbgDm;
	}

	public void setRwbgDm(String rwbgDm) {
		this.rwbgDm = rwbgDm;
	}

	public MacroTaskSurveyReport getMacroTaskSurveyReport() {
		return macroTaskSurveyReport;
	}

	public void setMacroTaskSurveyReport(MacroTaskSurveyReport macroTaskSurveyReport) {
		this.macroTaskSurveyReport = macroTaskSurveyReport;
	}

	public String getGlpj() {
		return glpj;
	}

	public void setGlpj(String glpj) {
		this.glpj = glpj;
	}

	public String getZfpj() {
		return zfpj;
	}

	public void setZfpj(String zfpj) {
		this.zfpj = zfpj;
	}

	public String getZhpj() {
		return zhpj;
	}

	public void setZhpj(String zhpj) {
		this.zhpj = zhpj;
	}

	public String getPjryDm() {
		return pjryDm;
	}

	public void setPjryDm(String pjryDm) {
		this.pjryDm = pjryDm;
	}

	public User getPjry() {
		return pjry;
	}

	public void setPjry(User pjry) {
		this.pjry = pjry;
	}

	public String getRwpjRq() {
		return rwpjRq;
	}

	public void setRwpjRq(String rwpjRq) {
		this.rwpjRq = rwpjRq;
	}

	public int getRwjsDf() {
		return rwjsDf;
	}

	public void setRwjsDf(int rwjsDf) {
		this.rwjsDf = rwjsDf;
	}

	public int getRwfkDf() {
		return rwfkDf;
	}

	public void setRwfkDf(int rwfkDf) {
		this.rwfkDf = rwfkDf;
	}

	public int getRwzxDf() {
		return rwzxDf;
	}

	public void setRwzxDf(int rwzxDf) {
		this.rwzxDf = rwzxDf;
	}
}
