package com.cos.photogramstart.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //IoC등록 transaction관리
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional	//Write(insert,update,delete)할때
	public User 회원가입(User user) {
		// 회원가입 진행
		String rawPassword=user.getPassword();
		String encPassword=bCryptPasswordEncoder.encode(rawPassword);	//암호화된 패스워드
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");//권한 관리자는 ROLE_ADMIN
		
		User userEntity=userRepository.save(user);
		return userEntity;
	}
}
