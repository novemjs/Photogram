package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor	//final 필드를 초기화 할때 사용
@Controller	//IoC 등록,파일을 리턴하는 컨트롤러
public class AuthController {

	
	@Autowired
	private final AuthService authService;
	
	/*
	 * //생성자로 의존성 주입(IoC등록 되어잇으므로 가능) public AuthController(AuthService authService)
	 * { this.authService=authService; }
	 */
	
	@GetMapping("/auth/signin")
	public String signinin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입 버튼 클릭 => auth/signup -> auth/signin
	//가입버튼 x
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {	
		if(bindingResult.hasErrors()) {
			Map<String,String> errorMap=new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("======================");
				System.out.println(error.getDefaultMessage());
				System.out.println("======================");
			}
			throw new CustomValidationException("유효성 검사 실패함",errorMap);
		}else {
			// User <= SignupDto값
			User user=signupDto.toEntity();
			
			User userEntity=authService.회원가입(user);
			System.out.println(userEntity);
			return "auth/signin";
		}
		
	}
}
