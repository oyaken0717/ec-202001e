package com.example;

import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@Scope("prototype")
public class MailText {

	/** Thymeleafのテンプレートエンジン*/
	private final TemplateEngine templateEngine;
	/** Thymeleafが使う変数を格納するオブジェクト */
	private final Context context;
	
	public MailText(TemplateEngine templateEngine) {
		this.templateEngine=templateEngine;
		context=new Context(Locale.getDefault());
	}
	public String process(String template) {
		return templateEngine.process(template, context);
	}
	public void setVariable(String name,Object value) {
		context.setVariable(name,value);
	}
}
