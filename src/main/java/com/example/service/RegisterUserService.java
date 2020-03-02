package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * 
 * ユーザー登録の業務処理を行うサービスクラス.
 * 
 * @author masashi.nose
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * ユーザー情報をDBに登録します.
	 * 
	 * @param user ユーザー情報が入ったオブジェクト
	 */
	public void register(User user) {
		userRepository.insert(user);
	}
	
	/**
	 * メールアドレスからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報 存在しない場合はnullを返します
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
