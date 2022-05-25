package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(
	uniqueConstraints= {
			@UniqueConstraint(
				name="subscribe_uk",
				columnNames= {"fromUserid","ToUserId"}
			)
			
	}
)
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//번호 증가 전략이 데이터베이스를 따라간다(AI)랑 비슷한듯?
	private int id;
	
	@JoinColumn(name="fromUserId")	//컬럼명 만들어이렇게!
	@ManyToOne
	private User fromUser;
	
	@JoinColumn(name="ToUserId")
	@ManyToOne
	private User toUser;
	

	private LocalDateTime createDate;
	
	@PrePersist	//db에 insert 되기 직전에 실행
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
}
