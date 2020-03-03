package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class SearchByFindItemService {

	@Autowired
	private ItemRepository itemRepository;

	public List<Item> showItemList() {
		List<Item> itemList = itemRepository.findAllItem();
		return itemList;
	}

	public List<Item> SearchByLikeName(String name) {
		List<Item> itemList = itemRepository.findByLikeName(name);
		return itemList;

	}

}
