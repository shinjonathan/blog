package com.greenstreetdigital.blog.article;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest
@AutoConfigureRestDocs
@ContextConfiguration(classes = ArticleHandler.class)
class ArticleHandlerTest {

    @MockBean
    ArticleService articleService;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetAllArticles() {
        when(articleService.getAll())
                .thenReturn(Flux
                        .just(new EasyRandom().nextObject(Article.class))
                );

        webClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().isOk().expectBody().consumeWith(
                        document("getArticles",
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                responseBody())
        );
    }

    @Test
    void testGetSingleArticle() {
        when(articleService.get(any()))
                .thenReturn(Mono.just(getArticleSample()));

        webClient.get()
                .uri("/articles/{id}", "article-id")
                .exchange()
                .expectStatus().isOk().expectBody().consumeWith(
                        document("getArticle",
                                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                                responseBody())
        );
    }

    @Test
    void testSaveArticle() {
        given(articleService.save(any()))
                .willReturn(Mono.just(getArticleSample()));

        webClient.post()
                .uri("/articles")
                .body(fromValue(getArticleSample()))
                .exchange()
                .expectStatus().isCreated().expectBody().consumeWith(document("postArticle",
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                responseBody())
        );

        verify(articleService).save(any());
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