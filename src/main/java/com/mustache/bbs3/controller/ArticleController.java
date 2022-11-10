package com.mustache.bbs3.controller;

import com.mustache.bbs3.domain.dto.ArticleDto;
import com.mustache.bbs3.domain.entity.Article;
import com.mustache.bbs3.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createPage() {
        return "/new";
    }

    @PostMapping("")
    public String createArticle(ArticleDto articleDto) {
        Article savedArticle = articleRepository.save(articleDto.toEntity());
        return String.format("redirect:/articles/%d", savedArticle.getId());
    }

    @GetMapping("/{id}")
    public String showOne(@PathVariable Long id, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            model.addAttribute("article", optionalArticle.get());
            return "/show";
        } else {
            return "/error";
        }
    }
}
