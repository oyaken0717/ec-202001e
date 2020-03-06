package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.repository.OrderHistoryRepository;

/**
 * 注文履歴画面を表示するサービス.
 * 
 * @author MaiOshiro
 *
 */
@Service
@Transactional
public class OrderHistoryService {

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;
	
	/**
	 * 注文履歴画面を表示するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報
	 */
	public List<Order> showOrderHistory(Integer userId) {
		List<Order>orderList=orderHistoryRepository.findByUserId(userId);
		return orderList;
	}
	
}
