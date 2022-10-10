package com.example.daun.entity;

import lombok.*;

import javax.persistence.*;

@Entity // db가 해당 객체를 인식함
@AllArgsConstructor  // lombok 모든생성자
@NoArgsConstructor  // lombok 생성자
@ToString // lombok tostring
@Getter // lombok 게터
@Setter
public class Article {

  @Id  // 대표값
  @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 id를 자동 생성
  private Long id;

  @Column // db 컬럼
  private String title;

  @Column // db 컬럼
  private String content;

    public void patch(Article articleEntity) {
      if(articleEntity.title != null){  // 타이틀 데이터가 없을땐 기존 내용 유지
        this.title = articleEntity.title;
      }
      if (articleEntity.content != null){ // // 컨텐트 데이터가 없을땐 기존 내용 유지
        this.content = articleEntity.content;
      }
    }
}
