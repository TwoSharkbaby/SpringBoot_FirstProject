package com.example.daun.repository;

import com.example.daun.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId); // 쿼리문과 파라매터값의 articleId 명이 같아야함

    List<Comment> findByNickname(String nickname); // resources의 META-INF에 orm.xml 파일로 쿼리문 구현

    @Query(value = "SELECT * FROM comment WHERE nickname LIKE '%i%'", nativeQuery = true)
    List<Comment> findByNicknameWith(); // 쿼리문과 파라매터값의 articleId 명이 같아야함


}
