package com.example.daun.entity;

import com.example.daun.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 해당 댓글 엔티티 여러개가 하나의 Article에 연결 ( 관계설정 )
    @JoinColumn(name = "article_id") // db의 article_id컬럼(FK)에 Article의 ID(PK)대표값을 저장
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createCommnet(CommentDto dto, Article article) {
        if (dto.getId() != null){
            throw new IllegalArgumentException("덧글 생성 실패! 덧글의 id가 없어야 합니다.");
        }
        if (dto.getArticleId() != article.getId()) {
            throw new IllegalArgumentException("덧글 생성 실패! 게시글의 id가 잘못되었습니다");
        }
        return new Comment(
            dto.getId(),
            article,
            dto.getNickname(),
            dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        if (this.id != dto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다");
        }
        if (dto.getNickname() != null){
            this.nickname = dto.getNickname();
        }
        if (dto.getBody() != null){
            this.body = dto.getBody();
        }
    }
}