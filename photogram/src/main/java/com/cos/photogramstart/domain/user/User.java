package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API(자바로 데이터를 영구적으로 저장(db)할 수 잇는 API를 제공)

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity	//디비에 테이블을 생성
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//번호 증가 전략이 데이터베이스를 따라간다(AI)랑 비슷한듯?
	private int id;
	
	@Column(length=20, unique=true)	//제약조건
	private String username;
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String name;
	private String website;	//웹 사이트
	private String bio;	//자기소개
	@Column(nullable=false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;	//사진
	private String role;	//권한
	
	//나는 연관관계의 주인이아니다. 그러므로 테이블에 컬럼을 만들지마라
	//User테이블을 select할때 해당 userid로 등록된 image들을 다가져와
	//Lazy=User를 select할때 해당 user id로 등록된 image들을 가지오지마 - 대신 getimages()함수의 image들이 호출될때 가져와
	//Eager=User를 select할때 해당 user id로 등록된 image들을 전부 join해서 가져와
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"user"})	//user의 getter가 무시된다
	private List<Image> images;	//양방향 매핑
	
	private LocalDateTime createDate;
	
	@PrePersist	//db에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
}
