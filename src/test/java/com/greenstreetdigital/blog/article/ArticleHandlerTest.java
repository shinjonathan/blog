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


    public Snippet[] getArticleResponseFields() {
        return new Snippet[] {
                responseFields(fieldWithPath("id")
                        .description("Unique ID for blog article")),
                responseFields(fieldWithPath("title")
                        .description("Title of article\n300 character limit")),
                responseFields(fieldWithPath("previewText")
                        .description("Short text to display\nSupports markdown")),
                responseFields(fieldWithPath("slug")
                        .description("A single word to be used to locate an article in lieu of an id")),
                responseFields(fieldWithPath("createTime")
                        .description("Date when article was first created\nTime served in UTC in yyyy-MM-dd HH:mm:ss")),
                responseFields(fieldWithPath("updateTime")
                        .description("Date when article was last updated\nTime served in UTC in yyyy-MM-dd HH:mm:ss\"")),
                responseFields(fieldWithPath("publishTime")
                        .description("Date when the article has been published\nTime served in UTC in yyyy-MM-dd HH:mm:ss")),
                responseFields(fieldWithPath("published")
                        .description("Denotes if the article has been published.")),
                responseFields(fieldWithPath("metaData")
                        .description("Any additional fields not tracked by this API"))
        };
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