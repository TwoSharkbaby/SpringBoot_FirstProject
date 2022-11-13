package com.example.daun.repository;

import com.example.daun.entity.Comment1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment1, Long> {

    List<Comment1> findByArticleId(Long articleId); // 쿼리문과 파라매터값의 articleId 명이 같아야함

    List<Comment1> findByNickname(String nickname); // resources의 META-INF에 orm.xml 파일로 쿼리문 구현

    List<Comment1> findByNicknameWith(); // 쿼리문과 파라매터값의 articleId 명이 같아야함


}
