package com.example.daun.service;

import com.example.daun.dto.CommentDto;
import com.example.daun.entity.Article;
import com.example.daun.entity.Comment;
import com.example.daun.repository.ArticleRepository;
import com.example.daun.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; // 서비스에서 리파지토리 연결

    @Autowired
    private ArticleRepository articleRepository; // Article도 comment와 마찬가지로 db상호 연결 해야되기때문에 연결

    public List<CommentDto> comments(Long articleId) {
/*        List<Comment> comments = commentRepository.findByArticleId(articleId); // comment에서 articleid와 일치하는 데이터 가져오기
        List<CommentDto> dtos = new ArrayList<CommentDto>(); //가져온데이터(엔티티)를 commentdto로 변환
        for (int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }
        return dtos;*/
        return commentRepository.findByArticleId(articleId)
            .stream()
            .map(comment -> CommentDto.createCommentDto(comment))
            .collect(Collectors.toList());  //  위과정 스트림으로 간소화
    }

    @Transactional // 실행과정에 실패하면 롤백
    public CommentDto create(Long articleId, CommentDto dto) {
        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다")); // articleid가 없으면 예외처리
        Comment comment = Comment.createCommnet(dto, article); // 댓글 엔티티 생성
        Comment created = commentRepository.save(comment); // db로 저장
        return CommentDto.createCommentDto(comment); // dto로 변경해서 반환
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다"));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment tatget = commentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("덧글 삭제 실패! 대상 댓글이 없습니다"));
        commentRepository.delete(tatget);
        return CommentDto.createCommentDto(tatget);
    }
}
