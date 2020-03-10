package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.repository.ItemDetailService;

/**
 * 商品詳細情報ページを操作するコントローラ.
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/detail")
public class ShowItemDetailController {

	@Autowired
	private ItemDetailService itemDetailService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 商品詳細ページへ遷移します.
	 * 
	 * @param id    ID
	 * @param model リクエストフォーム作成
	 * @return 商品詳細ページ
	 */
	@RequestMapping("/showDetail")
	public String showDetail(Integer id, @AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId == null) {
			session.setAttribute("userId", session.getId().hashCode());
		}
		
		if (loginUser != null) {
			userId = loginUser.getUser().getId();
		}
		Item item = itemDetailService.showDetail(id);
		item.setToppingList(itemDetailService.showToppingList());
		
		model.addAttribute("userId", userId);
		model.addAttribute("item", item);
		
		

		return "item_detail";
	}

}
