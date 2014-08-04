package com.tyut.sssy.task.service;

import com.tyut.sssy.base.service.*;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.MicroTaskDao;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.domain.MicroTaskExtractParameter;

import java.util.List;

public class MicroTaskService {

	/**
	 * 获取任务提取列表
	 * @param microTaskExtractParameter
	 * @return
	 */
	public List<MicroTask> getExtractMicroTaskList(
			MicroTaskExtractParameter microTaskExtractParameter) {
		
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getExtractMicroTaskList(microTaskExtractParameter);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

	public void add(MicroTask microTask) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		microTaskDao.add(microTask);
	}

	/**
	 * 按分析期删除记录
	 * @param dataCalcParameter
	 */
	public void deleteByFxq(DataCalcParameter dataCalcParameter) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		microTaskDao.deleteByFxq(dataCalcParameter);
		
	}

    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter){
        MicroTaskDao microTaskDao = new MicroTaskDao();
        microTaskDao.deleteByFxqAndZt(dataCalcParameter);
    }

    public boolean isExist(MicroTask microTask){
        MicroTaskDao dao = new MicroTaskDao();
        return dao.isExist(microTask);
    }

	/**
	 * 根据id获取任务对象
	 * @param id
	 * @return
	 */
	public MicroTask getMicroTaskById(int id) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		MicroTask microTask = microTaskDao.getMicroTaskById(id);
		
		if (null != microTask) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
			microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
			microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
			microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
			microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
			microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
			microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
			microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
			microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
			microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
			microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
			microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
			microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			
		}
		
		return microTask;
	}

	/**
	 * 更新任务对象
	 * @param microTask
	 */
	public void update(MicroTask microTask) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		microTaskDao.update(microTask);
	}

	/**
	 * 根据执行人员代码 和 状态代码 查询微观任务列表
	 * @param rwzxryDm
	 * @param rwzrDm
	 * @return
	 */
	public List<MicroTask> getMicroTaskListByZxryAndRwzt(String rwzxryDm,
			String rwzrDm) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

	/**
	 * 根据任务状态获取任务列表
	 * @param rwztDm
	 * @return
	 */
	public List<MicroTask> getMicroTaskListByRwzt(String rwztDm) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getMicroTaskListByRwzt(rwztDm);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

	/**
	 * 根据发布人员 和 任务状态获得任务列表
	 * @param rwfbryDm
	 * @param rwztDm
	 * @return
	 */
	public List<MicroTask> getMicroTaskListByFbryAndRwzt(String rwfbryDm,
			String rwztDm) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

	/**
	 * 根据反馈时间起止 任务状态 任务执行人员 获得任务列表
	 * @param sjQ
	 * @param sjZ
	 * @param rwztDm
	 * @param rwzxryDm
	 * @return
	 */
	public List<MicroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
			String sjZ, String rwztDm, String rwzxryDm) {
		
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getListByRwfkRqAndRwztAndRwzxryDm(sjQ, sjZ, rwztDm, rwzxryDm);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

	/**
	 * 根据管理机关和任务状态获取获取任务列表
	 * @param swjgDm
	 * @param rwztDm
	 * @return
	 */
	public List<MicroTask> getListBySwjgAndRwzt(String swjgDm, String rwztDm) {
		MicroTaskDao microTaskDao = new MicroTaskDao();
		List<MicroTask> microTaskList = microTaskDao.getListBySwjgAndRwzt(swjgDm, rwztDm);
		
		if (microTaskList.size() != 0) {
			
			IndustryService industryService = new IndustryService();
			BigIndustryService bigIndustryService = new BigIndustryService();
			FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
			AnalysisIndexService analysisIndexService = new AnalysisIndexService();
			IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
			TaskStateService taskStateService = new TaskStateService();
			UserService userService = new UserService();
			TaxUnitService taxUnitService = new TaxUnitService();
			TaxPayerService taxPayerService = new TaxPayerService();
			MicroTaskSurveyReportService microTaskSurveyReportService = new MicroTaskSurveyReportService();
			
			for (MicroTask microTask : microTaskList) {
				microTask.setTaxUnit(taxUnitService.getTaxUnitById(microTask.getSwjgDm()));
				microTask.setIndustry(industryService.getIndustryById(microTask.getCyDm()));
				microTask.setBigIndustry(bigIndustryService.getBigIndustryById(microTask.getHydlDm()));
				microTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(microTask.getQyzclxDm()));
				microTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(microTask.getFxzbDm()));
				microTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(microTask.getFxzbDm(), microTask.getTzqj()));
				microTask.setTaskState(taskStateService.getTaskStateById(microTask.getRwztDm()));
				microTask.setRwtqry(userService.getByCode(microTask.getRwtqryDm()));
				microTask.setRwfbry(userService.getByCode(microTask.getRwfbryDm()));
				microTask.setRwzxry(userService.getByCode(microTask.getRwzxryDm()));
				microTask.setTaxPayer(taxPayerService.getTaxPayerByCode(microTask.getNsrsbh()));
				microTask.setMicroTaskSurveyReport(microTaskSurveyReportService.getSurveyReportById(microTask.getRwbgDm()));
				microTask.setPjry(userService.getByCode(microTask.getPjryDm()));
			}
		}
		
		return microTaskList;
	}

}
