package com.me.o2ocampus.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.me.o2ocampus.dao.ShopDao;
import com.me.o2ocampus.dto.ShopExecution;
import com.me.o2ocampus.entity.Shop;
import com.me.o2ocampus.enums.ShopStateEnum;
import com.me.o2ocampus.exception.ShopOperationException;
import com.me.o2ocampus.service.ShopService;
import com.me.o2ocampus.util.ImageUtil;
import com.me.o2ocampus.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	
	@Autowired
	private ShopDao shopDao; 

	@Override
	public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
		Thread thread ;
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
		}
		shop.setEnableStatus(0); //经审核后才能让该商铺上线
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.insertShop(shop);
		if(effectedNum <= 0) {
			throw new ShopOperationException("addShop: failed to add the shop!");
		}else {
			if(shopImg != null) {
				addShopImg(shop, shopImg);
				effectedNum = shopDao.updateShop(shop);
				if(effectedNum <= 0) {
					throw new ShopOperationException("addShop: failed to add the shop Image!");
				}
			}
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
		String destAddr = PathUtil.getShopImagePath(shop.getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(shopImg, destAddr);
		shop.setShopImg(thumbnailAddr);
	}

}
