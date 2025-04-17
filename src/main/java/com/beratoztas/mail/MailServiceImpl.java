package com.beratoztas.mail;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements IMailService {

	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;

	public MailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendOrderConfirmationEmail(String to, Long orderId, BigDecimal totalPrice) {
		SimpleMailMessage message =new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Order Confirmation - Order #" + orderId);
		message.setText("Thank you for your order! 🎉\n\nOrder ID: " + orderId +
                "\nTotal: ₺" + totalPrice +
                "\n\nWe’ll notify you once your order is shipped.");
		mailSender.send(message);
	}

}
