package com.tyut.sssy.junit;

import com.tyut.sssy.data.dao.DataDao;
import com.tyut.sssy.data.domain.DataImportParameter;

import junit.framework.TestCase;

public class SoftwareDataDaoTest extends TestCase {
	public void dataImport() {
		DataImportParameter softwareData = new DataImportParameter();
		softwareData.setFxqNd("2011");
		softwareData.setFxqYf("12");
		softwareData.setFxqSssqQ("01");
		softwareData.setFxqSssqZ("31");
		softwareData.setPd("日");
		softwareData.setRkbz("Y");
		softwareData.setSjbz("Y");
		softwareData.setDjbz("Y");
		softwareData.setQjbz("Y");
		softwareData.setSxdmbz("Y");
		softwareData.setSxdjxx("Y");
		
		DataDao softwareDataDao = new DataDao();
		softwareDataDao.dataImport(softwareData);
	}
}
