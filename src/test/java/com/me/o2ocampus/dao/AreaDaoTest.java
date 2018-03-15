package com.me.o2ocampus.dao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.me.o2ocampus.BaseTest;
import com.me.o2ocampus.entity.Area;


public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
	
	private Logger logger = LoggerFactory.getLogger(AreaDaoTest.class);
	
	@Test
	public void test1() {
		logger.debug("testing AreaDao......");
		Area area = areaDao.findOne();
		System.out.println(area.getAreaName());
	}
}
