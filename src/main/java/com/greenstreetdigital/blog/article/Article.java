package com.greenstreetdigital.blog.article;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Value
@Document
public class Article {

    @Id
    String id;
    String shortDescription;
    String title;
    String body;
    String userId;

    Map<?, ?> metaData;
}
