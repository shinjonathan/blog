package com.greenstreetdigital.blog.article;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = ArticleHandler.class)
class ArticleHandlerTest {

    @MockBean
    ArticleService articleService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testArticleRoutes() {
        when(articleService.getArticles()).thenReturn(Flux.just(new EasyRandom().nextObject(ArticleResponse.class)));

        webClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().isOk();
    }
}