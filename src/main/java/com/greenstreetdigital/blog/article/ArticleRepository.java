package com.greenstreetdigital.blog.article;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
}
