package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/** 注文情報をOrderドメインにセットするResultSetExtractor */
	private static final ResultSetExtractor<Order>ORDER_RESULT_SET_EXTRACTOR=(rs)->{
		Order order=new Order();
		List<OrderTopping>orderToppingList=new ArrayList<>();
		List<OrderItem>orderItemList=new ArrayList<>();

		List<Topping> toppingList = new ArrayList<>();
		int firstOrderItemId=0;
		int beforeOrderId = 0;
		
		while(rs.next()) {
			int nowUserId = rs.getInt("order_id");
			if(nowUserId != beforeOrderId) {
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
				
//				preId=rs.getInt("order_id");
			}
			if(rs.getInt("orderitem_id") != firstOrderItemId && rs.getInt("orderitem_id")!=beforeOrderId) {
				OrderItem orderItem=new OrderItem();
				Item item=new Item();
				toppingList = new ArrayList<>();
				orderToppingList = new ArrayList<>();
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
				item.setToppingList(toppingList);
			}
			if(rs.getInt("order_topping_id")!=0) {
				OrderTopping orderTopping = new OrderTopping();
				Topping topping=new Topping();
				toppingList.add(topping);
				orderToppingList.add(orderTopping);
				orderTopping.setId(rs.getInt("order_topping_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("ordert_item_id"));
				orderTopping.setTopping(topping);
				
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
			}
			firstOrderItemId=rs.getInt("orderitem_id");
			beforeOrderId = rs.getInt("order_id");
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
		sql.append("ot.id order_topping_id,ot.topping_id topping_id,ot.order_item_id ordert_item_id,");
		sql.append("t.name topping_name,t.price_m topping_price_m,t.price_l topping_price_l ");
		sql.append("FROM orders o JOIN order_items oi ON o.id = oi.order_id ");
		sql.append("LEFT OUTER JOIN order_toppings ot ON oi.id = ot.order_item_id ");
		sql.append("INNER JOIN items i ON oi.item_id = i.id LEFT OUTER JOIN toppings t ON ot.topping_id = t.id ");
		sql.append("WHERE o.user_id=:user_id AND o.status=:status ORDER BY oi.id");
		SqlParameterSource param=new MapSqlParameterSource().addValue("user_id",userId).addValue("status",status);
		Order order=template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}

	/**
	 * 注文内容とユーザー情報を登録するメソッド.
	 * 
	 * @param order
	 * @return
	 */
	/*public Order insertOrder(Order order) {
		if(order.getId()==null) {
			SqlParameterSource param = new BeanPropertySqlParameterSource(order);
			Number key = insert.executeAndReturnKey(param);
			order.setId(key.intValue());
		}
		return order;
	}*/
	/**
	 * Ordersテーブルを更新するメソッド.
	 * 
	 * @param order 注文情報
	 */
	public void updateOrder(Order order) {
		StringBuilder sql=new StringBuilder();
		sql.append("UPDATE orders SET user_id=:userId,status=:status,total_price=:totalPrice,order_date=:orderDate,");
		sql.append("destination_name=:destinationName,destination_email=:destinationEmail,destination_zipcode=:destinationZipcode,");
		sql.append("destination_address=:destinationAddress,destination_tel=:destinationTel,delivery_time=:deliveryTime,");
		sql.append("payment_method=:paymentMethod WHERE id=:id");
		SqlParameterSource param=new BeanPropertySqlParameterSource(order);
		template.update(sql.toString(), param);
	}
	/**
	 * カートに追加した時にordersテーブルに格納するメソッド.
	 * 
	 * @param order 注文情報
	 * @return id情報を持ったbオブジェクト
	 */
	public Order insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		if(order.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			order.setId(key.intValue());
		} else {
			
		}
		return order;
	}
	

}

