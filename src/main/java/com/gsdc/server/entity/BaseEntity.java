package com.gsdc.server.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@Getter
@NoArgsConstructor
public class BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String key = UUID.randomUUID().toString();
}
