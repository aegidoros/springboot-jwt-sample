package com.aer.service.impl;

import com.aer.entities.UserEntity;
import com.aer.mapping.UserMapper;
import com.aer.model.User;
import com.aer.repository.UserRepository;
import com.aer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userMapper.toDto(userRepository.findByUsername(username));
    }

    public User findById(Long id) throws AccessDeniedException {
        return userMapper.toDto(userRepository.findOne(id));
    }

    public List<User> findAll() throws AccessDeniedException {
        ArrayList<UserEntity> userEntities = (ArrayList<UserEntity>) userRepository.findAll();
        return userEntities.stream().map(UserEntity -> userMapper.toDto(UserEntity))
                .collect(Collectors.toList());
    }

}
