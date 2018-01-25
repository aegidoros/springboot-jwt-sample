package com.aer.mapping;


import com.aer.entities.UserEntity;
import com.aer.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserEntity> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    RoleMapper roleMapper = RoleMapper.INSTANCE;
    @Override
   // @Mapping(target = "role", expression = "java(roleMapper.toDTO(userEntity.getRole))")
    User toDto(UserEntity userEntity);

}
