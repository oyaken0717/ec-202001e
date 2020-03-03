package com.example.form;

import java.util.List;

/**
 * ショッピングカートに追加するためのフォーム
 * 
 * @author yamadadai
 *
 */
public class AddShoppingCartForm {
	/**
	 * サイズ
	 */
	private Character size;
	/**
	 * トッピングリスト
	 */
	private List<Integer> toppingList;
	/**
	 * 数量
	 */
	private Integer quantity;
	/**
	 * itemID
	 */
	private Integer itemId;

	@Override
	public String toString() {
		return "AddShoppingCartForm [size=" + size + ", toppingList=" + toppingList + ", quantity=" + quantity
				+ ", itemId=" + itemId + "]";
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public List<Integer> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Integer> toppingList) {
		this.toppingList = toppingList;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
}
