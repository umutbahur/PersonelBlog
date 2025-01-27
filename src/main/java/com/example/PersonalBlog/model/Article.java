package com.example.PersonalBlog.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "articles", schema = "blogwebsite")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "article_seq")
    @SequenceGenerator(name = "articles_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Length(min = 1, max = 50)
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDate publicationDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "article")
    @Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_likes",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_email"))
    @Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<User> likes;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp updatedAt;

    public void like(User user){
        if(likes == null){
            likes = new HashSet<>();
        }
        likes.add(user);
    }

    public void comment(Comment comment){
        if(comments == null){
            comments = new HashSet<>();
        }
        comments.add(comment);
    }

    
}
