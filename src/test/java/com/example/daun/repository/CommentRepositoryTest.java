package com.example.daun.repository;

import com.example.daun.entity.Article;
import com.example.daun.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest // jpa 연동 테스트
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByArticleId() {
        {   /* Case 1: 4번 게시글의 모든 댓글 조회 */
            Long articleId = 4L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ");
            Comment a = new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크의 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }
        {   /* Case 2: 1번 게시글의 모든 댓글 조회 */
            Long articleId = 1L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            List<Comment> expected = Arrays.asList();
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }
        {   /* Case 3: 9번 게시글의 모든 댓글 조회 */
            Long articleId = 9L;
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            List<Comment> expected = Arrays.asList();
            assertEquals(expected.toString(), comments.toString(), "9번 글의 모든 댓글을 출력");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        {   /* Case 1: "Park"의 모든 댓글 조회 */
            String nickname = "Park";
            List<Comment> comments = commentRepository.findByNickname(nickname);
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "굳 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);
            assertEquals(expected.toString(), comments.toString());
        }
        {   /* Case 2: "Kim"의 모든 댓글 조회 */
            String nickname = "Kim";
            List<Comment> comments = commentRepository.findByNickname(nickname);
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), nickname, "유튜브");
            List<Comment> expected = Arrays.asList(a, b, c);
            assertEquals(expected.toString(), comments.toString());
        }
        {   /* Case 3: null 의 모든 댓글 조회 */
            String nickname = null;
            List<Comment> comments = commentRepository.findByNickname(nickname);
            List<Comment> expected = Arrays.asList();
            assertEquals(expected, comments);
        }
        {   /* Case 4: "" 의 모든 댓글 조회 */
            String nickname = "";
            List<Comment> comments = commentRepository.findByNickname(nickname);
            List<Comment> expected = Arrays.asList();
            assertEquals(expected, comments);
        }
    }

    @Test
    void findByNicknameWith() {
        {    // Case 5: 닉네임에 "i"가 포함된 모든 댓글 조회
            List<Comment> comments = commentRepository.findByNicknameWith();
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Kim", "아이 엠 샘");
            Comment b = new Comment(3L, new Article(4L, "당신의 인생 영화는?", "댓글 ㄱ"), "Choi", "쇼생크의 탈출");
            Comment c = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Kim", "샤브샤브");
            Comment d = new Comment(6L, new Article(5L, "당신의 소울 푸드는?", "댓글 ㄱㄱ"), "Choi", "초밥");
            Comment e = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Kim", "유튜브");
            Comment f = new Comment(9L, new Article(6L, "당신의 취미는?", "댓글 ㄱㄱㄱ"), "Choi", "독서");
            List<Comment> expected = Arrays.asList(a, b, c, d, e, f);
            assertEquals(expected.toString(), comments.toString());
        }
    }
}