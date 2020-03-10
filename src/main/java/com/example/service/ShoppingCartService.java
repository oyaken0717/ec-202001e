package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.AddShoppingCartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired
	private OrderRepository OrderRepository;
	
	@Autowired
	private OrderItemRepository OrderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	@ModelAttribute
	public AddShoppingCartForm setAddShoppingCartForm() {
		return new AddShoppingCartForm();
	}
	
	/**
	 * ショッピングカートに商品を追加するメソッド
	 * 
	 * @param form リクエストパラメータ
	 * @param userId　userId
	 */
	public void insert(AddShoppingCartForm form, int userId) {
		Order userOrder = OrderRepository.findByUserIdAndStatus(userId, 0);
		//ショッピングカートに何も入っていない（orderのstatusがない）とき、order,orderItem,orderToppingの3つをinsert。
		//そうでないときはorderItem,orderToppingの2つをinsert。
		if (userOrder == null) {
			//OrderオブジェクトにuserIdとstatusを格納
			Order order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order.setTotalPrice(0);
			order = OrderRepository.insert(order);
			//OrderItemオブジェクトにformとorderId格納
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(order.getId());
			orderItem = OrderItemRepository.insert(orderItem);
			
			//OrderToppingオブジェクトにtoppingIdとorderItemIdを格納
			if(form.getOrderToppingList() != null) {				
				for (Integer topping: form.getOrderToppingList()) {				
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setToppingId(topping);
					orderTopping.setOrderItemId(orderItem.getId());
					orderToppingRepository.insert(orderTopping);
				}
			}
		} else if (userOrder.getStatus() == 0) {
			//OrderItemオブジェクトにformとorderId格納
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(userOrder.getId());
			orderItem = OrderItemRepository.insert(orderItem);
			//OrderToppingオブジェクトにtoppingIdとorderItemIdを格納
			if(form.getOrderToppingList() != null) {				
				for (Integer topping: form.getOrderToppingList()) {				
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setToppingId(topping);
					orderTopping.setOrderItemId(orderItem.getId());
					orderToppingRepository.insert(orderTopping);
				}
			}
		}
	}
	
	/**
	 * ショッピングカートにある商品を削除するメソッド.
	 * 
	 * @param orderItemId orderItemId
	 */
	public void deleteOrderItem(int orderItemId) {
		orderToppingRepository.deleteById(orderItemId);
		OrderItemRepository.deleteById(orderItemId);
	}
	
	/**
	 * ショッピングカートを表示するためのメソッド.
	 * 
	 * @param userId userId
	 * @return 色々な情報が含まれた注文情報
	 */
	public Order showCartList(int userId) {
		Order userOrder = OrderRepository.findByUserIdAndStatus(userId, 0);
		
		return userOrder;
	}
	
	/**
	 * ログイン前に追加した商品をログイン後に反映し、削除するメソッド.
	 * 
	 * @param order_id オーダーID
	 * @param before_login_order_id　ログイン前のオーダーID
	 */
	public void saveBeforeLoginItem(int order_id, int before_login_order_id) {
		OrderItemRepository.saveBeforeLoginItem(order_id,before_login_order_id);
	}
}
