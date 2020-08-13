package com.greenstreetdigital.blog.article;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ArticleService {

    public Flux<ArticleResponse> getArticles() {
        return Flux.just(new ArticleResponse("ID", "ShortDescription", "ArticleText", "userId"));
    }
}
