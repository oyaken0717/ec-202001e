package com.example.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Credit;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.OrderDestinationForm;
import com.example.service.CreditService;
import com.example.service.OrderService;
import com.example.service.SendMailService;

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
	
	@Autowired
	private SendMailService sendMailService;
	
	@ModelAttribute
	public OrderDestinationForm setUpForm() {
		return new OrderDestinationForm();
	}
	@Autowired
	private CreditService creditService;

	@Bean
	RestTemplate RestTemplate() {
		return new RestTemplate();
	}

	
	/**
	 * 注文情報を表示するメソッド.
	 * 
	 * @param model モデル
	 * @return　注文商品表示画面
	 */
	@RequestMapping("")
	public String toOrder(OrderDestinationForm form,@AuthenticationPrincipal LoginUser loginUser,Model model) {
		int status=0;
		
		if (loginUser == null) {
			return "redirect:/login-user/to-login";
		}
		User user=loginUser.getUser();
		Integer userId = user.getId();
		Order order=orderService.findByUserIdAndStatus(userId,status);
		if(order == null) {
			return "redirect:/";
		}
		form.setDestinationName(loginUser.getUser().getName());
		form.setDestinationEmail(loginUser.getUser().getEmail());
		form.setDestinationZipcode(loginUser.getUser().getZipcode());
		form.setDestinationAddress(loginUser.getUser().getAddress());
		form.setDestinationTel(loginUser.getUser().getTelephone());
		
		model.addAttribute("olderDestinationForm",form);
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
	public String order(@Validated OrderDestinationForm form,BindingResult result,@AuthenticationPrincipal LoginUser loginUser,Model model) {
		
		Timestamp strDeliveryTime=null;
		if(!form.getDeliveryDate().equals("")&&!form.getDeliveryTime().equals("")) {
			strDeliveryTime=orderService.strTimestamp(form.getDeliveryDate()+","+form.getDeliveryTime());
			LocalDateTime localDateTime=LocalDateTime.now();
			Timestamp nowPlusOneHour=Timestamp.valueOf(localDateTime.plusHours(1));
			if(!nowPlusOneHour.before(strDeliveryTime)) {
				result.rejectValue("deliveryDate", null, "配達時間は現時刻の1時間後から指定できます");
			}
		}
		if(result.hasErrors()) {
			return toOrder(form,loginUser,model);
		}
		
		Integer status=0;
		User user = loginUser.getUser();
		Integer userId =user.getId();
		Order order=orderService.findByUserIdAndStatus(userId, status);
		
		order.setTotalPrice(order.getCalcTotalPrice());
		order.setOrderDate(new Date());
		order.setDestinationName(form.getDestinationName());
		order.setDestinationEmail(form.getDestinationEmail());
		order.setDestinationZipcode(form.getDestinationZipcode());
		order.setDestinationAddress(form.getDestinationAddress());
		order.setDestinationTel(form.getDestinationTel());
		order.setStatus(Integer.valueOf(form.getPaymentMethod()));
		order.setDeliveryTime(strDeliveryTime);
	//	order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		
		if("1".equals(form.getPaymentMethod())) {
			order.setPaymentMethod(Integer.valueOf(form.getPaymentMethod()));
		}
		if("2".equals(form.getPaymentMethod())) {
			Credit credit = creditService.service(form);
			if ("error".equals(credit.getStatus())) {
				System.out.println("失敗");
				model.addAttribute("message", "クレジットカード情報が不正です。");
				return toOrder(form,loginUser,model);
			}
		}
		orderService.order(order);
		sendMailService.sendOrderMail(order);
		

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
	/**
	 * 注文確認画面でカートの注文情報を削除するメソッド.
	 * 
	 * @param id　order_itemsテーブルのid
	 * @return 注文確認画面
	 */
	@RequestMapping("/deleteOrder")
	public String deleteOrder(Integer orederItemId) {
		orderService.deleteById(orederItemId);
		return "redirect:/orderconfirm";
	}
	
	
}
