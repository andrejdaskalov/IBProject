package com.adaskalov.ibproject.service.impl;

import com.adaskalov.ibproject.model.Medicine;
import com.adaskalov.ibproject.model.User;
import com.adaskalov.ibproject.model.UserType;
import com.adaskalov.ibproject.repository.UserRepository;
import com.adaskalov.ibproject.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(Exception::new);
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findById(Long.parseLong(username)).orElseThrow(Exception::new);
        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(u.getPassword())) //
                .roles(u.getType().name())
                .build();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addMeds(Medicine medicine) {

    }

    @Override
    public void deleteMeds(Medicine medicine) {

    }

    @Override
    public List<User> findAllPatients() {
        return userRepository.findAllByType(UserType.PATIENT);
    }


}
