package com.beratoztas.mail;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.beratoztas.exception.BaseException;
import com.beratoztas.exception.ErrorMessage;
import com.beratoztas.exception.MessageType;

import jakarta.mail.internet.MimeMessage;

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
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject("Order Confirmation - Order #" + orderId);
		message.setText("Thank you for your order! 🎉\n\nOrder ID: " + orderId + "\nTotal: ₺" + totalPrice
				+ "\n\nWe’ll notify you once your order is shipped.");
		mailSender.send(message);
	}

	@Override
	public void sendEmail(String to, String subject, String body) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,false);

			mailSender.send(message);
		} catch (jakarta.mail.MessagingException e) {
			throw new BaseException(new ErrorMessage(MessageType.MAIL_SEND_FAILED, "Failed to send email"+e.getMessage()));
		}

	}

}
