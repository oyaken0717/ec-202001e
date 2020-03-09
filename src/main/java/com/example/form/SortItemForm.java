package com.example.form;

/**
 * 並び替えの情報を受け取るフォーム.
 * @author ashibe
 *
 */
public class SortItemForm {
	
	/**
	 * 要素
	 */
	public String element;

	@Override
	public String toString() {
		return "SortItemForm [element=" + element + "]";
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

}


