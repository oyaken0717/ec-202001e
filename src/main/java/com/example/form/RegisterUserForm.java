package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 登録画面からリクエストパラメータを受け取るフォームクラス.
 * 
 * @author masashi.nose
 *
 */
public class RegisterUserForm {

	/** 名前 */
	@NotBlank(message="名前を入力してください。")
	private String name;
	/** メールアドレス */
	@Email(message="メールアドレスの形式ではありません。")
	@NotBlank(message="メールアドレスを入力してください。")
	private String email;
	/** 郵便番号 */
	@NotBlank(message="郵便番号を入力してください。")
	private String zipcode;
	/** 住所 */
	@NotBlank(message="住所を入力してください。")
	private String address;
	/** 電話 */
	@Pattern(regexp="0\\d{1,4}\\d{1,4}\\d{4}", message="電話番号を入力してください。")
	private String telephone;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください。")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message="確認用パスワードを入力してください。")
	private String confirmationPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", zipcode=" + zipcode + ", address=" + address
				+ ", telephone=" + telephone + ", password=" + password + ", confirmationPassword=" + confirmationPassword + "]";
	}

}
