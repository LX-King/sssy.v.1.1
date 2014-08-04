package com.tyut.sssy.task.service;

import java.util.ArrayList;
import java.util.List;

import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.sysmgr.service.UserService;
import com.tyut.sssy.task.dao.MacroTaskTestDao;
import com.tyut.sssy.task.dao.MicroTaskTestDao;
import com.tyut.sssy.task.domain.TaskTest;

public class MacroTaskTestService {

	/**
	 * 根据执行日期起止获得执行人员列表
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<User> getRwzxryListByRwfkRq(String sjQ, String sjZ) {
		
		MacroTaskTestDao macroTaskTestDao = new MacroTaskTestDao();
		List<String> rwzxryDmList = macroTaskTestDao.getRwzxryDmListByRwfkRq(sjQ, sjZ);
		
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
	 * 根据被考核人员和起止时间删除原有记录
	 * @param bkhryDm
	 * @param sjQ
	 * @param sjZ
	 */
	public void deleteByBkhryAndSjQAndSjZ(String bkhryDm, String sjQ, String sjZ) {

		MacroTaskTestDao macroTaskTestDao = new MacroTaskTestDao();
		macroTaskTestDao.deleteByBkhryAndSjQAndSjZ(bkhryDm, sjQ, sjZ);
	}

	/**
	 * 获取当日最大的任务考核代码
	 * @param dateStr
	 * @return
	 */
	public String getMaxRwkhDm(String dateStr) {
		MicroTaskTestDao microTaskTestDao = new MicroTaskTestDao();
		String maxRwkhDm = microTaskTestDao.getMaxRwkhDm(dateStr);
		return maxRwkhDm;
	}

	public void add(TaskTest taskTest) {
		MacroTaskTestDao macroTaskTestDao = new MacroTaskTestDao();
		macroTaskTestDao.add(taskTest);
	}

	/**
	 * 根据考核日期获得任务考核列表
	 * @param sjQ
	 * @param sjZ
	 * @return
	 */
	public List<TaskTest> getTaskTestListByKhRq(String sjQ, String sjZ,
			String khrwLx) {
		MacroTaskTestDao macroTaskTestDao = new MacroTaskTestDao();
		List<TaskTest> taskTestList = macroTaskTestDao.getTaskTestListByKhRq(sjQ, sjZ, khrwLx);
		
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
