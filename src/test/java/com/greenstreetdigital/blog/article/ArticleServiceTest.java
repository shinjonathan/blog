package com.greenstreetdigital.blog.article;


import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;


@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    public void before() {
        articleService.saveArticle(new Article(
                "id",
                "Description",
                "Title",
                "Body",
                "slug",
                LocalDateTime.now(),
                LocalDateTime.now(),
                true,
                "id",
                null
        ));
    }


    @Test
    public void testGetArticle() {
        StepVerifier.create(articleService.getArticles())
            .expectNextCount(1).verifyComplete();
    }

    @Test
    public void testAddArticle() {
        Article article = new EasyRandom().nextObject(Article.class);
        StepVerifier.create(articleService.saveArticle(article))
                .expectNextCount(1)
                .verifyComplete();
    }

}
