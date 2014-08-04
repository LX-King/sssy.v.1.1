package com.tyut.sssy.task.service;

import com.tyut.sssy.task.dao.TaskThresholdDao;

public class TaskThresholdService {

	/**
	 * 根据任务类型获取任务阈值
	 * @param rwLx
	 * @return
	 */
	public int getRwyzByRwlx(String rwLx) {
		
		TaskThresholdDao taskThresholdDao = new TaskThresholdDao();
		int rwyz = taskThresholdDao.getRwyzByRwlx(rwLx);
		
		return rwyz;
	}

	/**
	 * 根据任务类型设置任务阈值
	 * @param rwLx
	 * @param rwyz
	 */
	public void updateRwyzByRwlx(String rwLx, int rwyz) {
		TaskThresholdDao taskThresholdDao = new TaskThresholdDao();
		taskThresholdDao.updateRwyzByRwlx(rwLx, rwyz);
	}

}
