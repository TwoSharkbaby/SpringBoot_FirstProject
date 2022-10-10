package com.example.daun.dto;


import com.example.daun.entity.Article;
import lombok.*;

@AllArgsConstructor  // lombok 모든생성자
@ToString // lombok tostring
@NoArgsConstructor // lombok 생성자
@Getter // lombok 게터
@Setter
public class ArticleFrom {

    private Long id;
    private String title;
    private String content;
    public Article toEntity() { // 받은 데이터를 db가 이해할수있는 형식으로 변환
        return new Article(id, title, content);
    }

}
