package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
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
@RequestMapping("")
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

	@ModelAttribute
	public SortItemForm setUpSortItemForm() {
		return new SortItemForm();
	}

	@Autowired
	private SearchItemService searchItemService;

	/**
	 * 商品一覧への遷移（あいまい検索された場合はその商品のみ表示）.
	 * 
	 * @param model          モデル
	 * @param searchItemForm 検索された文字列を取得するフォーム
	 * @return 商品一覧表示画面
	 */
	@RequestMapping("")
	public String showItemList(Model model, SearchItemForm searchItemForm, Integer page) {
		List<Item> itemList = searchItemService.showItemList();
		List<Item> threeList = new ArrayList<>();
		List<List<Item>> bigItemList = new ArrayList<>();

		List<List<Item>> bigThreeList = new ArrayList<>();
		List<List<List<Item>>> superItemList = new ArrayList<>();

		for (int i = 0; i < itemList.size(); i++) {
			threeList.add(itemList.get(i));
			if (threeList.size() == 3) {
				bigItemList.add(threeList);
				threeList = new ArrayList<>();
			}
		}

		if (threeList.size() != 0) {
			bigItemList.add(threeList);			
		}

		for (int i = 0; i < bigItemList.size(); i++) {
			bigThreeList.add(bigItemList.get(i));
			if (bigThreeList.size() == 3) {
				superItemList.add(bigThreeList);
				bigThreeList = new ArrayList<>();
			}
		}
		
		if (bigThreeList.size() != 0) {
			superItemList.add(bigThreeList);			
		}

		StringBuilder itemListForAutocomplete = searchItemService.getItemListForAutoconplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
//		model.addAttribute("bigItemList", bigItemList);
		
		Integer index = 0;
		if (page != null) {
			index = page-1;
		}
		model.addAttribute("superItemList", superItemList);
		model.addAttribute("bigThreeList", superItemList.get(index));
		model.addAttribute("ichiran", 1);
		
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
	public String searchItemList(Model model, SearchItemForm searchItemForm, SortItemForm sortItemForm,Integer page) {
		List<List<Item>> bigItemList = showItemList(searchItemForm, model, sortItemForm,page);
		model.addAttribute("bigItemList", bigItemList);
		return "item_list_noodle";

	}

	/**
	 * 商品を表示させるための共通メソッド.
	 * 
	 * @param searchItemForm
	 * @param model
	 * @return 商品を3個入れたlistの詰まったlist
	 */
	private List<List<Item>> showItemList(SearchItemForm searchItemForm, Model model, SortItemForm sortItemForm,Integer page) {
		List<Item> itemList = searchItemService.SearchByLikeName(searchItemForm.getName());
		model.addAttribute("name", searchItemForm.getName());
		model.addAttribute("kensaku", searchItemForm.getName());
		if (sortItemForm.getElement().equals("high")) {
			Collections.sort(itemList, new ItemConparator());
			model.addAttribute("element", sortItemForm.getElement());
			model.addAttribute("narabikae", 1);
		}
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
					System.out.println(bigItemList);
				}
			}
		} else if (itemList.size() > 1 && itemList.size() < 3) {
			for (int i = 0; i < itemList.size(); i++) {
				threeList.add(itemList.get(i));
			}
			bigItemList.add(threeList);
		} else {
			for (int i = 0; i < itemList.size(); i++) {
				threeList.add(itemList.get(i));
				if (threeList.size() == 3) {
					bigItemList.add(threeList);
					threeList = new ArrayList<>();
				}
			}
//			if (threeList.size() < 3) {
//				bigItemList.add(threeList);
//			}
			if (threeList.size() != 0) {
				bigItemList.add(threeList);
			}		
		}

		List<List<Item>> bigThreeList = new ArrayList<>();
		List<List<List<Item>>> superItemList = new ArrayList<>();

		for (int i = 0; i < bigItemList.size(); i++) {
			bigThreeList.add(bigItemList.get(i));
			if (bigThreeList.size() == 3) {
				superItemList.add(bigThreeList);
				bigThreeList = new ArrayList<>();
			}
		}
		
		if (bigThreeList.size() != 0) {
			superItemList.add(bigThreeList);			
		}
		
		Integer index = 0;
		if (page != null) {
			index = page-1;
		}
		model.addAttribute("superItemList", superItemList);
		model.addAttribute("bigThreeList", superItemList.get(index));

		
		model.addAttribute("bigItemList", bigItemList);
		return bigItemList;
	}

}
