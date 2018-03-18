package com.me.o2ocampus.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.me.o2ocampus.dto.ShopExecution;
import com.me.o2ocampus.entity.Shop;

public interface ShopService {
	/**
	 * 创建商铺
	 * 
	 * @param Shop
	 *            shop
	 * @return ShopExecution shopExecution
	 * @throws Exception
	 */
	ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
}
