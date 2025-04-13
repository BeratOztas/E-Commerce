package com.beratoztas.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("1001", "kayıt bulunamadı."),
	GENERAL_EXCEPTION("9999", "Genel bir hata oluştu."),
	USERNAME_OR_PASSWORD_INVALID("1004","Kullanıcı adı veya parola yanlış"),
	TOKEN_IS_EXPIRED("1002", "Token'ın süresi geçmiştir"),
	USER_NOT_FOUND("1003","Kullanıcı bulunamadı."),
	USERNAME_ALREADY_EXIST("1006","Kullanıcı ismi zaten var."),
	EMAIL_ALREADY_EXIST("1007","Email önceden alınmış."),
	USERS_NOT_FOUND("1004","Kullanıcılar bulunamadı."),
	CATEGORY_NOT_FOUND("1008","Category Not Found !!."),
	CATEGORIES_NOT_FOUND("1009","Categories Not Found !!."),
	PRODUCT_NOT_FOUND("1010","Product Not Found !!."),
	PRODUCTS_NOT_FOUND("1011","Products Not Found !!."),
	USERNAME_NOT_FOUND("1005","Kullanıcı adı bulunamadı.");
	
	private String code;
	private String message;
	
	private MessageType(String code,String message) {
		this.code=code;
		this.message=message;
	}
	
}
