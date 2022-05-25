package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

	//네이티브 쿼리
	@Modifying	//insert,delete,update를 네이티브 쿼리로 작성하려면 해당 어노테이션이 필요하다
	@Query(value="insert into subscribe(fromUserId,ToUserId,createDate) values(:fromUserId,:ToUserId,now())",nativeQuery=true)
	void mSubscribe(int fromUserId,int ToUserId);	//성공하면 1(변경된 행의 개수가 리턴된다),실패 -1 그래서 int씀 
	
	// :은 바인딩하겟다는 의미
	@Modifying
	@Query(value="delete from subscribe where fromUserId=:fromUserId and ToUserId=:ToUserId",nativeQuery=true)
	void mUnSubscribe(int fromUserId,int ToUserId);
	
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:principalId AND toUserId=:pageUserId",nativeQuery = true)
	int mSubscribeState(int principalId,int pageUserId);
	
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:pageUserId",nativeQuery = true)
	int mSubscribeCount(int pageUserId);
}
