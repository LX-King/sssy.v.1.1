package com.tyut.sssy.task.service;

import com.tyut.sssy.base.service.*;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.RiskMicroTaskDao;
import com.tyut.sssy.task.domain.RiskMicroTask;
import com.tyut.sssy.task.domain.RiskMicroTaskExtractParameter;

import java.util.List;

public class RiskMicroTaskService {

    /**
     * 对获取的riskMicroTaskList 封装相应的对象，类Hibernate功能
     *
     * @param riskMicroTask
     */
    private void reMapping(RiskMicroTask riskMicroTask) {
        IndustryService industryService = new IndustryService();
        BigIndustryService bigIndustryService = new BigIndustryService();
        FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
        TaskStateService taskStateService = new TaskStateService();
        UserService userService = new UserService();
        TaxUnitService taxUnitService = new TaxUnitService();
        TaxPayerService taxPayerService = new TaxPayerService();
        RiskMicroTaskSurveyReportService riskMicroTaskSurveyReportService = new RiskMicroTaskSurveyReportService();
        riskMicroTask.setTaxUnit(taxUnitService.getTaxUnitById(riskMicroTask.getZgSwjgDm()));
        if (riskMicroTask.getCyDm() != null && !riskMicroTask.getCyDm().equals(""))
            riskMicroTask.setIndustry(industryService.getIndustryById(riskMicroTask.getCyDm()));
        if (riskMicroTask.getHydlDm() != null && !riskMicroTask.getHydlDm().equals(""))
            riskMicroTask.setBigIndustry(bigIndustryService.getBigIndustryById(riskMicroTask.getHydlDm()));
        if (riskMicroTask.getQyzclxDm() != null && !riskMicroTask.getQyzclxDm().equals(""))
            riskMicroTask.setFirmRegType(firmRegTypeService.getFirmRegTypeById(riskMicroTask.getQyzclxDm()));
        riskMicroTask.setTaskState(taskStateService.getTaskStateById(riskMicroTask.getRwztDm()));
        if (riskMicroTask.getRwtqryDm() != null && !riskMicroTask.getRwtqryDm().equals(""))
            riskMicroTask.setRwtqry(userService.getByCode(riskMicroTask.getRwtqryDm()));
        if (riskMicroTask.getRwfbryDm() != null && !riskMicroTask.getRwfbryDm().equals(""))
            riskMicroTask.setRwfbry(userService.getByCode(riskMicroTask.getRwfbryDm()));
        if (riskMicroTask.getRwzxryDm() != null && !riskMicroTask.getRwzxryDm().equals(""))
            riskMicroTask.setRwzxry(userService.getByCode(riskMicroTask.getRwzxryDm()));
        if (taxPayerService.getTaxPayerByCode(riskMicroTask.getNsrsbh()) != null)
            riskMicroTask.setTaxPayer(taxPayerService.getTaxPayerByCode(riskMicroTask.getNsrsbh()));

        riskMicroTask.setRiskMicroTaskSurveyReport(riskMicroTaskSurveyReportService.getSurveyReportById(riskMicroTask.getRwbgDm()));
        riskMicroTask.setPjry(userService.getByCode(riskMicroTask.getPjryDm()));
    }

    /**
     * 获取任务提取列表
     *
     * @param riskMicroTaskExtractParameter
     * @return List<RiskMicroTask>
     */
    public List<RiskMicroTask> getExtractRiskMicroTaskList(
            RiskMicroTaskExtractParameter riskMicroTaskExtractParameter) {

        RiskMicroTaskDao riskMicroTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> riskMicroTaskList = riskMicroTaskDao.getExtractRiskMicroTaskList(riskMicroTaskExtractParameter);

        if (riskMicroTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : riskMicroTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return riskMicroTaskList;
    }


    public void add(RiskMicroTask microTask) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        microTaskDao.add(microTask);
    }

    /**
     * 按分析期删除记录
     *
     * @param dataCalcParameter
     */
    public void deleteByFxq(DataCalcParameter dataCalcParameter) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        microTaskDao.deleteByFxq(dataCalcParameter);

    }

    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        microTaskDao.deleteByFxqAndZt(dataCalcParameter);
    }

    public boolean isExist(RiskMicroTask microTask) {
        RiskMicroTaskDao dao = new RiskMicroTaskDao();
        return dao.isExist(microTask);
    }


    /**
     * 根据id获取任务对象
     *
     * @param id
     * @return
     */
    public RiskMicroTask getRiskMicroTaskById(int id) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        RiskMicroTask riskMicroTask = microTaskDao.getRiskMicroTaskById(id);

        if (null != riskMicroTask) {
            reMapping(riskMicroTask);
        }

        return riskMicroTask;
    }

    /**
     * 更新任务对象
     *
     * @param microTask
     */
    public void update(RiskMicroTask microTask) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        microTaskDao.update(microTask);
    }

    /**
     * 根据执行人员代码 和 状态代码 查询微观任务列表
     *
     * @param rwzxryDm
     * @param rwzrDm
     * @return
     */
    public List<RiskMicroTask> getRiskMicroTaskListByZxryAndRwzt(String rwzxryDm,
                                                                 String rwzrDm) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> microTaskList = microTaskDao.getRiskMicroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        if (microTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : microTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return microTaskList;
    }

    /**
     * 根据任务状态获取任务列表
     *
     * @param rwztDm
     * @return
     */
    public List<RiskMicroTask> getRiskMicroTaskListByRwzt(String rwztDm) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> microTaskList = microTaskDao.getRiskMicroTaskListByRwzt(rwztDm);

        if (microTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : microTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return microTaskList;
    }

    /**
     * 根据发布人员 和 任务状态获得任务列表
     *
     * @param rwfbryDm
     * @param rwztDm
     * @return
     */
    public List<RiskMicroTask> getRiskMicroTaskListByFbryAndRwzt(String rwfbryDm,
                                                                 String rwztDm) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> microTaskList = microTaskDao.getRiskMicroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);

        if (microTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : microTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return microTaskList;
    }

    /**
     * 根据反馈时间起止 任务状态 任务执行人员 获得任务列表
     *
     * @param sjQ
     * @param sjZ
     * @param rwztDm
     * @param rwzxryDm
     * @return
     */
    public List<RiskMicroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
                                                                 String sjZ, String rwztDm, String rwzxryDm) {

        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> microTaskList = microTaskDao.getListByRwfkRqAndRwztAndRwzxryDm(sjQ, sjZ, rwztDm, rwzxryDm);

        if (microTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : microTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return microTaskList;
    }

    /**
     * 根据管理机关和任务状态获取获取任务列表
     *
     * @param swjgDm
     * @param rwztDm
     * @return
     */
    public List<RiskMicroTask> getListBySwjgAndRwzt(String swjgDm, String rwztDm) {
        RiskMicroTaskDao microTaskDao = new RiskMicroTaskDao();
        List<RiskMicroTask> microTaskList = microTaskDao.getListBySwjgAndRwzt(swjgDm, rwztDm);

        if (microTaskList.size() != 0) {
            for (RiskMicroTask riskMicroTask : microTaskList) {
                reMapping(riskMicroTask);
            }
        }

        return microTaskList;
    }

}
