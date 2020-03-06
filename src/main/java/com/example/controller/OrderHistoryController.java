package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.OrderHistoryService;

/**
 * 注文履歴を表示するコントローラー.
 * 
 * @author MaiOshiro
 *
 */
@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderHistoryService;

	@RequestMapping("")
	public String showOrderHisotry(Model model/*, LoginUser loginUser */) {
		User user = new User();//本来は消す
		Integer userId = user.getId();//loginUser.getAdministrator().getId();
		List<Order> orderList = orderHistoryService.showOrderHistory(1);
		for (int i = 0; i < orderList.size(); i++) {
			Integer status = orderList.get(i).getStatus();
			if (status == 0) {
				orderList.remove(i);
			}
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	}
	@RequestMapping("/goShoppingList")
	public String goShoppingList() {
		return "item_list_noodle";
	}
	
}
