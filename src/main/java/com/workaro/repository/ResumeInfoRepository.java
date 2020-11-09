package com.workaro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.workaro.model.ResumeInfo;

@Repository
public interface ResumeInfoRepository extends JpaRepository<ResumeInfo, Long>{

	@Query(value="Select r from ResumeInfo r where resume IS NOT NULL ORDER BY id desc")
	List<ResumeInfo> findAllResume(); 	

	@Query(value="Select r from ResumeInfo r where resume IS NULL ORDER BY id desc")
	List<ResumeInfo> findContactList(); 	
}
