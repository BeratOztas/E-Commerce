package com.beratoztas.enums;

public enum OrderStatus {
	PENDING, // Sipariş yeni oluşturuldu
	PROCESSING, // Sipariş hazırlanıyor (hazırlık sürecinde)
	SHIPPED, // Sipariş kargoya verildi
	DELIVERED, // Sipariş kullanıcıya teslim edildi
	CANCELLED, // Sipariş iptal edildi
	RETURNED // Sipariş iptal edildi
}
