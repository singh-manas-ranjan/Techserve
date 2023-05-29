package com.techserve.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

public class ImageHelper {
		public String saveImage(MultipartFile file) throws IOException {
			File fileLocation = new ClassPathResource("static/image").getFile();
			String fileName = file.getOriginalFilename();
			if(file.getOriginalFilename().length() != 32 && !fileName.contains("-")) {
				fileName = UUID.randomUUID().toString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			}
			Path path = Paths.get(fileLocation.getAbsolutePath()+File.separatorChar+fileName);
			Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		}
}
