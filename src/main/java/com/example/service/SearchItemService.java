package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 愛店検索を行うサービス.
 * @author ashibe
 *
 */
@Service
@Transactional
public class SearchItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 全件検索を行う
	 * @return　アイテムリスト
	 */
	public List<Item> showItemList() {
		List<Item> itemList = itemRepository.findAllItem();
		return itemList;
	}

	/**
	 * 名前の曖昧検索を行う
	 * @param name
	 * @return　アイテムリスト
	 */
	public List<Item> SearchByLikeName(String name) {
		List<Item> itemList = itemRepository.findByLikeName(name);
		return itemList;
	}

	/**
	 * オートコンプリート機能を追加するためのメソッド.
	 * @param itemList
	 * @return オートコンプリートのための文字列をデータベースから取得した配列
	 */
	public StringBuilder getItemListForAutoconplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");

		}
		System.out.println(itemListForAutocomplete);
		return itemListForAutocomplete;
	}

}
