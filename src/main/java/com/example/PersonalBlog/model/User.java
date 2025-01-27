package com.example.PersonalBlog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users", schema = "blogwebsite")
public class User {
    
    @Id
    @EqualsAndHashCode.Include
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String role;
}
