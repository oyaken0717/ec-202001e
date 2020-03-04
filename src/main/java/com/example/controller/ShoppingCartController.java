package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.AddShoppingCartForm;
import com.example.service.ShoppingCartService;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService service;
	
	@ModelAttribute
	public AddShoppingCartForm setAddShoppingCartForm() {
		return new AddShoppingCartForm();
	}
	
	@RequestMapping("")
	public String index() {
		return "item_detail";
	}
	
	@RequestMapping("/insert")
	public String insert(AddShoppingCartForm form, int userId) {
		service.insert(form, userId);
		return "cart_list";
	}
}
