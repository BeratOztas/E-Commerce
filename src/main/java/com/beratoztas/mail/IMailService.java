package com.beratoztas.mail;

import java.math.BigDecimal;

public interface IMailService {

	public void sendOrderConfirmationEmail(String to, Long orderId, BigDecimal totalPrice);

	public void sendEmail(String to, String subject, String body);

}
