package com.tyut.sssy.task.service;

import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.RiskMicroTaskTestDao;
import com.tyut.sssy.task.domain.TaskTest;

import java.util.ArrayList;
import java.util.List;

public class RiskMicroTaskTestService {

	/**
	 * 根据任务反馈日期获得任务执行人员列表
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<User> getRwzxryListByRwfkRq(String sjQ, String sjZ) {
		RiskMicroTaskTestDao riskMicroTaskTestDao = new RiskMicroTaskTestDao();
		List<String> rwzxryDmList = riskMicroTaskTestDao.getRwzxryDmListByRwfkRq(sjQ, sjZ);
		
		UserService userService = new UserService();
		List<User> userList = new ArrayList<User>();
		User user = null;
		
		for (String rwzxryDm : rwzxryDmList) {
			user = userService.getByCode(rwzxryDm);
			userList.add(user);
		}
		
		return userList;
	}

	/**
	 * 获取当日最大的任务考核代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxRwkhDm(String dateStr) {
        RiskMicroTaskTestDao riskMicroTaskTestDao = new RiskMicroTaskTestDao();

		String maxRwkhDm = riskMicroTaskTestDao.getMaxRwkhDm(dateStr);
		return maxRwkhDm;
	}

	public void add(TaskTest taskTest) {
		RiskMicroTaskTestDao riskMicroTaskTestDao = new RiskMicroTaskTestDao();
        riskMicroTaskTestDao.add(taskTest);
	}

	/**
	 * 根据被考核人员和起止时间删除原有记录
	 * @param bkhryDm
	 * @param sjQ
	 * @param sjZ
	 */
	public void deleteByBkhryAndSjQAndSjZ(String bkhryDm, String sjQ, String sjZ) {
        RiskMicroTaskTestDao riskMicroTaskTestDao = new RiskMicroTaskTestDao();

        riskMicroTaskTestDao.deleteByBkhryAndSjQAndSjZ(bkhryDm, sjQ, sjZ);
	}

	/**
	 * 根据考核日期获得任务考核列表
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<TaskTest> getTaskTestListByKhRq(String sjQ, String sjZ, String khrwLx) {
        RiskMicroTaskTestDao riskMicroTaskTestDao = new RiskMicroTaskTestDao();

		List<TaskTest> taskTestList = riskMicroTaskTestDao.getTaskTestListByKhRq(sjQ, sjZ, khrwLx);
		
		if (taskTestList.size() != 0) {
			
			UserService userService = new UserService();
			
			for (TaskTest taskTest : taskTestList) {
				taskTest.setBkhry(userService.getByCode(taskTest.getBkhryDm()));
				taskTest.setKhry(userService.getByCode(taskTest.getKhryDm()));
			}
		}
		
		return taskTestList;
	}

}
