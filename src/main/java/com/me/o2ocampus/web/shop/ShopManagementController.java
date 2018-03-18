package com.me.o2ocampus.web.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.o2ocampus.dto.ShopExecution;
import com.me.o2ocampus.entity.PersonInfo;
import com.me.o2ocampus.entity.Shop;
import com.me.o2ocampus.enums.ShopStateEnum;
import com.me.o2ocampus.service.ShopService;
import com.me.o2ocampus.util.CodeUtil;
import com.me.o2ocampus.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shop")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	
	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		MultipartHttpServletRequest multipartRequest = null;
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver multipartResolver = 
				new CommonsMultipartResolver(request.getServletContext());
		if (multipartResolver.isMultipart(request)) {
			multipartRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartRequest
					.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (shop != null && shopImg != null) {
			try {
				PersonInfo user = (PersonInfo) request.getSession()
						.getAttribute("user");
				shop.setOwnerId(user.getUserId());
				ShopExecution se = shopService.addShop(shop, shopImg);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 若shop创建成功，则加入session中，作为权限使用
					@SuppressWarnings("unchecked")
					List<Shop> shopList = (List<Shop>) request.getSession()
							.getAttribute("shopList");
					if (shopList != null && shopList.size() > 0) {
						shopList.add(se.getShop());
						// request.getSession().setAttribute("shopList", shopList);
					} else {
						shopList = new ArrayList<Shop>();
						shopList.add(se.getShop());
						request.getSession().setAttribute("shopList", shopList);
					}
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}
}
