package com.example.PersonalBlog.controller;


import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PersonalBlog.service.ArticleService;
import com.example.PersonalBlog.service.UserService;
import com.example.PersonalBlog.model.Article;
import com.example.PersonalBlog.model.Comment;
import com.example.PersonalBlog.model.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    // go to home page
    @GetMapping({"", "/"})
    public String index(){
        return "redirect:/home";
    }

    //get all articles
    @GetMapping("/home")
    public String homePage(Model model, @RequestParam(defaultValue = "publicationDate") String sortBy,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size){

        Page<Article> articlePage = articleService.findAllSorted(sortBy, page, size);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "index";
                            }
        
        @GetMapping("/article/{id}")
        public String getArticle(@PathVariable("id") Long id, Authentication auth, Model model){
            Article article = articleService.getArticleById(id);
            if(article == null){
                return "/article/not-found";
            }
            model.addAttribute("article", article);
            if(auth == null){
                model.addAttribute("liked", false);
                model.addAttribute("logged", false);
            }
            else {
                User user = User.builder().email(auth.getName()).build();
                model.addAttribute("liked", article.getLikes().contains(user));
                model.addAttribute("logged", true);
            }
            return "article/article";
        }

        @GetMapping("/dashboard")
        public String getDashboard(@RequestParam(defaultValue = "1") int page, Model model){
            int pageSize = 10;
            Page<Article> articlPage = articleService.getArticles(PageRequest.of(page - 1, pageSize));
            model.addAttribute("articles", articlPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", articlPage.getTotalPages());
            model.addAttribute("pageNumbers", IntStream.rangeClosed(1,
                        articlPage.getTotalPages()).boxed().collect(Collectors.toList()));
            return "dashboard";
            }

        @GetMapping("/article/add")
        public String showAddArticleForm(Model model){
            model.addAttribute("article", new Article());
            return "article/add-article";
        }

        @PostMapping("/article/add")
        public String addArticle(@Validated @ModelAttribute Article article, BindingResult result){
            if(result.hasErrors()){
                return "article/add-article";
            }
            articleService.saveArticle(article);
            return "redirect:/dashboard";
        }
            
        @PostMapping("/article/like")
        public String like(@RequestParam("id") Long id, Authentication auth, Model model){
            Article article = articleService.getArticleById(id);
            User user = userService.findByEmail(auth.getName());
            if(article.getLikes() == null || !article.getLikes().contains(user)){
                article.like(user);
            } else {
                article.getLikes().remove(user);
            }
            articleService.updateArticle(article);
            return "redirect:/article/" + article.getId();
        }

        @PostMapping("/article/comment")
        public String comment(@RequestParam("id") Long id, @RequestParam("text") String text,
                            Authentication auth, Model model){
            Article article = articleService.getArticleById(id);
            article.comment(Comment.builder().article(article).user(userService.findByEmail(auth.getName()))
                    .text(text).build());
            articleService.updateArticle(article);
            return "redirect:/article/" + article.getId();
            
        }

        @PostMapping("/article/delete")
        public String delete(@RequestParam("id") Long id, Authentication auth, Model model){
            articleService.deleteArticle(id);
            return "redirect:/dashboard";
        }

        @GetMapping("/article/update")
        public String updateForm(@RequestParam("id") Long id, Authentication auth, Model model){
            model.addAttribute("article", articleService.getArticleById(id));
            return "/article/update";
        }

        @PostMapping("/article/update")
        public String update(@Validated @ModelAttribute Article article, Model model){
            articleService.saveArticle(article);
            return "redirect:/dashboard";
        }
}
