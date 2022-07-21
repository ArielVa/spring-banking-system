package com.example.banking.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.banking.aop.Loggable;

@Service
public class EmailService {

	@Autowired private JavaMailSender emailSender;
	
	@Loggable(className = "EmailService",
			success = "Email was sent successfully",
			failed = "Failed to send an email.",
			throwable = MessagingException.class)
	public void sendEmail(String sender, String[] to, String title, String body) throws MessagingException  {
    	MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(body);
        emailSender.send(message);        
    }
	
}
