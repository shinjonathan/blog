package com.greenstreetdigital.blog.article;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

@WebFluxTest
@AutoConfigureRestDocs
@ContextConfiguration(classes = ArticleHandler.class)
class ArticleHandlerTest {

    @MockBean
    ArticleService articleService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetArticles() {
        when(articleService.getArticles())
                .thenReturn(Flux
                        .just(new EasyRandom().nextObject(Article.class))
                );

        webClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().isOk().expectBody().consumeWith(
                        document("articles",
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                responseBody()
        ));
    }

    @Test
    void testGetArticle() {
        when(articleService.getArticle(any()))
                .thenReturn(Mono
                        .just(getArticleSample())
                );

        webClient.get()
                .uri("/articles/{id}", "article-id")
                .exchange()
                .expectStatus().isOk().expectBody().consumeWith(
                        document("article",
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                responseBody()));
    }

    public Article getArticleSample() {
        return new Article(
                "id",
                "Title",
                "The Preview Text",
                "Body",
                "slug-of-article",
                "USERID",
                LocalDateTime.of(2020,9,11,0,0),
                LocalDateTime.of(2020,9,11,0,0),
                LocalDateTime.of(2020,9,11,0,0),
                true,
                Collections.emptyMap()
        );
    }
}