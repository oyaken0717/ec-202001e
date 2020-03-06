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
	
	private List<Integer> orderToppingList;
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
		return "AddShoppingCartForm [size=" + size + ", orderToppingList=" + orderToppingList + ", quantity=" + quantity
				+ ", itemId=" + itemId + "]";
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	

	public List<Integer> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<Integer> orderToppingList) {
		this.orderToppingList = orderToppingList;
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
