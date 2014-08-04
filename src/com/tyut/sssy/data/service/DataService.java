package com.tyut.sssy.data.service;

import com.tyut.sssy.base.domain.BigIndustry;
import com.tyut.sssy.base.domain.FirmRegType;
import com.tyut.sssy.base.domain.TaxUnit;
import com.tyut.sssy.base.service.BigIndustryService;
import com.tyut.sssy.base.service.FirmRegTypeService;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.data.dao.DataDao;
import com.tyut.sssy.data.domain.DataCalcParameter;
import com.tyut.sssy.data.domain.DataImportParameter;
import com.tyut.sssy.data.domain.DataImportQt;
import com.tyut.sssy.data.domain.DataImportZdsy;
import com.tyut.sssy.task.domain.AnalysisMacroIndex;
import com.tyut.sssy.task.domain.AnalysisMicroIndex;
import com.tyut.sssy.task.domain.MacroTask;
import com.tyut.sssy.task.domain.MicroTask;
import com.tyut.sssy.task.service.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目名称：sssy20120419
 * 类名称：SoftwareImportService
 * 类描述：综合征管软件数据操作
 * 创建人：梁斌
 * 创建时间：2012-4-19 下午08:09:21
 * 修改人：梁斌
 * 修改时间：2013-7-8
 * 修改备注： 修改了其中的微观任务导入
 *
 */
public class DataService {

    // 数据导入
    public void dataImport(DataImportParameter dataImportParameter) {
        DataDao dataDao = new DataDao();
        dataDao.dataImport(dataImportParameter);
    }

    // 数据分析
    public void dataAnalyze(DataCalcParameter dataCalcParameter) {
        DataDao dataDao = new DataDao();

        // ding分析
        dataDao.dataCalc(dataCalcParameter);
    }

    /**
     * 指标计算2
     */
    public void dataAnalyze2(DataCalcParameter dataCalcParameter) {
        DataDao dataDao = new DataDao();

        dataDao.dataCalc2(dataCalcParameter);
    }

    /**
     * 重点税源数据导入
     *
     * @param dataImportZdsy
     */
    public void zdsyDataImport(DataImportZdsy dataImportZdsy) {
        DataDao dataDao = new DataDao();
        dataDao.zdsyDataImport(dataImportZdsy);
    }

    /**
     * 综合征管软件数据导入
     *
     * @param dataImportQt
     */
    public void zrDataImport(DataImportQt dataImportQt) {
        DataDao dataDao = new DataDao();
        dataDao.zrDataImport(dataImportQt);
    }

    /**
     * 代码刷新
     */
    public void refreshCode() {
        DataDao dataDao = new DataDao();
        dataDao.refreshCode();
    }


    /**
     * 导入风险指数微观任务
     * @param dataCalcParameter
     * @param isOverrided
     */
    public boolean importRiskMicroTask(DataCalcParameter dataCalcParameter , String isOverrided){

        //两种方式 1：存储过程 2：java 导入
        //1:for process
        DataDao dataDao = new DataDao();
        boolean flag = dataDao.riskTaskImport(dataCalcParameter,isOverrided);
        return flag;
    }

    /**
     * 向微观任务表中插入选定的记录
     *
     * @param dataCalcParameter
     */
    public void insertDataIntoMicroTaskTable(DataCalcParameter dataCalcParameter, String isOverrided) {

        DataDao dataDao = new DataDao();

        MicroTaskService microTaskService = new MicroTaskService();

        // 1-- 筛选出分值>=固定值的记录
        // 1.1-- 从fx_wgzb中选出不同的纳税人识别号
        List<String> taxPayerCodeList = dataDao.getTaxPayerCodeListFromFxWgzb(dataCalcParameter);

        // 1.2-- 根据分析期和纳税人识别号查找记录
        AnalysisMicroIndexService analysisMicroIndexService = new AnalysisMicroIndexService();

        TaskThresholdService taskThresholdService = new TaskThresholdService();
        int wgrwYz = taskThresholdService.getRwyzByRwlx("wgrw");

        for (String taxPayerCode : taxPayerCodeList) {
            BigDecimal zfz = new BigDecimal(0);
            List<AnalysisMicroIndex> analysisMicroIndexList = analysisMicroIndexService.getListByTaxPayerCodeAndFxq(taxPayerCode, dataCalcParameter);

            // 1.3 计算总分值
            for (AnalysisMicroIndex analysisMicroIndex : analysisMicroIndexList) {
                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMicroIndex.getTzqj().equals("ycqj1")) {
                    if (analysisMicroIndex.getBdl() != null)
//						zfz += 3.0/120 *  analysisMicroIndex.getBdl().floatValue() * analysisMicroIndex.getZbqz();
                        BigDecimal.valueOf(3.0 / 120 * analysisMicroIndex.getBdl().floatValue() * analysisMicroIndex.getZbqz());
                    zfz = zfz.add(BigDecimal.valueOf(3.0 / 120 * analysisMicroIndex.getBdl().floatValue() * analysisMicroIndex.getZbqz()));
                } else {
//					zfz += analysisMicroIndex.getTzfz() * analysisMicroIndex.getZbqz();
                    zfz = zfz.add(BigDecimal.valueOf(analysisMicroIndex.getTzfz() * analysisMicroIndex.getZbqz()));
                }
            }
            // 2-- 如果>=阈值，插入记录
            int comp = zfz.compareTo(BigDecimal.valueOf(wgrwYz));
            if (comp == 0 || comp == 1) {
                // 大于等于阈值
                MicroTask microTask = null;
                for (AnalysisMicroIndex analysisMicroIndex : analysisMicroIndexList) {
                    microTask = new MicroTask();

                    microTask.setNsrsbh(analysisMicroIndex.getNsrsbh());
                    microTask.setSwjgDm(analysisMicroIndex.getZgSwjgDm());
                    microTask.setFxqNd(analysisMicroIndex.getFxqNd());
                    microTask.setFxqSjq(analysisMicroIndex.getFxqSjq());
                    microTask.setFxqSjz(analysisMicroIndex.getFxqSjz());
                    microTask.setCyDm(analysisMicroIndex.getCyDm());
                    microTask.setHydlDm(analysisMicroIndex.getHydlDm());
                    microTask.setQyzclxDm(analysisMicroIndex.getQyzclxDm());
                    microTask.setFxzbDm(analysisMicroIndex.getFxzbDm());
                    microTask.setTzqj(analysisMicroIndex.getTzqj());
                    microTask.setTzfz(analysisMicroIndex.getTzfz());
                    microTask.setBdl(analysisMicroIndex.getBdl());
                    microTask.setZfz(zfz);
                    microTask.setRwztDm("00");
                    microTask.setRwtqryDm("");
                    microTask.setRwtqRq("");
                    microTask.setRwfbryDm("");
                    microTask.setRwfbRq("");
                    microTask.setRwzxryDm("");
                    microTask.setBzjsRq("");
                    microTask.setSjjsRq("");
                    microTask.setBzfkRq("");
                    microTask.setSjfkRq("");
                    microTask.setRwbgDm("");
                    microTask.setGlpj("");
                    microTask.setZfpj("");
                    microTask.setZhpj("");
                    microTask.setPjryDm("");
                    microTask.setRwpjRq("");
                    microTask.setRwjsDf(0);
                    microTask.setRwfkDf(0);
                    microTask.setRwzxDf(0);
                    if (isOverrided.trim().equals("n")) {
                        if (!microTaskService.isExist(microTask))
                            microTaskService.add(microTask);
                    } else {
                        microTaskService.add(microTask);
                    }
                }

            }
        }

    }

    /**
     * 按分析期删除任务记录
     *
     * @param dataCalcParameter
     */
    public void deleteTaskDataByDate(DataCalcParameter dataCalcParameter) {
        DataDao dataDao = new DataDao();
        dataDao.deleteTaskDataByDate(dataCalcParameter);
    }

    /**
     * 插入总/分量任务
     *
     * @param dataCalcParameter
     */
    public void insertDataIntoMacroTaskTable(DataCalcParameter dataCalcParameter, String isOverrided) {

        DataDao dataDao = new DataDao();

        MacroTaskService macroTaskService = new MacroTaskService();

        // 1-- 筛选出分值>=固定值的记录
        // 1.1-- 从fx_hgzb中选出不同的 管理机关代码
        String fxzblxMx = "";
        fxzblxMx = "管理机关";
        List<String> swjgDmList = dataDao.getSwjgDmListFromFxHgzb(dataCalcParameter, fxzblxMx);

        // 1.2-- 从fx_hgzb中选出不同的 行业大类代码
        fxzblxMx = "行业";
        List<String> hydlDmList = dataDao.getHydlDmListFromFxHgzb(dataCalcParameter, fxzblxMx);

        // 1.3-- 从fx_hgzb中选出不同的 注册类型代码
        fxzblxMx = "注册类型";
        List<String> qyzclxDmList = dataDao.getQyzclxDmListFromFxHgzb(dataCalcParameter, fxzblxMx);

        AnalysisMacroIndexService analysisMacroIndexService = new AnalysisMacroIndexService();
        TaskThresholdService taskThresholdService = new TaskThresholdService();
        int hgrwYz = taskThresholdService.getRwyzByRwlx("hgrw");

        // 2. 查找记录并计算总分值
        // 2.1 分 管理机关 总/分量任务
        for (String swjgDm : swjgDmList) {
//			int zfz = 0;
            BigDecimal zfz = new BigDecimal(0);
            fxzblxMx = "管理机关";
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndSwjgDm(dataCalcParameter, fxzblxMx, swjgDm);

            // 2.1.1 计算总分值
            for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndex.getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndex.getBdl() != null)
//						zfz += 3.0/120 *  analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz();
                        zfz = zfz.add(BigDecimal.valueOf(3.0 / 120 * analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz()));
                } else {
//					zfz += analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz();
                    zfz = zfz.add(BigDecimal.valueOf(analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz()));
                }
            }
            // 2.1.2-- 如果>=阈值，插入记录
            int comp = zfz.compareTo(BigDecimal.valueOf(hgrwYz));
            if (comp == 0 || comp == 1) {

                MacroTask macroTask = null;
                TaxUnitService taxUnitService = new TaxUnitService();
                TaxUnit taxUnit = null;
                for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                    macroTask = new MacroTask();

                    macroTask.setNsrsbh(analysisMacroIndex.getNsrsbh());
                    macroTask.setSwjgDm(analysisMacroIndex.getZgSwjgDm());
                    macroTask.setFxqNd(analysisMacroIndex.getFxqNd());
                    macroTask.setFxqSjq(analysisMacroIndex.getFxqSjq());
                    macroTask.setFxqSjz(analysisMacroIndex.getFxqSjz());
                    macroTask.setFxzbDm(analysisMacroIndex.getFxzbDm());
                    macroTask.setFxzblxMx(analysisMacroIndex.getFxzblxMx());
                    macroTask.setFxzblxMxDm(analysisMacroIndex.getFxzblxMxDm());

                    taxUnit = taxUnitService.getTaxUnitById(analysisMacroIndex.getZgSwjgDm());
                    macroTask.setFlMc(taxUnit.getMc());

                    macroTask.setTzqj(analysisMacroIndex.getTzqj());
                    macroTask.setBdl(analysisMacroIndex.getBdl());
                    macroTask.setTzfz(analysisMacroIndex.getTzfz());
                    macroTask.setZfz(zfz);
                    macroTask.setRwztDm("00");
                    macroTask.setRwtqryDm("");
                    macroTask.setRwtqRq("");
                    macroTask.setRwfbryDm("");
                    macroTask.setRwfbRq("");
                    macroTask.setRwzxryDm("");
                    macroTask.setBzjsRq("");
                    macroTask.setSjjsRq("");
                    macroTask.setBzfkRq("");
                    macroTask.setSjfkRq("");
                    macroTask.setRwbgDm("");
                    macroTask.setGlpj("");
                    macroTask.setZfpj("");
                    macroTask.setZhpj("");
                    macroTask.setPjryDm("");
                    macroTask.setRwpjRq("");
                    macroTask.setRwjsDf(0);
                    macroTask.setRwfkDf(0);
                    macroTask.setRwzxDf(0);

                    if (isOverrided.trim().equals("n")) {
                        if (!macroTaskService.isExist(macroTask))
                            macroTaskService.add(macroTask);
                    } else {
                        macroTaskService.add(macroTask);

                    }
                }
            }
        }

        // 2.2 分 行业 总/分量任务
        for (String hydlDm : hydlDmList) {
//			int zfz = 0;
            BigDecimal zfz = new BigDecimal(0);
            fxzblxMx = "行业";
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndHydlDm(dataCalcParameter, fxzblxMx, hydlDm);

            // 2.2.1 计算总分值
            for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndex.getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndex.getBdl() != null)
//						zfz += 3.0/120 *  analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz();
                        zfz = zfz.add(BigDecimal.valueOf(3.0 / 120 * analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz()));
                } else {
//					zfz += analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz();
                    zfz = zfz.add(BigDecimal.valueOf(analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz()));
                }
            }
            // 2.2.2-- 如果>=阈值，插入记录
            int comp = zfz.compareTo(BigDecimal.valueOf(hgrwYz));
            if (comp == 0 || comp == 1) {

                BigIndustryService bigIndustryService = new BigIndustryService();
                BigIndustry bigIndustry = null;

                MacroTask macroTask = null;
                for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                    macroTask = new MacroTask();

                    macroTask.setNsrsbh(analysisMacroIndex.getNsrsbh());
                    macroTask.setSwjgDm(analysisMacroIndex.getZgSwjgDm());
                    macroTask.setFxqNd(analysisMacroIndex.getFxqNd());
                    macroTask.setFxqSjq(analysisMacroIndex.getFxqSjq());
                    macroTask.setFxqSjz(analysisMacroIndex.getFxqSjz());
                    macroTask.setFxzbDm(analysisMacroIndex.getFxzbDm());
                    macroTask.setFxzblxMx(analysisMacroIndex.getFxzblxMx());
                    macroTask.setFxzblxMxDm(analysisMacroIndex.getFxzblxMxDm());

                    bigIndustry = bigIndustryService.getBigIndustryById(analysisMacroIndex.getFxzblxMxDm());
                    macroTask.setFlMc(bigIndustry.getMc());

                    macroTask.setTzqj(analysisMacroIndex.getTzqj());
                    macroTask.setBdl(analysisMacroIndex.getBdl());
                    macroTask.setTzfz(analysisMacroIndex.getTzfz());
                    macroTask.setZfz(zfz);
                    macroTask.setRwztDm("00");
                    macroTask.setRwtqryDm("");
                    macroTask.setRwtqRq("");
                    macroTask.setRwfbryDm("");
                    macroTask.setRwfbRq("");
                    macroTask.setRwzxryDm("");
                    macroTask.setBzjsRq("");
                    macroTask.setSjjsRq("");
                    macroTask.setBzfkRq("");
                    macroTask.setSjfkRq("");
                    macroTask.setRwbgDm("");
                    macroTask.setGlpj("");
                    macroTask.setZfpj("");
                    macroTask.setZhpj("");
                    macroTask.setPjryDm("");
                    macroTask.setRwpjRq("");
                    macroTask.setRwjsDf(0);
                    macroTask.setRwfkDf(0);
                    macroTask.setRwzxDf(0);

                    macroTaskService.add(macroTask);
                }
            }
        }

        // 2.3 分 注册类型 总/分量任务
        for (String qyzclxDm : qyzclxDmList) {
//			int zfz = 0;
            BigDecimal zfz = new BigDecimal(0);
            fxzblxMx = "注册类型";
            List<AnalysisMacroIndex> analysisMacroIndexList = analysisMacroIndexService.getListByFxqAndFxzblxMxAndQyzclxDm(dataCalcParameter, fxzblxMx, qyzclxDm);

            // 2.3.1 计算总分值
            for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                // 当处于异常区间1时，3/120 * 变动率 * 权重
                if (analysisMacroIndex.getTzqj().equals("ycqj1")) {
                    if (analysisMacroIndex.getBdl() != null)
//						zfz += 3.0/120 *  analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz();
                        zfz = zfz.add(BigDecimal.valueOf(3.0 / 120 * analysisMacroIndex.getBdl().floatValue() * analysisMacroIndex.getZbqz()));
                } else {
//					zfz += analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz();
                    zfz = zfz.add(BigDecimal.valueOf(analysisMacroIndex.getTzfz() * analysisMacroIndex.getZbqz()));
                }
            }
            // 2.3.2-- 如果>=阈值，插入记录
            int comp = zfz.compareTo(BigDecimal.valueOf(hgrwYz));
            if (comp == 0 || comp == 1) {

                MacroTask macroTask = null;
                FirmRegTypeService firmRegTypeService = new FirmRegTypeService();
                FirmRegType firmRegType = null;

                for (AnalysisMacroIndex analysisMacroIndex : analysisMacroIndexList) {
                    macroTask = new MacroTask();

                    macroTask.setNsrsbh(analysisMacroIndex.getNsrsbh());
                    macroTask.setSwjgDm(analysisMacroIndex.getZgSwjgDm());
                    macroTask.setFxqNd(analysisMacroIndex.getFxqNd());
                    macroTask.setFxqSjq(analysisMacroIndex.getFxqSjq());
                    macroTask.setFxqSjz(analysisMacroIndex.getFxqSjz());
                    macroTask.setFxzbDm(analysisMacroIndex.getFxzbDm());
                    macroTask.setFxzblxMx(analysisMacroIndex.getFxzblxMx());
                    macroTask.setFxzblxMxDm(analysisMacroIndex.getFxzblxMxDm());

                    firmRegType = firmRegTypeService.getFirmRegTypeById(analysisMacroIndex.getFxzblxMxDm());
                    macroTask.setFlMc(firmRegType.getMc());

                    macroTask.setTzqj(analysisMacroIndex.getTzqj());
                    macroTask.setBdl(analysisMacroIndex.getBdl());
                    macroTask.setTzfz(analysisMacroIndex.getTzfz());
                    macroTask.setZfz(zfz);
                    macroTask.setRwztDm("00");
                    macroTask.setRwtqryDm("");
                    macroTask.setRwtqRq("");
                    macroTask.setRwfbryDm("");
                    macroTask.setRwfbRq("");
                    macroTask.setRwzxryDm("");
                    macroTask.setBzjsRq("");
                    macroTask.setSjjsRq("");
                    macroTask.setBzfkRq("");
                    macroTask.setSjfkRq("");
                    macroTask.setRwbgDm("");
                    macroTask.setGlpj("");
                    macroTask.setZfpj("");
                    macroTask.setZhpj("");
                    macroTask.setPjryDm("");
                    macroTask.setRwpjRq("");
                    macroTask.setRwjsDf(0);
                    macroTask.setRwfkDf(0);
                    macroTask.setRwzxDf(0);

                    macroTaskService.add(macroTask);
                }
            }
        }
    }

}
