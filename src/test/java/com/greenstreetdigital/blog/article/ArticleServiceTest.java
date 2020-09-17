package com.greenstreetdigital.blog.article;


import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;


@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void testGetArticle() {
        Article article = new EasyRandom().nextObject(Article.class);
        articleService.save(article).block();
        StepVerifier.create(articleService.getAll())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void testAddArticle() {
        Article article = new EasyRandom().nextObject(Article.class);
        StepVerifier.create(articleService.save(article))
                .expectNextCount(1)
                .verifyComplete();
    }

}
