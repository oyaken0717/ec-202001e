package com.example.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class OrderDestinationForm {

	/** 配達先氏名 */
	@NotBlank(message="名前を入力してください")
	private String destinationName;
	
	/** 配達先メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	private String destinationEmail;
	
	/** 配達先郵便番号*/
	@NotBlank(message="郵便番号を入力してください")
	private String destinationZipcode;
	
	/**　配達先住所 */
	@NotBlank(message="住所を入力してください")
	private String destinationAddress;
	
	/** 配達先電話番号 */
	@NotBlank(message="電話番号を入力してください")
	private String destinationTel;
	
	/** 配達日*/
	@NotEmpty(message="配達日を選択してください")
	private String deliveryDate;
	
	/** 配達時間 */
	private String deliveryTime;
	
	/**　支払い方法*/
	private String paymentMethod;

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

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
	
}