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
@RequestMapping("")
public class OrderHistoryController {

	@Autowired
	private OrderHistoryService orderHistoryService;
	
	@RequestMapping("/orderHistory")
	public String showOrderHisotry(Model model/*LoginUser loginUser*/) {
		User user=new User();
		Integer userId=user.getId();
		List<Order>orderList=orderHistoryService.showOrderHistory(userId);
		for(int i=0;i<orderList.size();i++) {
			Integer status=orderList.get(i).getStatus();
			if(status==0) {
				orderList.remove(i);
			}
		}
		model.addAttribute("orderList",orderList);
		return "order_history";
	}
	
}
