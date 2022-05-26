package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//di를 위한선언
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly=true)	//영속성 컨텍스트 변경 감지를해서, 더티체킹, flush(반영)
	public Page<Image> 이미지스토리(int principalId,Pageable pageable){
		Page<Image> images=imageRepository.mstory(principalId,pageable);
		return images;
	}
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto,PrincipalDetails principalDetails) {
		UUID uuid=UUID.randomUUID();	//uuid(파일 랜덤으로 키생성?)
		String imageFileName=uuid+"_"+imageUploadDto.getFile().getOriginalFilename();	//사진.jpg
		System.out.println("이미지 파일이름:"+imageFileName);
		
		Path imageFilePath=Paths.get(uploadFolder+imageFileName);
		
		//통신,I/O -> 예외가 발생할 수 잇다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//image테이블에 저장
		Image image=imageUploadDto.toEntity(imageFileName,principalDetails.getUser());	
		imageRepository.save(image);
		
		//System.out.println(imageEntity.toString());
	}
	
	
}
