package com.aer.mapping;

import com.aer.entities.ApiClientEntity;
import com.aer.model.ApiClient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ApiClientMapper extends EntityMapper<ApiClient, ApiClientEntity> {

    ApiClientMapper INSTANCE = Mappers.getMapper(ApiClientMapper.class);
}
