package com.aer.mapping;

import com.aer.entities.PermissionEntity;
import com.aer.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PermissionMapper extends EntityMapper<Permission, PermissionEntity> {

    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);


}
