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

@Controller
@RequestMapping("/")
public class SearchItemController {

	@ModelAttribute
	public SearchItemForm setUuSearchItemForm() {
		return new SearchItemForm();
	}

	@Autowired
	private HttpSession session;

	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping("/")
	public String searchItem(Model model, SearchItemForm form) {
		if (form.getName() == null) {
			List<Item> itemList = searchItemService.showItemList();
			model.addAttribute("itemList", itemList);
		} else {
			List<Item> itemList = searchItemService.SearchByLikeName(form.getName());
			model.addAttribute("itemList", itemList);
		}
		return "item_list_noodle";

	}
}
