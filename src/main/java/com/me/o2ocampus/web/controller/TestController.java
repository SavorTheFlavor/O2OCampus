package com.me.o2ocampus.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.me.o2ocampus.dao.AreaDaoTest;

@RestController
@RequestMapping("/test")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(AreaDaoTest.class);
	@RequestMapping
	public String test2() {
		logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return "Hey!";
	}
}
