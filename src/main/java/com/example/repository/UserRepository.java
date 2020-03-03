package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ログイン時にuserテーブルを操作するリポジトリ.
 * 
 * @author oyamadakenji
 *
 */
@Repository
public class UserRepository {

	/**
	 * Userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザー情報をDBに登録します.
	 * 
	 * @param user ユーザーの情報の入ったオブジェクト
	 */
	public void insert(User user) {
		String sql = "INSERT INTO users (name, email, password, zipcode, address, telephone) VALUES (:name, :email, :password, :zipcode, :address, :telephone)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);

	}

	/**
	 * メールアドレスからユーザー情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザーの情報の入ったオブジェクト
	 */
	public User findByEmail(String email) {
		String sql = "select id, name, email, password, zipcode, address, telephone from  users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * ユーザーIDでユーザー情報を取得するメソッド.
	 * 
	 * @param id ユーザーID
	 * @return
	 */
	public User findById(int id) {
		String sql = "select id,name, email, password, zipcode, address, telephone from users where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		System.out.println(userList);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	

}
