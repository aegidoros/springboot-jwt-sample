package com.aer.service;

import com.aer.model.ApiClient;

public interface ApiClientService {

    ApiClient findByApiKey (String apiKey);
}
