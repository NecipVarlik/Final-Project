package com.necipvarlik.final_project.service.concretes;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.necipvarlik.final_project.service.abstracts.IFileStorageService;

@Service
public class FileStorageService implements IFileStorageService {

	// uploads klasörünün sistemdeki dizin yolunu oluşturmak için
	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") +
			"/src/main/resources/static" +
			"/uploads";

	@Override
	public void save(MultipartFile file) throws IOException , FileAlreadyExistsException{

		Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		Files.write(fileNameAndPath, file.getBytes());
	}

}
