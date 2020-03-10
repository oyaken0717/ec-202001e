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

//	---------------------------------
	
//	private String user_id;
//	private String order_number;
//	private String amount;
	/** カード番号 */
	private String card_number;
	/** カード有効期限(年) */
	private String card_exp_year;
	/** カード有効期限(月) */
	private String card_exp_month;
	/** カード名義人 */
	private String card_name;
	/** セキュリティコード */
	private String card_cvv;

	
	
}