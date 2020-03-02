package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	private LoginUserService loginUserService;
	
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/to-login")
	public String toLogin() {
		return "login";
	}

	
}
