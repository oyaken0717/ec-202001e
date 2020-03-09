package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.OrderService;

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
	private OrderService orderService;

	/**
	 * 注文履歴を表示するメソッド.
	 * 
	 * @param model
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("")
	public String showOrderHisotry(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		
		Integer userId=loginUser.getUser().getId();
		List<Order> orderList = orderService.showOrderHistory(userId);
		if(orderList==null) {
			model.addAttribute("message","注文履歴はありません");
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	}
	
}
