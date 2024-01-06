package com.gsdc.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends BaseEntity{

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private PreferenceLanguage language;

    @ManyToOne
    @JoinColumn(name = "father_id", referencedColumnName = "id")
    private User studentOwner;
}
