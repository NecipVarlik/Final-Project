package com.necipvarlik.final_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDetails {

	private String receiver;
	
	private String subject;
	
	private String body;
}
