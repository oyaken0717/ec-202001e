package com.example.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.SearchItemForm;
import com.example.form.SortItemForm;
import com.example.service.SearchItemService;

/**
 * 商品表示を操作するコントローラー.
 * 
 * @author ashibe
 *
 */
@Controller
@RequestMapping("/searchItem")
public  class SearchItemController   {


	/**
	 * searchItemFormをインスタンス化
	 * 
	 * @return SearchItemForm();
	 */
	@ModelAttribute
	public SearchItemForm setUuSearchItemForm() {
		return new SearchItemForm();
	}
	@ModelAttribute
	public SortItemForm setUpSortItemForm() {
		return new SortItemForm();
	}

	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 商品一覧への遷移（あいまい検索された場合はその商品のみ表示）.
	 * 
	 * @param model モデル
	 * @param form
	 * @return 商品一覧表示画面
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
		}
		model.addAttribute("bigItemList", bigItemList);

		return "item_list_noodle";
	}

	/**
	 * 商品曖昧検索の表示.
	 * 
	 * @param model
	 * @param form
	 * @return 商品一覧表示画面
	 */
	@RequestMapping("/searchItem")
	public String searchItemList(Model model, SearchItemForm searchItemform,SortItemForm sortItemForm) {
		List<Item> itemList = searchItemService.SearchByLikeName(searchItemform.getName());
		List<Item> threeList = new ArrayList<>();
		List<List<Item>> bigItemList = new ArrayList<>();

		if (itemList.size() == 0) {
			model.addAttribute("message", "該当する商品がありません");
			itemList = searchItemService.showItemList();
			for (int i = 0; i < itemList.size(); i++) {
				threeList.add(itemList.get(i));
				if (threeList.size() == 3) {
					bigItemList.add(threeList);
					threeList = new ArrayList<>();
				}
			}
			model.addAttribute("bigItemList", bigItemList);

		} else {
			if (itemList.size() < 3) {
				for (int i = 0; i < itemList.size(); i++) {
					threeList.add(itemList.get(i));
				} 
				bigItemList.add(threeList);
			} else {
				for (int i = 0; i < itemList.size(); i++) {
					threeList.add(itemList.get(i));
					if (threeList.size() == 3){
						bigItemList.add(threeList);
						threeList = new ArrayList<>();
					}
					
				}
				if(threeList.size()<3) {
					bigItemList.add(threeList);
				}

			}
			model.addAttribute("bigItemList", bigItemList);

		}
		return "item_list_noodle";
	}
	
	
}


