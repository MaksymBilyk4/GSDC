package com.gsdc.server.facade.user;

import com.gsdc.server.dto.user.UserResponse;
import com.gsdc.server.entity.Student;
import com.gsdc.server.entity.User;
import com.gsdc.server.facade.GeneralFacade;
import com.gsdc.server.service.auth.JwtAuthService;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapper extends GeneralFacade<User, UserResponse> {

    private final JwtAuthService jwtAuthService;

    public UserResponseMapper(JwtAuthService jwtAuthService) {
        super(User.class, UserResponse.class);
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    protected void decorateDto(UserResponse dto, User entity) {
        for (Student s: entity.getStudents()) {
            dto.getStudentsIds().add(s.getId());
            dto.getStudentKeys().add(s.getKey());
        }

        dto.setJwtResponse(jwtAuthService.getJwtResponse(entity));
    }
}

