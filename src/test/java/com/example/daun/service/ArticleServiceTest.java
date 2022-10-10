package com.example.daun.service;

import com.example.daun.dto.ArticleFrom;
import com.example.daun.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링부트와 연동되어 테스트 TDD
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        //Article c = new Article(4L, "다다다다", "3333"); //오류 유도
        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c)); // 예상
        List<Article> articles = articleService.index();  // 실제
        assertEquals(expected.toString(), articles.toString()); // 검증
    }

    @Test
    void show_succed_existed_id() {
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //Article expected = new Article(id, "가가가", "1111"); //오류 유도
        Article article = articleService.show(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_failed_not_existed_id() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.show(id); // 없으면 null를 리턴하기로 했음
        assertEquals(expected, article); // null은 toString이 불가
    }

    @Test
    @Transactional
        // 테스트를 클래스 단위로 할때 생성되거나 삭제되는 테스트가 있으면 다른 테스트에서
    void create_succed_existed_title_content() { // 실제 데이터가 변경되기에 트렌젝션을 걸어야함
        String title = "라라라라";
        String content = "4444";
        ArticleFrom from = new ArticleFrom(null, title, content);
        Article expected = new Article(4L, title, content);
        Article article = articleService.create(from);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_failed_included_id() {
        String title = "라라라라";
        String content = "4444";
        ArticleFrom from = new ArticleFrom(4L, title, content);
        Article expected = null;
        Article article = articleService.create(from);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_succed_exised_id_title_content() {
        Long id = 1L;
        ArticleFrom from = new ArticleFrom(id, "가가가", "111");
        Article expected = new Article(id, "가가가", "111");
        Article article = articleService.update(id, from);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_succed_exised_id_title() {
        Long id = 1L;
        ArticleFrom from = new ArticleFrom(id, "가가가", null);
        Article expected = new Article(id, "가가가", "1111");
        Article article = articleService.update(id, from);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_succed_exised_id_content() {
        Long id = 1L;
        ArticleFrom from = new ArticleFrom(id, null, "1");
        Article expected = new Article(id, "가가가가", "1");
        Article article = articleService.update(id, from);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_failed_not_exised_id() {
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleFrom from = new ArticleFrom(id, title, content);
        Article expected = null;
        Article article = articleService.update(id, from);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_failed_exised_only_id() {
        Long id = 1L;
        ArticleFrom from = new ArticleFrom(id, null, null);
        Article expected = new Article(id, "가가가가", "1111");
        Article article = articleService.update(id, from);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_succed_exised_id() {
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        Article article = articleService.delete(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_failed_not_exised_id() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.delete(id);
        assertEquals(expected, article);
    }
}