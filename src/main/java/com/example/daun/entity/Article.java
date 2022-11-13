package com.example.daun.entity;

import lombok.*;

import javax.persistence.*;

@Entity // db가 해당 객체를 인식함
@AllArgsConstructor  // lombok 모든생성자
@NoArgsConstructor  // lombok 생성자
@ToString // lombok tostring
@Getter // lombok 게터
@Setter
@SequenceGenerator(
    name = "USER_SEQ_GEN1",
    sequenceName = "SEQUENCE1",
    initialValue = 1,
    allocationSize = 1
)
public class Article {

  @Id  // 대표값
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "USER_SEQ_GEN1"
  )
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
