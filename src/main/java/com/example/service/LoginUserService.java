package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログイン情報を操作するサービス.
 * 
 * @author oyamadakenji
 *
 */
@Service
@Transactional
public class LoginUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	/**
	 * メールアドレスからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報 存在しない場合はnullを返します
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * ログインをします.
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 管理者情報　存在しない場合はnullが返ります
	 */
	public User login(String email, String passward) {
	  User user = userRepository.findByEmail(email);
	  // パスワード一致チェック
	  if(passwordEncoder.matches(passward, user.getPassword())) {
	    return user;
	  }
	  return null;
	}

}
