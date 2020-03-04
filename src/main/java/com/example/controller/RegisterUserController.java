package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * usersテーブルを操作するコントローラです.
 * 
 * @author masashi.nose
 *
 */
@Controller
@RequestMapping("/register-user")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;

	@ModelAttribute
	public RegisterUserForm setUpForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザー情報登録画面に遷移します.
	 * 
	 * @return ユーザー情報登録画面
	 */
	@RequestMapping("/toRegister")
	public String toRegister() {
		return "register";
	}

	/**
	 * 
	 * ユーザー情報を登録します. 登録成功の場合、ログイン画面へ遷移します. メールアドレスが重複した場合、登録画面へ戻りエラーメッセージを表示させます.
	 * 
	 * @param form   リクエストパラメータ
	 * @param result 入力値チェック
	 * @return （成功時）ログイン画面/（失敗時）登録画面
	 */
	@RequestMapping("/register")
	public String register(@Validated RegisterUserForm form, BindingResult result) {
		
		User existUser = registerUserService.findByEmail(form.getEmail());
		
		System.out.println(form);

		if (!(existUser == null)) {
			result.rejectValue("email", "", "入力したメールアドレスはすでに登録されています。");
		}

		if (!(form.getPassword().equals(form.getConfirmationPassword()))) {
			
			result.rejectValue("password", "", "パスワードが一致していません");
			result.rejectValue("confirmationPassword", "", "");
		}
		
		

		if (result.hasErrors()) {
			return toRegister();
		}

		User user = new User();

		BeanUtils.copyProperties(form, user);
		registerUserService.register(user);

		return "redirect:/login-user/to-login";

	}

}
