package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.domain.Topping;

/**
 * 商品詳細情報を表示する業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class ItemDetailService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * 
	 * 商品詳細情報を表示します.
	 * 
	 * @param id ID
	 * @return 商品情報が詰まったオブジェクト.
	 */
	public Item showDetail(Integer id) {
		return itemRepository.load(id);
	}

	/**
	 * トッピング情報一覧を表示します.
	 * 
	 * @return トッピング情報一覧
	 */
	public List<Topping> showToppingList() {
		return toppingRepository.findAll();
	}

}
