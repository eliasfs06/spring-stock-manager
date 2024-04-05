package com.eliasfs06.spring.stockmanager.repository;

import com.eliasfs06.spring.stockmanager.model.User;

public interface UserRepository extends GenericRepository<User> {
    User findByUsername(String username);
}
