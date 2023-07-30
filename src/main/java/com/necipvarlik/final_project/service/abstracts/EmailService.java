package com.necipvarlik.final_project.service.abstracts;

import com.necipvarlik.final_project.dto.EmailDetails;

public interface EmailService {

	String send(EmailDetails email);
}
