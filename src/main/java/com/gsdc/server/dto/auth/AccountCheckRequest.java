package com.gsdc.server.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCheckRequest {
    private String login;
}