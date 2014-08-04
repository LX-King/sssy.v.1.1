package com.tyut.sssy.task.domain;

import java.math.BigDecimal;

import com.tyut.sssy.base.domain.AnalysisIndex;
import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.Industry;
import com.tyut.sssy.base.domain.TaxPayer;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.User;

/**
 * 
 * 项目名称：sssy20120618
 * 类名称：MicroTask  
 * 类描述：微观任务类  
 * 创建人：梁斌  
 * 创建时间：2012-6-18 下午03:24:53  
 * 修改人：梁斌  
 * 修改时间：2012-6-18 下午03:24:53  
 * 修改备注：  
 * @version 
 *
 */
public class MicroTask {

	private int id;				// 任务id
	
	private String nsrsbh;		// 纳税人识别号
	
	private TaxPayer taxPayer;	// 纳税人对象
	
	private String swjgDm;		// 税务机关代码
	
	private TaxUnit taxUnit;	// 税务机关对象
	
	private String fxqNd;		// 分析期年度
	
	private String fxqSjq;		// 分析期时间起
	
	private String fxqSjz;		// 分析期时间止
	
	private String cyDm;		// 产业代码
	
	private Industry industry;	// 产业对象
	
	private String hydlDm;		// 行业大类代码
	
	private BigIndustry bigIndustry;	// 行业大类对象
	
	private String qyzclxDm;	// 产业注册类型代码
	
	private FirmRegType firmRegType;	// 产业注册类型对象
	
	private String fxzbDm;		// 分析指标代码
	
	private AnalysisIndex analysisIndex;		// 指标对象
	
	private String tzqj;		// 特征区间
	
	private IndexFeatureDB indexFeatureDB;	// 分析指标特征描述对象
	
	private int tzfz;			// 特征分值
	
	private BigDecimal bdl;		// 变动率
	
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
	
	private MicroTaskSurveyReport microTaskSurveyReport;	// 任务调查报告
	
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

	public String getCyDm() {
		return cyDm;
	}

	public void setCyDm(String cyDm) {
		this.cyDm = cyDm;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getHydlDm() {
		return hydlDm;
	}

	public void setHydlDm(String hydlDm) {
		this.hydlDm = hydlDm;
	}

	public BigIndustry getBigIndustry() {
		return bigIndustry;
	}

	public void setBigIndustry(BigIndustry bigIndustry) {
		this.bigIndustry = bigIndustry;
	}

	public String getQyzclxDm() {
		return qyzclxDm;
	}

	public void setQyzclxDm(String qyzclxDm) {
		this.qyzclxDm = qyzclxDm;
	}

	public FirmRegType getFirmRegType() {
		return firmRegType;
	}

	public void setFirmRegType(FirmRegType firmRegType) {
		this.firmRegType = firmRegType;
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

	public String getRwpjRq() {
		return rwpjRq;
	}

	public void setRwpjRq(String rwpjRq) {
		this.rwpjRq = rwpjRq;
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

	public TaxPayer getTaxPayer() {
		return taxPayer;
	}

	public void setTaxPayer(TaxPayer taxPayer) {
		this.taxPayer = taxPayer;
	}

	public int getTzfz() {
		return tzfz;
	}

	public void setTzfz(int tzfz) {
		this.tzfz = tzfz;
	}

	public MicroTaskSurveyReport getMicroTaskSurveyReport() {
		return microTaskSurveyReport;
	}

	public void setMicroTaskSurveyReport(MicroTaskSurveyReport microTaskSurveyReport) {
		this.microTaskSurveyReport = microTaskSurveyReport;
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

	public BigDecimal getBdl() {
		return bdl;
	}

	public void setBdl(BigDecimal bdl) {
		this.bdl = bdl;
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

	public BigDecimal getZfz() {
		return zfz;
	}

	public void setZfz(BigDecimal zfz) {
		this.zfz = zfz;
	}
}
