package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity	//디비에 테이블을 생성
public class Image {	//N, 1
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//번호 증가 전략이 데이터베이스를 따라간다(AI)랑 비슷한듯?
	private int id;

	private String caption;//오늘 나 너무 피곤해! 같은 문장
	private String postImageUrl;	//사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 => db에 저장된 경로를 insert

	@JoinColumn(name="userid")	//외래키로 저장되므로 외래키이름 지정
	@ManyToOne
	private User user;	//1, 1
	
	//이미지 좋아요
	
	//댓글
	
	private LocalDateTime createDate;
	
	@PrePersist	//db에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
	
	
}
