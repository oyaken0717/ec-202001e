package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemsテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	
	/**
	 * カートに追加した時にorder_itemsテーブルに格納するメソッド.
	 * 
	 * @param orderItem 注文商品
	 * @return id情報を持った注文商品
	 */
	public OrderItem insert(OrderItem orderItem) {
		String sql = "INSERT INTO order_items(itemId,orderId,quantity,size) values(:itemId,:orderId,:quantity,:size)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		template.update(sql, param);
		
		return orderItem;
	}
}
