package com.example.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	public String toOrder(/*LoginUser loginUser,*/Model model) {
		int status=0;
		User user=new User();//loginUser.getAdministrator();
		
		Integer userId=user.getId();
		Order order=orderService.findByUserIdAndStatus(2,status);
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
	public String order(@Validated OrderDestinationForm form,BindingResult result,/*LoginUser loginUser,*/Model model) {
		
		Timestamp strDeliveryTime=null;
		strDeliveryTime=orderService.strTimestamp(form.getDeliveryDate()+","+form.getDeliveryTime());
		LocalDateTime localDateTime=LocalDateTime.now();
		Timestamp nowPlusOneHour=Timestamp.valueOf(localDateTime.plusHours(1));
		if(!nowPlusOneHour.before(strDeliveryTime)) {
			result.rejectValue("deliveryDate", null, "配達時間は現時刻の1時間前を指定してください");
		}
		if(result.hasErrors()) {
			return toOrder(/*loginUser,*/model);
		}
		
		Integer status=0;
		User user=new User();//loginUser.getAdministrator();
		Integer userId=user.getId();
		Order order=orderService.findByUserIdAndStatus(1, status);
		
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setStatus(Integer.valueOf(form.getPaymentMethod()));
		order.setDeliveryTime(strDeliveryTime);
		order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		
		orderService.order(order);
		

		return "redirect:/orderconfirm/orderFinish";
		
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
