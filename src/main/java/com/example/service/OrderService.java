package com.example.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;

import com.example.repository.OrderRepository;


/**
 * 注文情報を表示するサービス.
 * 
 * @author MaiOshiro
 *
 */
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;


	/**
	 * UserIdとStatusから注文情報を取得するメソッド.
	 * 
	 * @param userId ログインしているユーザーのID
	 * @param status 注文の状態
	 * @return 注文情報
	 */
	public Order findByUserIdAndStatus(Integer userId,Integer status) {
		return orderRepository.findByUserIdAndStatus(userId,status);
	}

}
