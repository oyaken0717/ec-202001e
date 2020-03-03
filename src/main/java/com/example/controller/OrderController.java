package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.service.OrderService;

/**
 * 注文情報を表示するコントローラ.
 * 
 * @author MaiOshiro
 *
 */
@Controller
@RequestMapping("/confirmation")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	/**
	 * 注文情報を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return　注文商品表示画面
	 */
	public String toOrder(Model model) {
		Integer status=0;
		User user=new User();
		Integer userId=user.getId();
		
		Order order=orderService.findByUserIdAndStatus(userId, status);
		model.addAttribute("order",order);
		
		return "order_confirm";
	}
	
}
