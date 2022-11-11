package com.niit.UserAuthenticationService.repository;

import com.niit.UserAuthenticationService.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAuthenticationRepository extends JpaRepository<User,String> {
    public User findByUserEmailAndPassword(String UserEmail,String password);
}
