package com.example.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert=new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName=simpleJdbcInsert.withTableName("order_items");
		insert=withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * カートに追加した時にorder_itemsテーブルに格納するメソッド.
	 * 
	 * @param orderItem 注文商品
	 * @return id情報を持った注文商品
	 */
	public OrderItem insert(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		if(orderItem.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			orderItem.setId(key.intValue());
		}
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
