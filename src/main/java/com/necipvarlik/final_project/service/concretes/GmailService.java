package com.necipvarlik.final_project.service.concretes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.necipvarlik.final_project.dto.EmailDetails;
import com.necipvarlik.final_project.service.abstracts.EmailService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class GmailService implements EmailService {

	private final JavaMailSender mailSender;
	
	//bu sayede app proptaki kullanıcı mailini alıyoruz 
	@Value("${spring.mail.username}") private String sender;
	
	
	@Override
	public String send(EmailDetails email) {
		
		SimpleMailMessage mail = new SimpleMailMessage(); 
		
		mail.setFrom(sender);
		mail.setSubject(email.getSubject());
		mail.setText(email.getBody());
		mail.setTo(email.getReceiver());
		
		try {
			mailSender.send(mail);
		} catch (Exception e) {
			String.format("E-mail %s alıcısına gönderilemedi.", email.getReceiver());
		}
		
		
		return String.format("E-mail %s alıcısına başarılı bir şekilde gönderildi.", email.getReceiver());
	}

}
