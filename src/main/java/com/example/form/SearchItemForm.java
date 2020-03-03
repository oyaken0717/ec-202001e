package com.example.form;

/**
 * 検索された名前を扱うフォーム.
 * @author ashibe
 *
 */
public class SearchItemForm {
	
	/**
	 * 名前
	 */
	private String name;

	@Override
	public String toString() {
		return "SearchItemForm [name=" + name + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
