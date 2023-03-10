package com.adaskalov.ibproject.service;

import com.adaskalov.ibproject.model.Medicine;
import com.adaskalov.ibproject.model.User;

import java.util.List;

public interface UserService {
    public User getUserById(Long id) throws Exception;
    public User saveUser(User user);
    void addMeds(Medicine medicine);
    void deleteMeds(Medicine medicine);

    List<User> findAllPatients();
}
