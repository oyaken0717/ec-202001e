package com.example.controller;

import java.util.ArrayList;
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
	 * 
	 * @return SearchItemForm();
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
	 * 
	 * @param model モデル
	 * @param form
	 * @return商品一覧表示画面
	 */
	@RequestMapping("/")
	public String showItemList(Model model) {

		List<Item> itemList = searchItemService.showItemList();
		List<Item> threeList = new ArrayList<>();
		List<List<Item>> bigItemList = new ArrayList<>();
		for (int i = 0; i < itemList.size(); i++) {
			threeList.add(itemList.get(i));
			if (threeList.size() == 3) {
				bigItemList.add(threeList);
				threeList = new ArrayList<>();
		}
//				else {
//				bigItemList.add(threeList);
//			}
		}
		model.addAttribute("bigItemList",bigItemList);

//		for (Item item : itemList) {
//			int i = item.getId();
//			if (i < 3) {
//				for (i = 0; i < 3; i++) {
//					List<Item> itemList1 = new ArrayList<>();
//					itemList1.addAll(itemList);
//				}
//			} else {
//				
//				List<Item> itemList2 = new ArrayList<>();
//				itemList2.addAll(itemList);
//			}
//		}
		return "item_list_noodle";
	}

	@RequestMapping("/searchItem")
	public String searchItemList(Model model, SearchItemForm form) {
		List<Item> itemList = searchItemService.SearchByLikeName(form.getName());
		if (itemList.size() == 0) {
			model.addAttribute("message", "該当する商品がありません");
			List<Item> itemList1 = searchItemService.showItemList();
			model.addAttribute("itemList", itemList1);
		} else {
			model.addAttribute("itemList", itemList);
		}
		return "item_list_noodle";
	}
}