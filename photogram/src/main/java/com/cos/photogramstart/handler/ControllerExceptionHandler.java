package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

//예외를 모두 낚아챈다
@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	//runtimeExceptiom을 
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		
		//CMRespDto, Script 비교
		//1. 클라이언트에게 응답할 때는 Script 좋음
		//2. AJax통신 - CMRespDto 좋음
		//3. Android 통신 - CMRespDto 좋음
		return Script.back(e.getErrorMap().toString());
	}
	
	//runtimeExceptiom을 
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<CMRespDto<?>> validationException(CustomValidationApiException e) {
		System.out.println("===================나실행됨????????????=================");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<CMRespDto<?>> apivalidationException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomException.class)
	public String Exception(CustomException e) {
		return Script.back(e.getMessage());
	}
}
