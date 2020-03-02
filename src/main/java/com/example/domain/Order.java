package com.example.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * orderテーブルのドメインクラス.
 * 
 * @author MaiOshiro
 *
 */
public class Order {

	/** 注文ID */
	private Integer id;
	/** ユーザーID*/
	private Integer userId;
	/** 注文の状態*/
	private Integer status;
	/** 合計金額*/
	private Integer totalPrice;
	/** 注文日 */
	private Date orderDate;
	/** 配達先氏名 */
	private String destinationName;
	/** 配達先Eメールアドレス */
	private String destinationEmail;
	/** 配達先郵便番号*/
	private String destinationZipcode;
	/** 配達先住所 */
	private String destinationAddress;
	/** 配達先電話番号*/
	private String destinationTel;
	/** 配達時間 */
	private Timestamp deliveryTime;
	/** 支払い方法 */
	private Integer paymentMethod;
	/** ユーザー */
	private User user;
	/** 注文商品一覧*/
	private List<OrderItem>orderItemList;
	
	public int getCalcTotalPrice() {
		int total=0;
		for(OrderItem orderItem:orderItemList) {
			total+=orderItem.getSubTotal();
		}
		return total;
	}
	
	/**
	 * 消費税のgetter.
	 * 
	 * @return
	 */
	public int getTax() {
		return (int)(getCalcTotalPrice()*0.1);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	
}
