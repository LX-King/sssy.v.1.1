package com.tyut.sssy.infosearch.actions.graphic.tax;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.infosearch.service.TaxCollectionSearchService;
import com.tyut.sssy.sysmgr.dao.IndustryDao;
import com.tyut.sssy.sysmgr.dao.IndustryGraphicDao;
import com.tyut.sssy.sysmgr.dao.RegisterTypeGraphicDao;
import com.tyut.sssy.sysmgr.dao.TaxCategoryGraphicDao;
import com.tyut.sssy.sysmgr.domain.Industry;
import com.tyut.sssy.sysmgr.domain.IndustryGraphic;
import com.tyut.sssy.sysmgr.domain.RegisterTypeGraphic;
import com.tyut.sssy.sysmgr.domain.TaxCategoryGraphic;
import com.tyut.sssy.taxcollection.domain.TaxCollectionFinishTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTable;
import com.tyut.sssy.taxcollection.domain.TaxCollectionStructureChangeTableParameter;
import com.tyut.sssy.utils.*;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

/**
 * ClassName:TaxFinishGraphicAction
 * Function:税收结构变化
 * Author: LiuXiang
 * Date: 2012-6-24
 * Time: 10:53:53
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class TaxStrucChangeGraphicAction extends ActionSupport implements Preparable {

    private TaxCollectionStructureChangeTableParameter parameter = null;

    private TaxCollectionSearchService service = null;

    private Map<String, List<BigDecimal>> dataset = null;

    private List<String> categories = null;


    /**
     * 预处理
     *
     * @throws Exception
     */
    public void prepare() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        this.parameter = (TaxCollectionStructureChangeTableParameter) session.getAttribute(SessionAttributeKey.SSSRJGBH);
        this.service = new TaxCollectionSearchService();
        this.dataset = new LinkedHashMap<String, List<BigDecimal>>();
        this.categories = new ArrayList<String>();
        for (int i = 1; i <= Integer.parseInt(this.parameter.getYf()); i++) {
            this.categories.add(i + "月");
        }
    }

    /**
     * 默认执行方法
     *
     * @return string
     */
    @Override
    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String method = request.getParameter("method");
        if (method.equals("line")) {
            drawLineGraphic();
            return null;
        } else if (method.equals("column")) {
            drawColumnGraphic();
            return null;
        } else {
            drawPieGraphic();
            return null;
        }
    }

    /*画折线图*/
    private void drawLineGraphic() {
        String monthStr = parameter.getYf();
        int month = Integer.parseInt(monthStr);
        if (this.dataset.size() == 0)
            extractData(month);
        TaxUnitService taxUnitService = new TaxUnitService();

        //税务机关代码
        String swjgDm = parameter.getSwjg();
        //数据类型
        String dataType = parameter.getSjlx();
        //报表期
        String reportPeriod = parameter.getYf().trim().equals("1") ? parameter.getNd() + "年" + " 1月" : parameter.getNd() + "年" + " 1 - " + (Integer.parseInt(parameter.getYf())) + " 月";
        //税务机关单位名称
        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        // 数据类型名称
        String dataTypeMc = null;
        if (dataType.equals("R"))
            dataTypeMc = "入库税收";
        else
            dataTypeMc = "实缴税收";
        //图表的标题
        String caption = unitName + reportPeriod + dataTypeMc + "税收收入结构变化情况图表";

        //生成FushionChartDataSource
        String xAxisName = "本期";
        String yAxisName = "完成税收";
        FushionChartLineJsonData dataSource = new FushionChartLineJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());
    }


    /*画饼图*/
    private void drawPieGraphic() {
        TaxUnitService taxUnitService = new TaxUnitService();
        String monthStr = parameter.getYf();
        int month = Integer.parseInt(monthStr);
        if (this.dataset.size() == 0)
            extractData(month);
        //税务机关代码
        String swjgDm = parameter.getSwjg();
        //数据类型
        String dataType = parameter.getSjlx();
        //报表期
        String reportPeriod = parameter.getYf().trim().equals("1") ? parameter.getNd() + "年" + "1月" : parameter.getNd() + "年" + "1 - " + (Integer.parseInt(parameter.getYf())) + "月";
        //税务机关单位名称
        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        // 数据类型名称
        String dataTypeMc = null;
        if (dataType.equals("R"))
            dataTypeMc = "入库税收";
        else
            dataTypeMc = "实缴税收";
        //图表的标题
        String caption = unitName + reportPeriod + dataTypeMc + "税收收入结构变化情况图表";

        //生成FushionChartDataSource
        String xAxisName = "本期";
        String yAxisName = "完成税收";
        Map<String, BigDecimal> dataSet = new LinkedHashMap<String, BigDecimal>();
        BigDecimal other = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        for (Map.Entry<String, List<BigDecimal>> entry : this.dataset.entrySet()) {
            if (entry.getKey().trim().equals("合计")) {
                for (BigDecimal b : entry.getValue()) {
                    total = total.add(b);
                }
            } else {
                BigDecimal tempOther = new BigDecimal(0);
                for (BigDecimal b : entry.getValue()) {
                    tempOther = tempOther.add(b);
                }
                if (other.equals(new BigDecimal(0))) {
                    other = total.subtract(tempOther);
                } else {
                    other = other.subtract(tempOther);
                }
                dataSet.put(entry.getKey(), tempOther.divide(total, 5, BigDecimal.ROUND_HALF_EVEN));
            }
        }
        String displayItem = this.parameter.getFl();
        if (displayItem.equals("02") || displayItem.equals("05") || displayItem.equals("03"))
            dataSet.put("其它", other.divide(total, 5, BigDecimal.ROUND_HALF_EVEN));
        FushionChartPieJsonData dataSource = new FushionChartPieJsonData(caption, xAxisName, yAxisName, dataSet);
        //        FushionChartDataSource dataSource = generateLineDataSource(caption, xAxisName, yAxisName, displayItemService.getById(displayItem).getFlmc());
        printJson(dataSource.toJson());

    }

    /*画柱图*/
    private void drawColumnGraphic() {
        String monthStr = parameter.getYf();
        int month = Integer.parseInt(monthStr);
        if (this.dataset.size() == 0)
            extractData(month);
        TaxUnitService taxUnitService = new TaxUnitService();

        //税务机关代码
        String swjgDm = parameter.getSwjg();
        //数据类型
        String dataType = parameter.getSjlx();
        //报表期
        String reportPeriod = parameter.getYf().trim().equals("1") ? parameter.getNd() + "年" + "1月" : parameter.getNd() + "年" + "1 - " + (Integer.parseInt(parameter.getYf())) + "月";
        //税务机关单位名称
        String unitName = taxUnitService.getTaxUnitById(swjgDm).getMc();
        // 数据类型名称
        String dataTypeMc = null;
        if (dataType.equals("R"))
            dataTypeMc = "入库税收";
        else
            dataTypeMc = "实缴税收";
        //图表的标题
        String caption = unitName + reportPeriod + dataTypeMc + "税收收入结构变化情况图表";

        //生成FushionChartDataSource
        String xAxisName = "本期";
        String yAxisName = "完成税收";
        FushionChartColumnJsonData dataSource = new FushionChartColumnJsonData(caption, xAxisName, yAxisName, this.categories, this.dataset);
        printJson(dataSource.toJson());
    }

    private void extractData(int month){

		String displayItem = this.parameter.getFl();
		List<BigDecimal> totalList = new ArrayList<BigDecimal>();
		this.dataset.put("合计", totalList);
		
		if (displayItem.equals("02")) {/*行业*/
			IndustryGraphicDao industryGraphicDao = new IndustryGraphicDao();
			List<IndustryGraphic> list = industryGraphicDao.queryAll();
			

			for (int k = 0; k < list.size(); k++) {
				IndustryGraphic currIndustry = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currIndustry.getIndustryName().trim(),
						tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					IndustryGraphic currIndustry = list.get(j);
					List<BigDecimal> tempList = this.dataset.get(currIndustry
							.getIndustryName().trim());

					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currIndustry.getIndustryName().trim().equals(
								table.getXm().trim())) {
							if (table.getA1() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}
					this.dataset.put(currIndustry.getIndustryName().trim(),
							tempList);
				}

			}
			

		} else if (displayItem.equals("05")) { /*税种*/
			TaxCategoryGraphicDao taxCategoryGaphicDao = new TaxCategoryGraphicDao();
			List<TaxCategoryGraphic> list = taxCategoryGaphicDao.queryAll();
			
			for (int k = 0; k < list.size(); k++) {
				TaxCategoryGraphic currTaxCategory = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currTaxCategory.getMc().trim(), tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					TaxCategoryGraphic currTaxCategory = list.get(j);
					List<BigDecimal> tempList = this.dataset
							.get(currTaxCategory.getMc().trim());
					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currTaxCategory.getMc().trim().equals(
								table.getXm().trim())) {
							if (table.getA1() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}

					this.dataset.put(currTaxCategory.getMc().trim(), tempList);
				}
			}
			
		} else if (displayItem.equals("01")) {/*产业*/
			IndustryDao industryDao = new IndustryDao();
			List<Industry> list = industryDao.getAllIndustry();
			

			for (int k = 0; k < list.size(); k++) {
				Industry currIndustry = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currIndustry.getIndustryName().trim(),
						tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					Industry currIndustry = list.get(j);
					List<BigDecimal> tempList = this.dataset.get(currIndustry
							.getIndustryName().trim());
					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currIndustry.getIndustryName().trim().equals(
								table.getXm().trim())) {
							if (table.getA1() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}
					this.dataset.put(currIndustry.getIndustryName().trim(),
							tempList);
				}
				
			}
		} else if (displayItem.equals("03")) {//注册类型
			RegisterTypeGraphicDao registerTypeGraphicDao = new RegisterTypeGraphicDao();
			List<RegisterTypeGraphic> list = registerTypeGraphicDao.queryAll();
			

			for (int k = 0; k < list.size(); k++) {
				RegisterTypeGraphic currRegisterType = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currRegisterType.getMc().trim(), tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					RegisterTypeGraphic currRegisterType = list.get(j);
					List<BigDecimal> tempList = this.dataset
							.get(currRegisterType.getMc().trim());

					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currRegisterType.getMc().trim().equals(
								table.getXm().trim())) {
							if (table.getA1() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}
					this.dataset.put(currRegisterType.getMc().trim(), tempList);
				}
				
			}
		} else if (displayItem.equals("04")) {//预算级次
			List<String> list = Arrays.asList(Constants.PRE_CALL_LEVEL);
			

			for (int k = 0; k < list.size(); k++) {
				String currPreCallLevel = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currPreCallLevel.trim(), tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					String currPreCallLevel = list.get(j);
					List<BigDecimal> tempList = this.dataset
							.get(currPreCallLevel.trim());
					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currPreCallLevel.trim()
								.equals(table.getXm().trim())) {
							if (table.getA2() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}
					this.dataset.put(currPreCallLevel.trim(), tempList);
				}
				
			}
		} else if (displayItem.equals("06")) {//其他
			List<String> list = Arrays.asList(Constants.OTHER);
			

			for (int k = 0; k < list.size(); k++) {
				String currPreCallLevel = list.get(k);
				List<BigDecimal> tempList = new ArrayList<BigDecimal>();
				this.dataset.put(currPreCallLevel.trim(), tempList);
			}

			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
				totalList.add(finishTable.get(0).getA1());

				for (int j = 0; j < list.size(); j++) {
					String currPreCallLevel = list.get(j);
					List<BigDecimal> tempList = new ArrayList<BigDecimal>();
					for (TaxCollectionStructureChangeTable table : finishTable) {
						if (currPreCallLevel.trim()
								.equals(table.getXm().trim())) {
							if (table.getA1() != null)
								tempList.add(table.getA1());
							else
								tempList.add(new BigDecimal(0));
							break;
						}
					}
					this.dataset.put(currPreCallLevel.trim(), tempList);
				}
							}
		} else {
			List<BigDecimal> tempList = new ArrayList<BigDecimal>();
			for (int i = 1; i <= month; i++) {
				String temp = String.valueOf(i);
				if (!temp.equals("10") && !temp.equals("11")
						&& !temp.equals("12")) {
					temp = "0" + temp;
				}
				parameter.setYf(temp);
				tempList.add(service.getStructureChangeTable(parameter).get(0).getA1());
			}
		}
	}

    
    
   /* *//**
     * 根据显示项目 封装数据
     *
     * @param month
     *//*
    private void extractData(int month) {
        String displayItem = this.parameter.getFl();
        if (displayItem.equals("02")) {行业
            IndustryGraphicDao industryGraphicDao = new IndustryGraphicDao();
            List<IndustryGraphic> list = industryGraphicDao.queryAll();
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);

            for (int j = 0; j < list.size(); j++) {
                IndustryGraphic currIndustry = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);

                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currIndustry.getIndustryName().trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                    while (tempList.size() < i) {
                        tempList.add(new BigDecimal(0));
                    }
                }
                this.dataset.put(currIndustry.getIndustryName().trim(), tempList);
            }

        } else if (displayItem.equals("05")) { 税种
            TaxCategoryGraphicDao taxCategoryGaphicDao = new TaxCategoryGraphicDao();
            List<TaxCategoryGraphic> list = taxCategoryGaphicDao.queryAll();
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);
            for (int j = 0; j < list.size(); j++) {
                TaxCategoryGraphic currTaxCategory = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currTaxCategory.getMc().trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                }
                this.dataset.put(currTaxCategory.getMc().trim(), tempList);
            }

        } else if (displayItem.equals("01")) {产业
            IndustryDao industryDao = new IndustryDao();
            List<Industry> list = industryDao.getAllIndustry();
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);
            for (int j = 0; j < list.size(); j++) {
                Industry currIndustry = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currIndustry.getIndustryName().trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                }
                this.dataset.put(currIndustry.getIndustryName().trim(), tempList);
            }
        } else if (displayItem.equals("03")) {//注册类型
            RegisterTypeGraphicDao registerTypeGraphicDao = new RegisterTypeGraphicDao();
            List<RegisterTypeGraphic> list = registerTypeGraphicDao.queryAll();
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);
            for (int j = 0; j < list.size(); j++) {
                RegisterTypeGraphic currRegisterType = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currRegisterType.getMc().trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                }
                this.dataset.put(currRegisterType.getMc().trim(), tempList);
            }
        } else if (displayItem.equals("04")) {//预算级次
            List<String> list = Arrays.asList(Constants.PRE_CALL_LEVEL);
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);
            for (int j = 0; j < list.size(); j++) {
                String currPreCallLevel = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currPreCallLevel.trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                }
                this.dataset.put(currPreCallLevel.trim(), tempList);
            }
        } else if (displayItem.equals("06")) {//其他
            List<String> list = Arrays.asList(Constants.OTHER);
            List<BigDecimal> totalList = new ArrayList<BigDecimal>();

            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                totalList.add(finishTable.get(0).getA1());
            }
            this.dataset.put("合计", totalList);
            for (int j = 0; j < list.size(); j++) {
                String currPreCallLevel = list.get(j);
                List<BigDecimal> tempList = new ArrayList<BigDecimal>();

                for (int i = 1; i <= month; i++) {
                    String temp = String.valueOf(i);
                    if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                        temp = "0" + temp;
                    }
                    parameter.setYf(temp);
                    List<TaxCollectionStructureChangeTable> finishTable = service.getStructureChangeTable(parameter);
                    for (TaxCollectionStructureChangeTable table : finishTable) {
                        if (currPreCallLevel.trim().equals(table.getXm().trim())) {
                            tempList.add(table.getA1());
                            break;
                        }
                    }
                }
                this.dataset.put(currPreCallLevel.trim(), tempList);
            }
        } else {
            List<BigDecimal> tempList = new ArrayList<BigDecimal>();
            for (int i = 1; i <= month; i++) {
                String temp = String.valueOf(i);
                if (!temp.equals("10") && !temp.equals("11") && !temp.equals("12")) {
                    temp = "0" + temp;
                }
                parameter.setYf(temp);
                tempList.add(service.getStructureChangeTable(parameter).get(0).getA1());
            }
        }
    }
*/
    /**
     * 生成Line数据源
     *
     * @param caption
     * @return FushionChartDataSource
     */
    private FushionChartDataSource generateLineDataSource(String caption, String xAxisName, String yAxisName, String displayItem) {
        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName(xAxisName);
        chart.setYAxisName(yAxisName);
        chart.setNumberPerfix("￥");

        FushionChartCategories categories = new FushionChartCategories();

        List<FushionChartCategories> categoriesList = new ArrayList<FushionChartCategories>();
        categoriesList.add(categories);
        List<FushionChartDataSet> dataSet = new ArrayList<FushionChartDataSet>();

        for (Map.Entry<String, List<BigDecimal>> entrySet : this.dataset.entrySet()) {
            FushionChartDataSet dataSet1 = new FushionChartDataSet(entrySet.getKey());
            for (BigDecimal d : entrySet.getValue()) {
                FushionChartData data1 = new FushionChartData();
                data1.setValue(d.toString());
                dataSet1.add(data1);
            }
            dataSet.add(dataSet1);
        }

        FushionChartDataSource dataSource = new FushionChartDataSource(chart, categoriesList, dataSet);
        return dataSource;
    }

    /**
     * 生成饼图 数据源
     *
     * @param caption
     * @param xAxisName
     * @param yAxisName
     * @param displayItem
     * @return FushionChartDataSource
     */
    private FushionChartDataSource generatePieDataSource(String caption, String xAxisName, String yAxisName, String displayItem) {
        FushionChartDesc chart = new FushionChartDesc();
        chart.setCaption(caption);
        chart.setXAxisName(xAxisName);
        chart.setYAxisName(yAxisName);
        chart.setNumberPerfix("￥");

        FushionChartCategories categories = new FushionChartCategories();
        for (int i = 1; i <= dataset.size(); i++) {
            FushionChartCategory f = new FushionChartCategory();
            f.setLabel(i + "月");
            categories.add(f);
        }
        List<FushionChartCategories> categoriesList = new ArrayList<FushionChartCategories>();
        categoriesList.add(categories);
        List<FushionChartDataSet> dataSet = new ArrayList<FushionChartDataSet>();
        FushionChartDataSet dataSet1 = new FushionChartDataSet(displayItem);
        for (int i = 1; i <= this.dataset.size(); i++) {
            FushionChartData data1 = new FushionChartData();
            data1.setValue(this.dataset.get(i - 1).toString());
            dataSet1.add(data1);
        }
        dataSet.add(dataSet1);
        FushionChartDataSource dataSource = new FushionChartDataSource(chart, categoriesList, dataSet);
        return dataSource;
    }


    /**
     * 输出结果 封装
     *
     * @param json
     * @return void
     */
    private void printJson(String json) {
        if (json == null || json.equals(""))
            json = "{\"chart\":{\"caption\":\"Business Results 2005 v 2006\",\"xaxisname\":\"Month\",\"yaxisname\":\"Revenue\",\"showvalues\":\"0\",\"numberprefix\":\"$\"},\"categories\":[{\"category\":[{\"label\":\"Jan\"},{\"label\":\"Feb\"},{\"label\":\"Mar\"},{\"label\":\"Apr\"},{\"label\":\"May\"},{\"label\":\"Jun\"},{\"vline\":\"true\",\"color\":\"FF5904\",\"thickness\":\"2\"},{\"label\":\"Jul\"},{\"label\":\"Aug\"},{\"label\":\"Sep\"},{\"label\":\"Oct\"},{\"label\":\"Nov\"},{\"label\":\"Dec\"}]}],\"dataset\":[{\"seriesname\":\"2006\",\"dataset\":[{\"value\":\"27400\"},{\"value\":\"29800\"},{\"value\":\"25800\"},{\"value\":\"26800\"},{\"value\":\"29600\"},{\"value\":\"32600\"},{\"value\":\"31800\"},{\"value\":\"36700\"},{\"value\":\"29700\"},{\"value\":\"31900\"},{\"value\":\"34800\"},{\"value\":\"24800\"}]},{\"seriesname\":\"2005\",\"dataset\":[{\"value\":\"10000\"},{\"value\":\"11500\"},{\"value\":\"12500\"},{\"value\":\"15000\"},{\"value\":\"11000\"},{\"value\":\"9800\"},{\"value\":\"11800\"},{\"value\":\"19700\"},{\"value\":\"21700\"},{\"value\":\"21900\"},{\"value\":\"22900\"},{\"value\":\"20800\"}]}],\"trendlines\":{\"line\":[{\"startvalue\":\"26000\",\"color\":\"91C728\",\"displayvalue\":\"Target\",\"showontop\":\"1\"}]},\"styles\": {\"definition\": [{\"name\": \"CanvasAnim\",\"type\": \"animation\",\"param\": \"_xScale\",\"start\": \"0\",\"duration\": \"1\"}],\"application\": [{\"toobject\": \"Canvas\",\"styles\": \"CanvasAnim\"}]}}";
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = null;

        try {
            out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert out != null;
        out.flush();
        out.close();
    }
}