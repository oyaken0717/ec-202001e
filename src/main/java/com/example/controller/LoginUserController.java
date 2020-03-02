package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String login(LoginUserForm form,Model model) {
		User user = loginUserService.findByEmail(form.getEmail());
		if (user == null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です。");
			return toLogin();
		}
		session.setAttribute("user", user);
		return "item_list_noodle";
	}


	
}
