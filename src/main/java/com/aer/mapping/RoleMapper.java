package com.aer.mapping;

import com.aer.entities.RoleEntity;
import com.aer.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<Role, RoleEntity> {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
}
