package com.necipvarlik.final_project.service.abstracts;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

	void save(MultipartFile file) throws IOException;
}
