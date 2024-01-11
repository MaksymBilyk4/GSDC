package com.gsdc.server.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity{

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

}
