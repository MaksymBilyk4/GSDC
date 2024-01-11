package com.gsdc.server.dto.user;

import com.gsdc.server.dto.auth.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private JwtResponse jwtResponse;

    private Long id;

    private String key;

    private String email;

    // Set of students ids
    private Set<Long> studentsIds = new HashSet<>();

    // Set of students keys (uuid)
    private Set<String> studentKeys = new HashSet<>();

    @Override
    public String toString() {
        return "UserResponse{" +
                "jwtResponse=" + jwtResponse +
                ", id=" + id +
                ", key='" + key + '\'' +
                ", email='" + email + '\'' +
                ", studentsIds=" + studentsIds +
                ", studentsIds=" + studentKeys +
                '}';
    }
}
