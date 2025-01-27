package com.example.PersonalBlog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Page<Article> findAllSorted(String sortBy, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(sortBy.equals("likes")){
            return articleRepository.findAllSortedByLikes(pageable);
        }
        return articleRepository.findAllOrderByPublicationDateDesc(pageable);
    }

    // create an article
    public Article saveArticle (Article article){
        return articleRepository.save(article);
    }

    // Get a list of all articles
    public Page<Article> getArticles(PageRequest of){
        return articleRepository.findAllOrderByPublicationDateDesc(of);
    }

    // Get an article by its ID
    public Article getArticleById(Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // Update an article
    public Article updateArticle(Article updatedArticle){
        return articleRepository.save(updatedArticle);
    }

    // Delete an article by its ID
    public void deleteArticle(Long id){
        articleRepository.deleteById(id);
    }

}
