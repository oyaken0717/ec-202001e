package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.AddShoppingCartForm;
import com.example.service.OrderService;
import com.example.service.ShoppingCartService;

/**
 * ショッピングカート関連の情報を操作するコントローラ.
 * 
 * @author yamadadai
 *
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService service;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public AddShoppingCartForm setAddShoppingCartForm() {
		return new AddShoppingCartForm();
	}

	/**
	 * 商品詳細を表示.
	 * 
	 * @return 商品詳細
	 */
	@RequestMapping("")
	public String index() {
		return "item_detail";
	}

	/**
	 * ショッピングカートに商品を追加.
	 * 
	 * @param form   リクエストパラメータ
	 * @param userId userId
	 * @return カートの中身を表示
	 */
	@RequestMapping("/insert")
	public String insert(AddShoppingCartForm form, @AuthenticationPrincipal LoginUser loginUser) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			session.setAttribute("userId", session.getId().hashCode());
		}
		
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		
		service.insert(form, userId);
		return "redirect:/cart/showList";
	}

	/**
	 * ショッピングカートから商品を削除.
	 * 
	 * @param orderItemId orderItemId
	 * @return カートの中身を表示
	 */
	@RequestMapping("/delete")
	public String delteOrderItem(int orderItemId) {
		service.deleteOrderItem(orderItemId);
		return "redirect:/cart/showList";
	}

	/**
	 * ショッピングカートの中身を表示.
	 * 
	 * @param model リクエストスコープ
	 * @return カートの中身を表示
	 */
	@RequestMapping("/showList")
	public String showcartList(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		//ログイン前の仮userIdをsessionスコープに保持
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			session.setAttribute("userId", session.getId().hashCode());
			userId = (Integer)session.getAttribute("userId");
		}
		//ログイン前にカートに追加した時に生成されるorder情報を取得
		Order beforeLoginOrder = orderService.findByUserIdAndStatus(userId, 0);
		
		//ログインしているidを元にorder情報を取得
		Order loginOrder = new Order();
		if (loginUser != null) {
			loginOrder = orderService.findByUserIdAndStatus(loginUser.getUser().getId(), 0);
			userId = loginUser.getUser().getId();
		}
		
		//ログイン前に追加した商品を、ログイン後に反映
		if(beforeLoginOrder != null && loginUser != null) {
			//ログイン後のオーダー情報がない場合に作成
			if (loginOrder == null) {
				loginOrder = new Order();
				loginOrder.setUserId(userId);
				loginOrder.setStatus(0);
				loginOrder.setTotalPrice(0);
				orderService.insert(loginOrder);
			}
			service.saveBeforeLoginItem(loginOrder.getId(), beforeLoginOrder.getId());
			orderService.deleteOrderById(beforeLoginOrder.getId());
		}			
		
		
		Order order = service.showCartList(userId); 
		if (order == null) {
			order = new Order();
		}
		model.addAttribute("order", order);
		model.addAttribute("orderItemList", order.getOrderItemList());
		return "cart_list";
	}
}
