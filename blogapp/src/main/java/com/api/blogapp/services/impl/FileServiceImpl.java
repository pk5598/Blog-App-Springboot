package com.api.blogapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.blogapp.services.FileService;
@Service
public class FileServiceImpl implements FileService{


	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name
				String imgName=file.getOriginalFilename();
				
				// full path
				
				
				String randomID=UUID.randomUUID().toString();
				String filename1=randomID.concat(imgName.substring(imgName.lastIndexOf(".")));
				String filePath=path+File.separator+filename1;
				
				
				//create img folder if not created
				File f=new File(path);
				if (!f.exists()) {
					f.mkdir();
				}
				
				//file copy(save) in image folder

				Files.copy(file.getInputStream(), Paths.get(filePath));
				
				return imgName;
	}


	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream iStream=new FileInputStream(fullPath);
		//db logic to return input stream
		return iStream;
	}

}
