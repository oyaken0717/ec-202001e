package com.example.service;



import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		Order order=orderRepository.findByUserIdAndStatus(userId,status);
		return order;
	}

	/**
	 * 注文をするメソッド.
	 * 
	 * @param order 注文情報
	 */
	public void order(Order order) {
		orderRepository.save(order);
	}
	

	private static final String TIMESTAMP = "yyyy-MM-dd,h";
	

	/**
	 * 日付をTimestamp型に変換するメソッド.
	 * 
	 * @param time
	 * @return
	 */
	public Timestamp strTimestamp(String time) {
	try {
			return new Timestamp(new SimpleDateFormat(TIMESTAMP).parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
