package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Item;

public class ItemRepository {
	
	 @Autowired
	 private NamedParameterJdbcTemplate template;
	 
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString(""));
		item.setPriceM(rs.getInt(""));
		item.setPriceL(rs.getInt(""));
		item.setImagePath(rs.getString(""));
		item.setDeleted(rs.getBoolean(""));
		return item;
	};
	
	public List<Item> findAllItem() {
		String sql="select * from items order by id";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
		
	}
	
	

}
