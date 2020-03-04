package com.example.service;

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
	
	public void insert(AddShoppingCartForm form, int userId) {
		Order userOrder = OrderRepository.findByUserIdAndStatus(userId, 0);
		//ショッピングカートに何も入っていない（orderのstatusがない）とき、order,orderItem,orderToppingの3つをinsert。
		//そうでないときはorderItem,orderToppingの2つをinsert。
		if (userOrder.getStatus() == null) {
			//OrderオブジェクトにuserIdとstatusを格納
			Order order = new Order();
			order.setUserId(userId);
			order.setStatus(0);
			order = OrderRepository.insert(order);
			
			//OrderItemオブジェクトにformとorderId格納
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(order.getId());
			orderItem = OrderItemRepository.insert(orderItem);
			
			System.out.println(form);
			//OrderToppingオブジェクトにtoppingIdとorderItemIdを格納
			for (Integer topping: form.getOrderToppingList()) {			
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(topping);
				orderTopping.setOrderItemId(orderItem.getId());
				orderToppingRepository.insert(orderTopping);
			}
		} else {
			//OrderItemオブジェクトにformとorderId格納
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(form, orderItem);
			orderItem.setOrderId(userOrder.getId());
			orderItem = OrderItemRepository.insert(orderItem);
			System.out.println(form);
			//OrderToppingオブジェクトにtoppingIdとorderItemIdを格納
			for (Integer topping: form.getOrderToppingList()) {				
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(topping);
				orderTopping.setOrderItemId(orderItem.getId());
				orderToppingRepository.insert(orderTopping);
			}
		}
	}
}
