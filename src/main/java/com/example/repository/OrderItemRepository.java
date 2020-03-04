package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
		String sql = "INSERT INTO order_items(item_id,order_id,quantity,size) values(:itemId,:orderId,:quantity,:size)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		int orderItemId = template.update(sql, param);
		orderItem.setId(orderItemId);
		return orderItem;
	}
	
	/**
	 * カートの商品を削除するメソッド.
	 * 
	 * @param id　itemの主キー
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM order_items WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
