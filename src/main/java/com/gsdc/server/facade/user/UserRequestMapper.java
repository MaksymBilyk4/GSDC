package com.gsdc.server.facade.user;

import com.gsdc.server.dto.user.UserRequest;
import com.gsdc.server.entity.User;
import com.gsdc.server.facade.GeneralFacade;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserRequestMapper extends GeneralFacade<User, UserRequest> {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserRequestMapper(BCryptPasswordEncoder passwordEncoder) {
        super(User.class, UserRequest.class);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void decorateEntity(User entity, UserRequest dto) {
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setStudents(new HashSet<>());
    }
}