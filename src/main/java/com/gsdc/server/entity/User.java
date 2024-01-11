package com.gsdc.server.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fathers")
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "studentOwner")
    private Set<Student> students;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        User user = new User();
        if (this == obj) {
            return true;
        }
        if (! (obj instanceof User)) {
            return false;
        }
        return (Objects.equals(username, user.username) && Objects.equals(email, user.email));
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
