package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

// 어노테이션없이 IoC등록 자동으로 된다  <object,pk종류>
public interface UserRepository extends JpaRepository<User,Integer>{
	// JPA query method
	User findByUsername(String username);
}
