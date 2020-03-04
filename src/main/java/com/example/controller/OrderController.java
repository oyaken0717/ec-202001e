package com.example.controller;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderDestinationForm;
import com.example.service.OrderService;

/**
 * 注文情報を表示するコントローラ.
 * 
 * @author MaiOshiro
 *
 */
@Controller
@RequestMapping("/orderconfirm")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@ModelAttribute
	public OrderDestinationForm setUpForm() {
		return new OrderDestinationForm();
	}
	
	/**
	 * 注文情報を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return　注文商品表示画面
	 */
	@RequestMapping("")
	public String toOrder(Model model) {
		int status=0;
		User user=new User();
		Integer userId=user.getId();
		Order order=orderService.findByUserIdAndStatus(1,status);
		model.addAttribute("order",order);
		
		return "order_confirm";
	}
	/**
	 * 注文するメソッド.
	 * 
	 * @param form 配達先情報の詰まったフォーム
	 * @param result
	 * @param model　
	 * @return 注文確認画面にリダイレクト
	 */
	@RequestMapping("/order")
	public String order(@Validated OrderDestinationForm form,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return toOrder(model);
		}
		Timestamp strDeliveryTime=null;
		strDeliveryTime=orderService.strTimestamp(form.getDeliveryDate()+""+form.getDeliveryTime());
		
		Integer status=0;
		User user=new User();
		Integer userId=user.getId();
		Order order=orderService.findByUserIdAndStatus(userId, status);
			
		order.setStatus(Integer.valueOf(form.getPaymentMethod()));
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setDeliveryTime(strDeliveryTime);
		order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		
		orderService.order(order);
		
		return "redirect:/orderfinish";
		
	}
	/**
	 * 注文完了画面を表示するメソッド.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/orderFinish")
	public String finish() {
		return "order_finished";
	}
}
