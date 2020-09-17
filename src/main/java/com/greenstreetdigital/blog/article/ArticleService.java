package com.greenstreetdigital.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Flux<Article> getAll() {
        return articleRepository.findAll();
    }
    public Mono<Article> get(String articleId) {
        return articleRepository.findById(articleId);
    }

    public Mono<Article> save(Article article) {
        return articleRepository.save(article);
    }
}
