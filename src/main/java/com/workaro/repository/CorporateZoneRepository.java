package com.workaro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.workaro.model.CorporateZone;
import com.workaro.model.ResumeInfo;

public interface CorporateZoneRepository extends JpaRepository<CorporateZone, Long>{

}
