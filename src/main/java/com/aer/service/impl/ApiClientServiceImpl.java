package com.aer.service.impl;

import com.aer.mapping.ApiClientMapper;
import com.aer.model.ApiClient;
import com.aer.repository.ApiClientRepository;
import com.aer.service.ApiClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiClientServiceImpl implements ApiClientService {

    @Autowired
    private ApiClientRepository apiClientRepository;

    private ApiClientMapper apiClientMapper = ApiClientMapper.INSTANCE;

    @Override
    public ApiClient findByApiKey(String apiKey) {
        return apiClientMapper.toDto(apiClientRepository.findByApiKey(apiKey));
    }
}
