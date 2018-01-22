package com.aer.mapping;

import com.aer.entities.PrivilegeEntity;
import com.aer.model.Privilege;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PrivilegeMapper extends EntityMapper<Privilege, PrivilegeEntity> {

    PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);


}
