package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author MaiOshiro
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** 注文情報をOrderドメインにセットするResultSetExtractor */
	private static final ResultSetExtractor<Order>ORDER_RESULT_SET_EXTRACTOR=(rs)->{
		boolean firstTimeOrder=true;
		Order order=new Order();
		Item item=new Item();
		OrderItem orderItem=new OrderItem();
		List<OrderTopping>orderToppingList=new ArrayList<>();
		List<OrderItem>orderItemList=new ArrayList<>();
		int firstOrderItemId=0;
		
		while(rs.next()) {
			if(firstTimeOrder) {
				order.setId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("order_user_id"));
				order.setStatus(rs.getInt("order_status"));
				order.setTotalPrice(rs.getInt("order_total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("order_destination_name"));
				order.setDestinationEmail(rs.getString("order_destination_email"));
				order.setDestinationZipcode(rs.getString("order_destination_zipcode"));
				order.setDestinationAddress(rs.getString("order_destination_address"));
				order.setDestinationTel(rs.getString("order_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("order_delivery_time"));
				order.setPaymentMethod(rs.getInt("order_payment_method"));
				order.setOrderItemList(orderItemList);
				firstTimeOrder=false;
			}
			if(rs.getInt("orderitem_id") != firstOrderItemId) {
				orderItemList.add(orderItem);
				orderItem.setId(rs.getInt("orderitem_id"));
				orderItem.setItemId(rs.getInt("orderitem_item_id"));
				orderItem.setOrderId(rs.getInt("orderitem_order_id"));
				orderItem.setQuantity(rs.getInt("orderitem_quantity"));
				orderItem.setSize(rs.getString("orderitem_size").toCharArray()[0]);
				orderItem.setItem(item);
				orderItem.setOrderToppingList(orderToppingList);
				
				item.setId(rs.getInt("item_id"));
				item.setName(rs.getString("item_name"));
				item.setDescription(rs.getString("item_description"));
				item.setPriceM(rs.getInt("item_price_m"));
				item.setPriceL(rs.getInt("item_price_l"));
				item.setImagePath(rs.getString("item_image_path"));
				item.setDeleted(rs.getBoolean("item_deleted"));
				
				Topping topping=new Topping();
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
			}
			firstOrderItemId=rs.getInt("orderitem_id");
		}
		return order;
	};
	
	/**
	 * 注文情報を取得するメソッド.
	 * 
	 * @param userId ユーザーID
	 * @param status 注文の状態
	 * @return　取得した注文情報を入れるOrderオブジェクト
	 */
	public Order findByUserIdAndStatus(Integer userId,Integer status) {
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT i.id item_id,i.name item_name,i.description item_description,i.price_m item_price_m,");
		sql.append("i.price_l item_price_l,i.image_path item_image_path,i.deleted item_deleted,");
		sql.append("o.id order_id,o.user_id order_user_id,o.status order_status,o.total_price order_total_price,");
		sql.append("o.order_date order_date,o.destination_name order_destination_name,o.destination_email order_destination_email,");
		sql.append("o.destination_zipcode order_destination_zipcode,o.destination_address order_destination_address,");
		sql.append("o.destination_tel order_destination_tel,o.delivery_time order_delivery_time,o.payment_method order_payment_method,");
		sql.append("oi.id orderitem_id,oi.item_id orderitem_item_id,oi.order_id orderitem_order_id, oi.quantity orderitem_quantity,oi.size orderitem_size,");
		sql.append("ot.id order_topping_id,ot.topping_id topping_id,");
		sql.append("t.name topping_name,t.price_m topping_price_m,t.price_l topping_price_l ");
		sql.append("FROM orders o JOIN order_items oi ON o.id = oi.id ");
		sql.append("LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append("INNER JOIN items i ON oi.item_id = i.id LEFT OUTER JOIN toppings t ON ot.topping_id = t.id ");
		sql.append("WHERE o.user_id=:user_id AND o.status=:status ORDER BY oi.id");
		SqlParameterSource param=new MapSqlParameterSource().addValue("user_id",userId).addValue("status",status);
		Order order=template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}
	

}

