package com.gsdc.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity{

    // id here only (extended by base entity)
    @Column(name = "description", nullable = true)
    private String description;
}
