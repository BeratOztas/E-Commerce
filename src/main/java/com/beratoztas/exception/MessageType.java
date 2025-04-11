package com.beratoztas.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("1001", "kayıt bulunamadı."),
	GENERAL_EXCEPTION("9999", "Genel bir hata oluştu."),
	USERNAME_OR_PASSWORD_INVALID("1004","Kullanıcı adı veya parola yanlış"),
	TOKEN_IS_EXPIRED("1002", "Token'ın süresi geçmiştir"),
	USERNAME_NOT_FOUND("1005","Kullanıcı adı bulunamadı.");
	
	private String code;
	private String message;
	
	private MessageType(String code,String message) {
		this.code=code;
		this.message=message;
	}
	
}
