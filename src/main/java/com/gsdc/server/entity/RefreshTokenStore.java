package com.gsdc.server.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "refres_jwt_store")
public class RefreshTokenStore extends BaseEntity{

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "refreshToken", nullable = false)
    private String refreshToken;

}
