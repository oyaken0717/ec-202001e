package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
}
