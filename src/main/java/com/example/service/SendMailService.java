package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.MailText;
import com.example.domain.Order;

@Service
public class SendMailService {

	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private MailText mailText;
	
	/**
	 * 注文完了メールの設定メソッド.
	 * 
	 * @param order
	 */
	@Async
	public void sendOrderMail(Order order) {
		String to=order.getDestinationEmail();
		String subject="[ラクラクラーメン]ご注文を承りました";
		mailText.setVariable("order",order);
		String context=mailText.process("message.txt");
		
		sendMail(to,subject,context);
	}
	/**
	 * 注文完了メールを送信するメソッド.
	 * 
	 * @param to 送信先メールアドレス
	 * @param subject　メールの件名
	 * @param context　メール本文
	 */
	private void sendMail(String to,String subject,String context) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(context);
		
		mailSender.send(message);
	}
	
}