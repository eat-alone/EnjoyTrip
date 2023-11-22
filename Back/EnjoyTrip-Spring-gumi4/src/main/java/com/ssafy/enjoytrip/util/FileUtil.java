package com.ssafy.enjoytrip.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

import com.ssafy.enjoytrip.board.model.FileInfoDto;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtil {
	
	private final String UPLOAD_PATH = "upload";

	
	public Resource readFileAsResource(final FileInfoDto file) throws IOException {
		
		String realPath = "C:" + File.separator + UPLOAD_PATH;
	    realPath = realPath + File.separator + file.getSaveFolder() + File.separator + file.getSaveFile();
        String filename = file.getSaveFile();
        Path filePath = Paths.get(realPath);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() == false || resource.isFile() == false) {
                throw new RuntimeException("file not found : " + filePath.toString());
            }
//            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
//
//            String s = "";
//            while((s = br.readLine()) != null){
//                System.out.println(s);
//            }
            System.out.println(resource);
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("file not found : " + filePath.toString());
        }
    }
}
