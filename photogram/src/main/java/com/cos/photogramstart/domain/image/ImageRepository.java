package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
 
public interface ImageRepository extends JpaRepository<Image,Integer>{
	
	@Query(value="SELECT * FROM image WHERE userid IN(SELECT ToUserId FROM subscribe WHERE fromUserId=:principalId) order by id desc",nativeQuery=true)
	Page<Image> mstory(int principalId,Pageable pageable);
}
