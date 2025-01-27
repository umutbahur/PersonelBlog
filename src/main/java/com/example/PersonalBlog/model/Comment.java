package com.example.PersonalBlog.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "comments", schema = "blogwebsite")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;
}
