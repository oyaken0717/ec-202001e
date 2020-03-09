package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.AddShoppingCartForm;
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
	 * @param form リクエストパラメータ
	 * @param userId userId
	 * @return カートの中身を表示
	 */
	@RequestMapping("/insert")
	public String insert(AddShoppingCartForm form, int userId) {
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
		int userId = 0;
		try {			
			if (loginUser.getUser() != null) {
				userId = loginUser.getUser().getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Order order = service.showCartList(userId); 
		
		model.addAttribute("order", order);
		model.addAttribute("orderItemList", order.getOrderItemList());
		return "cart_list";
	}
}
