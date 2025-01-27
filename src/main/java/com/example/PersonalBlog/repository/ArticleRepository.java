package com.example.PersonalBlog.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.PersonalBlog.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a ORDER BY a.publicationDate DESC, SIZE(a.likes) DESC")
    Page<Article> findAllOrderByPublicationDateDesc (Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY SIZE(a.likes) DESC, a.publicationDate DESC")
    Page<Article> findAllSortedByLikes(Pageable pageable);
}
