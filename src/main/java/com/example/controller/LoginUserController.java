package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.LoginUserService;

/**
 * ユーザー情報を操作するコントローラー.
 * 
 * @author oyamadakenji
 *
 */
@Controller
@RequestMapping("/login-user")
public class LoginUserController {

	@Autowired
	private HttpSession session;

	@Autowired
	private LoginUserService loginUserService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		return new LoginUserForm();
	}

	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/to-login")
	public String toLogin() {
		return "login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form ユーザーログイン用フォーム
	 * @return ログイン後の商品一覧画面
	 */
	@RequestMapping("/login")
	public String login(@Validated LoginUserForm form, BindingResult result, Model model) {

		if (result.hasErrors()) {
			result.rejectValue("email", null, "メールアドレス、またはパスワードが間違っています");
			return toLogin();
		}
		User user = loginUserService.findByEmail(form.getEmail());
		if (user == null) {
			result.rejectValue("email", null, "登録されていません");
			return toLogin();
		}
		session.setAttribute("user", user);
		return "item_list_noodle";
	}

	/**
	 * ログアウトをします.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/login-user/to-login";
	}

}
