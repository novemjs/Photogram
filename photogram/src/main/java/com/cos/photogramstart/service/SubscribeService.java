package com.cos.photogramstart.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;	//Repository는 EntityManager를 구현해서 만들어져 있는 구현체
	
	@Transactional(readOnly=true)
	public List<SubscribeDto> 구독리스트(int principalId,int pageUserId){
		
		//쿼리 준비단계
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT u.id,u.username,u.profileImageUrl, ");
		sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId=? AND ToUserId=u.id),1,0) subscribeState, ");
		sb.append("if ((?=u.id),1,0) equalUserState ");
		sb.append("FROM user u inner join subscribe s ");
		sb.append("on u.id=s.ToUserId ");
		sb.append("WHERE s.fromUserId=? ");	//세미콜론 첨부하면안됨
		
		//첫번째 물음표 principalId
		//두번째 물음표 로그인한 아이디 principalId
		//마지막 물음표 pageUserId
		
		//쿼리 완성 부분
		Query query=em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리 실행 부분(qlrm 라이브러리 필요 = Dto에 DB결과를 매핑하기위해서)
		JpaResultMapper result=new JpaResultMapper();
		
		List<SubscribeDto> subscribeDtos=result.list(query, SubscribeDto.class);
		
		return subscribeDtos;
	}
	
	@Transactional
	public void 구독하기(int fromUserId,int ToUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, ToUserId);
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId,int ToUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, ToUserId);
	}
}
