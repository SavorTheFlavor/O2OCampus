package com.me.o2ocampus.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.me.o2ocampus.BaseTest;
import com.me.o2ocampus.entity.Area;
import com.me.o2ocampus.entity.Shop;
import com.me.o2ocampus.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	
	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testAInsertShop() throws Exception {
		Shop shop = new Shop();
		shop.setOwnerId(1L);
		Area area = new Area();
		area.setAreaId(1L);
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shop.setShopName("mytest1");
		shop.setShopDesc("mytest1");
		shop.setShopAddr("testaddr1");
		shop.setPhone("13810524526");
		shop.setShopImg("test1");
		shop.setLongitude(1D);
		shop.setLatitude(1D);
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(0);
		shop.setAdvice("审核中");
		shop.setArea(area);
		shop.setShopCategory(sc);
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testQuery() throws Exception {
		Shop shop = shopDao.queryByShopId(15);
		System.out.println(shop);
	}
}
