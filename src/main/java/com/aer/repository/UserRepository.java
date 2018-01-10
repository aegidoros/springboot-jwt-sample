package com.aer.repository;

import com.aer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fan.jin on 2016-10-15.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
    User findByEmail(String email);
}

