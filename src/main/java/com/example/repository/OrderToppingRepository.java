package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * order_toppingsテーブルを操作するリポジトリ.
 * 
 * @author yamadadai
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * カートに追加した時にorder_toppingsテーブルに格納するメソッド.
	 * 
	 * @param orderTopping 注文したトッピング
	 */
	public void insert(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings(topping_id, order_item_id) values(:toppingId, :orderItemId)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}
	
	/**
	 * カートの商品に紐づいたトッピングを削除するメソッド.
	 * 
	 * @param id　orderToppingの主キー
	 */
	public void deleteById(int orderItemId) {
		String sql = "DELETE FROM order_toppings WHERE order_item_id=:orderItemId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderItemId", orderItemId);
		template.update(sql, param);
	}
}
