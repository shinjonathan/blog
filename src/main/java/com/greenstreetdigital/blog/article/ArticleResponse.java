package com.greenstreetdigital.blog.article;

import lombok.Value;

@Value
public class ArticleResponse {
    String id;
    String shortDescription;
    String articleText;
    String userId;
}
