package com.beratoztas.exception;

import lombok.Getter;

@Getter
public enum MessageType {

	NO_RECORD_EXIST("1001", "kayıt bulunamadı."),
	GENERAL_EXCEPTION("9999", "Genel bir hata oluştu."),
	UNAUTHORIZED("401","User authentication required"),
	ACCESS_DENIED("1025","Access Denied!"),
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
	CART_NOT_FOUND("1012","CART Not Found !!.."),
	CART_ITEM_NOT_FOUND("1013","Cart Item Not Found !!.."),
	INVALID_ADDRESS("1017","Invalid Address.!"),
	ADDRESS_REQUIRED("1018","Address required..!"),
	ADDRESS_NOT_FOUND("1019","Addres Not Found..!"),
	CART_NULL_OR_EMPTY("1016","Cart null or empty...!"),
	ORDER_NOT_FOUND("1015","Order Not Found ..!!"),
	INVALID_QUANTITY("1014","Quantity must be at least 1. "),
	ORDERS_NOT_FOUND("1022", "No orders found."),
	INVALID_REFRESH_TOKEN("1020","Refresh Token Invalid.!"),
	MAIL_SEND_FAILED("1021","MAIL COULD NOT SEND"),
	USERNAME_NOT_FOUND("1005","Kullanıcı adı bulunamadı.");

	private String code;
	private String message;

	private MessageType(String code,String message) {
		this.code=code;
		this.message=message;
	}

}
