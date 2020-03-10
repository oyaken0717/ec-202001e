package com.example.domain;

/**
 * クレジットAPIに繋いで返ってきた情報がここに入る.
 * 
 * @author oyamadakenji
 *
 */
public class Credit {

	/** 成功か失敗かが端的に格納される */
	private String status;

	/** error_codeに対応したが格納される */
	private String message;
	
	/** 独自のエラーコードが格納される */
	private String error_code;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@Override
	public String toString() {
		return "Credit [status=" + status + ", message=" + message + ", error_code=" + error_code + "]";
	}

	
}
