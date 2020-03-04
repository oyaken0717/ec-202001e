package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.SearchItemForm;
import com.example.service.SearchItemService;

/**
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/searchItem")
public class SearchItemController {

	/**
	 * searchItemFormをインスタンス化
	 * @return　SearchItemForm();
	 */
	@ModelAttribute
	public SearchItemForm setUuSearchItemForm() {
		return new SearchItemForm();
	}

	@Autowired
	private HttpSession session;

	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 商品一覧への遷移（あいまい検索された場合はその商品のみ表示）.
	 * @param model　モデル
	 * @param form
	 * @return商品一覧表示画面
	 */
	@RequestMapping("/")
	public String searchItem(Model model, SearchItemForm form) {
		if (form.getName() == null) {
			List<Item> itemList = searchItemService.showItemList();
			model.addAttribute("itemList", itemList);
	}else {
			List<Item> itemList = searchItemService.SearchByLikeName(form.getName());
			model.addAttribute("itemList", itemList);
		}
		return "item_list_noodle";

	}
}
