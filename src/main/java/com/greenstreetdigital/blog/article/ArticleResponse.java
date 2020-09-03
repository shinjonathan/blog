package com.greenstreetdigital.blog.article;

import lombok.Value;

@Value
public class ArticleResponse {
    String id;
    String shortDescription;
    String articleText;
    String userId;

    public static ArticleResponse fromArticle(Article article) {
        return new ArticleResponse(article.getId(), article.getShortDescription(), article.getBody(), article.getUserId());
    }
}
