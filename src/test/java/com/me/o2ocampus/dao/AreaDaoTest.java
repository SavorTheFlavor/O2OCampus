package com.me.o2ocampus.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.me.o2ocampus.BaseTest;
import com.me.o2ocampus.entity.Area;

public class AreaDaoTest extends BaseTest{
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void test1() {
		Area area = areaDao.findOne();
		System.out.println(area.getAreaName());
	}
}
