package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.SearchItemService;

@Controller
@RequestMapping("/")
public class SearchItemController {

	@Autowired
	private HttpSession session;

	@Autowired
	private SearchItemService showitemListServise;

	@RequestMapping("/")
	public String searchItem(Model model) {
		
		List<Item> itemList = showitemListServise.showItemList();
		model.addAttribute("itemList", itemList);
		return "item_list_noodle";

	}

}
