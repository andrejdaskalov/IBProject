package com.adaskalov.ibproject.repository;

import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllByType(UserType type);
}
