package com.example.daun.repository;


import com.example.daun.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {  // 인터페이스사용 및 entity클래스와 대표값의 형태

    @Override
    ArrayList<Article> findAll(); // 기본 형식이 Iterable 인것을 ArrayList로 변환
}
