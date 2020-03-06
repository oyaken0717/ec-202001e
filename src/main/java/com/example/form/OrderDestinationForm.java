package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OrderDestinationForm {

	/** 配達先氏名 */
	@NotBlank(message = "名前を入力してください")
	private String destinationName;

	/** 配達先メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が間違っています")
	private String destinationEmail;

	/** 配達先郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	@Size(min = 1, max = 7, message = "郵便番号はハイフンを入れず数字7文字で入力してください")
	private String destinationZipcode;

	/** 配達先住所 */
	@NotBlank(message = "住所を入力してください")
	private String destinationAddress;

	/** 配達先電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	@Size(min = 1, max = 11, message = "電話番号はハイフンを入れず11文字以内で入力してください")
	private String destinationTel;

	/** 配達日 */
	@NotEmpty(message = "配達日を選択してください")
	private String deliveryDate;

	/** 配達時間 */
	@NotNull(message = "配達時間を指定してください")
	private String deliveryTime;

	/** 支払い方法 */
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