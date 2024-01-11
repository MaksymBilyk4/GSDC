package com.gsdc.server.controller;

import com.gsdc.server.dto.auth.*;
import com.gsdc.server.dto.user.UserRequest;
import com.gsdc.server.dto.user.UserResponse;
import com.gsdc.server.entity.User;
import com.gsdc.server.facade.user.UserRequestMapper;
import com.gsdc.server.facade.user.UserResponseMapper;
import com.gsdc.server.service.UserService;
import com.gsdc.server.service.auth.JwtAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("${api.version}/auth")
public class AuthController {

    private final JwtAuthService jwtAuthService;
    private final UserService userService;
    private final UserResponseMapper responseMapper;
    private final UserRequestMapper requestMapper;

    @PostMapping("/account")
    public ResponseEntity<AccountCheckResponse> account(@RequestBody AccountCheckRequest authRequest) {
        final AccountCheckResponse res = jwtAuthService.account(authRequest);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> getAccessRefreshTokens(@RequestBody JwtRequest authRequest) {
        final JwtResponse res = jwtAuthService.login(authRequest);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserRequest userRequest) {
        User user = userService.create(requestMapper.convertToEntity(userRequest));

        return ResponseEntity.ok(responseMapper.convertToDto(user));
    }

    @PostMapping("/access")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse jwtResponse = jwtAuthService.getAccessToken(request.getRefreshToken());

        return ResponseEntity.ok(jwtResponse);
    }


    // endpoints for authorized user
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse jwtResponse = jwtAuthService.refresh(request.getRefreshToken());

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public void logout() {
        String username = (String) jwtAuthService.getAuthInfo().getPrincipal();
        jwtAuthService.deleteAllByLogin(username);
    }
}
