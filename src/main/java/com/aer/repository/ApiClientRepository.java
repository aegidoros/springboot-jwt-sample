package com.aer.repository;

import com.aer.entities.ApiClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiClientRepository extends CrudRepository<ApiClientEntity, Long> {
    ApiClientEntity findByApiKey(String apiKey);
}
