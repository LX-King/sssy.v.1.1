package com.tyut.sssy.task.service;

import com.tyut.sssy.base.service.AnalysisIndexService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.sysmgr.service.IndexFeatureDBService;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.MacroTaskDao;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MacroTaskExtractParameter;

import java.util.List;

public class MacroTaskService {

    public void deleteByFxq(DataCalcParameter dataCalcParameter) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        macroTaskDao.deleteByFxq(dataCalcParameter);

    }

    public void add(MacroTask macroTask) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        macroTaskDao.add(macroTask);
    }

    /**
     * 在任务提取模块 得到任务列表
     *
     * @param macroTaskExtractParameter
     * @return
     */
    public List<MacroTask> getMacroTaskListInExtractSection(
            MacroTaskExtractParameter macroTaskExtractParameter) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = null;

        if (macroTaskExtractParameter.getZflLx().equals("管理机关")) {
            macroTaskList = macroTaskDao.getMacroTaskListBySwjgInExtractSection(macroTaskExtractParameter);
        } else if (macroTaskExtractParameter.getZflLx().equals("行业")) {
            macroTaskList = macroTaskDao.getMacroTaskListByHyInExtractSection(macroTaskExtractParameter);
        } else if (macroTaskExtractParameter.getZflLx().equals("注册类型")) {
            macroTaskList = macroTaskDao.getMacroTaskListByZclxInExtractSection(macroTaskExtractParameter);
        }

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
    }

    public MacroTask getMacroTaskById(int id) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        MacroTask macroTask = macroTaskDao.getMacroTaskById(id);

        AnalysisIndexService analysisIndexService = new AnalysisIndexService();
        IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
        TaskStateService taskStateService = new TaskStateService();
        UserService userService = new UserService();
        TaxUnitService taxUnitService = new TaxUnitService();
        MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

        macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
        macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
        macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
        macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
        macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
        macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
        macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
        macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
        macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

        return macroTask;
    }

    public void update(MacroTask macroTask) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        macroTaskDao.update(macroTask);

    }

    /**
     * 根据执行人员和任务状态获得任务列表
     *
     * @param rwzxryDm
     * @param rwzrDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByZxryAndRwzt(String rwzxryDm,
                                                         String rwzrDm) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = macroTaskDao.getMacroTaskListByZxryAndRwzt(rwzxryDm, rwzrDm);

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
    }

    /**
     * 根据任务状态获取任务列表
     *
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByRwzt(String rwztDm) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = macroTaskDao.getMacroTaskListByRwzt(rwztDm);

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
    }

    /**
     * 根据任务发布人员 和 任务状态获取任务列表
     *
     * @param rwfbryDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getMacroTaskListByFbryAndRwzt(String rwfbryDm,
                                                         String rwztDm) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = macroTaskDao.getMacroTaskListByFbryAndRwzt(rwfbryDm, rwztDm);

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
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
    public List<MacroTask> getListByRwfkRqAndRwztAndRwzxryDm(String sjQ,
                                                             String sjZ, String rwztDm, String rwzxryDm) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = macroTaskDao.getListByRwfkRqAndRwztAndRwzxryDm(sjQ, sjZ, rwztDm, rwzxryDm);

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
    }

    public boolean isExist(MacroTask macroTask){
        MacroTaskDao dao = new MacroTaskDao();
        return dao.isExist(macroTask);
    }

    /**
     * 根据总/分量类型和任务状态获取任务列表
     *
     * @param swjgDm
     * @param hydlDm
     * @param qyzclxDm
     * @param rwztDm
     * @return
     */
    public List<MacroTask> getListByZflLxAndRwzt(String zflLx, String swjgDm, String hydlDm,
                                                 String qyzclxDm, String rwztDm) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        List<MacroTask> macroTaskList = null;

        if (zflLx.equals("管理机关")) {
            macroTaskList = macroTaskDao.getMacroTaskListBySwjgAndRwzt(zflLx, swjgDm, rwztDm);
        } else if (zflLx.equals("行业")) {
            macroTaskList = macroTaskDao.getMacroTaskListByHyAndRwzt(zflLx, hydlDm, rwztDm);
        } else if (zflLx.equals("注册类型")) {
            macroTaskList = macroTaskDao.getMacroTaskListByZclxAndRwzt(zflLx, qyzclxDm, rwztDm);
        }

        if (macroTaskList.size() != 0) {

            AnalysisIndexService analysisIndexService = new AnalysisIndexService();
            IndexFeatureDBService indexFeatureDBService = new IndexFeatureDBService();
            TaskStateService taskStateService = new TaskStateService();
            UserService userService = new UserService();
            TaxUnitService taxUnitService = new TaxUnitService();
            MacroTaskSurveyReportService macroTaskSurveyReportService = new MacroTaskSurveyReportService();

            for (MacroTask macroTask : macroTaskList) {
                macroTask.setTaxUnit(taxUnitService.getTaxUnitById(macroTask.getSwjgDm()));
                macroTask.setAnalysisIndex(analysisIndexService.getAnalysisIndexById(macroTask.getFxzbDm()));
                macroTask.setIndexFeatureDB(indexFeatureDBService.getByCodeAndType(macroTask.getFxzbDm(), macroTask.getTzqj()));
                macroTask.setTaskState(taskStateService.getTaskStateById(macroTask.getRwztDm()));
                macroTask.setRwtqry(userService.getByCode(macroTask.getRwtqryDm()));
                macroTask.setRwfbry(userService.getByCode(macroTask.getRwfbryDm()));
                macroTask.setRwzxry(userService.getByCode(macroTask.getRwzxryDm()));
                macroTask.setMacroTaskSurveyReport(macroTaskSurveyReportService.getSurveyReportById(macroTask.getRwbgDm()));
                macroTask.setPjry(userService.getByCode(macroTask.getPjryDm()));

            }
        }

        return macroTaskList;
    }

    public void deleteByFxqAndZt(DataCalcParameter dataCalcParameter) {
        MacroTaskDao macroTaskDao = new MacroTaskDao();
        macroTaskDao.deleteByFxqAndZt(dataCalcParameter);
    }
}
