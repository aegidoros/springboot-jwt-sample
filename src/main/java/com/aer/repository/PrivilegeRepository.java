package com.aer.repository;

import com.aer.entities.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Long> {

    PrivilegeEntity findByName(String name);
}
