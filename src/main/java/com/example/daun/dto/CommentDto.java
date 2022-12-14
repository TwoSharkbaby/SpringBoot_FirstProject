package com.example.daun.dto;

import com.example.daun.entity.Comment1;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {

    private Long id;
    @JsonProperty("article_id") // json에서는 article_id로 날아오니 articleId로 인식 프로퍼티
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment1 comment) {
        return new CommentDto(
            comment.getId(),
            comment.getArticle().getId(),
            comment.getNickname(),
            comment.getBody()
        );
    }
}
