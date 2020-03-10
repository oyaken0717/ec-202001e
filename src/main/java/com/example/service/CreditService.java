package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Credit;
import com.example.form.OrderDestinationForm;

public class CreditService {

	   @Autowired
	    RestTemplate restTemplate;
	    
	    private static final String URL = "http://192.168.17.53:8080/sample-credit-card-web-api/credit-card/payment";
	    
	    /**
	     * クレジットAPIに接続する.
	     * 
	     * @param form 注文確認画面で入力されたクレジットカード情報が入っている
	     * @return APIから返ってきた情報がCreditクラスのフィールドの形に格納される
	     */
	    public Credit service(OrderDestinationForm form) {
	    	return restTemplate.postForObject(URL, form, Credit.class);
	    }
}
