package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
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
