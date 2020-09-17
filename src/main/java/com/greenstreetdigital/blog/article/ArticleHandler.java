package com.greenstreetdigital.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Configuration
public class ArticleHandler {

    @Bean
    RouterFunction<?> routes(RequestHandler requestHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/articles"), requestHandler::getArticles)
                .andRoute(RequestPredicates.GET("/articles/{id}"), requestHandler::getArticle)
                .andRoute(RequestPredicates.POST("/articles"), requestHandler::createArticle);
    }

    @Component
    @RequiredArgsConstructor
    public static class RequestHandler {

        private final ArticleService articleService;

        public Mono<ServerResponse> getArticles(ServerRequest serverRequest) {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(articleService.getAll(), Article.class);
        }

        public Mono<ServerResponse> getArticle(ServerRequest serverRequest) {
            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(articleService.get(serverRequest.pathVariable("id")), Article.class);
        }

        public Mono<ServerResponse> createArticle(ServerRequest serverRequest) {

            Mono<Article> article = serverRequest.bodyToMono(Article.class);
            UUID uuid = UUID.randomUUID();

            return ServerResponse.created(
                    UriComponentsBuilder.fromPath("/articles/{id}").buildAndExpand(uuid).toUri())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(
                            fromPublisher(article.map(a ->  a.withId(uuid.toString()))
                                    .flatMap(articleService::save), Article.class));
        }

    }
}
