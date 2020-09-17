package com.greenstreetdigital.blog.article;

import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@Document
public class Article {

    @Id
    @With
    String id;

    String title;
    String previewText;

    String body;
    String slug;
    String userId;

    LocalDateTime createTime;
    LocalDateTime updateTime;
    LocalDateTime publishDate;
    boolean published;

    Map<?, ?> metaData;
}
