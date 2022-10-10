package com.example.daun.service;

import com.example.daun.dto.ArticleFrom;
import com.example.daun.entity.Article;
import com.example.daun.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service // RestController와 Repository의 사이에서 구동
public class ArticleService {

    @Autowired // DI(의존성주입)
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleFrom from) {
        Article article = from.toEntity();
        if (article.getId() != null){ // 새로 만드는건데 기존에 있는 아이디 수정 방지
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleFrom from) {
        Article article = from.toEntity(); // 입력 받은 데이터들을 article으로 변환하고 임시로 넣어두고
        Article target = articleRepository.findById(id).orElse(null); // 받은 아이디를 바탕으로 db 데이터를 타겟에 저장하고
        if (target == null || id != article.getId()) {                  // db 타겟이 없거나 json으로 입력받은 id와 db id가 다르면 null
            return null;
        }
        target.patch(article); // 임시로 넣어둔 데이터안에 모든 컬럼의 데이터가 없을때 예외처리
        Article updated = articleRepository.save(target);  // 해당 db 데이터에 임시로 넣어둔 데이터를 저장
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null){
            return null;
        }
        articleRepository.delete(target);
        return target;
    }
    @Transactional  // 트렌잭션 발동
    public List<Article> createArticles(List<ArticleFrom> froms) {
        List<Article> articleList = froms.stream()
            .map(from -> from.toEntity()) // froms를 entity묶음으로 변환
            .collect(Collectors.toList());
        articleList.stream()
            .forEach(article -> articleRepository.save(article)); // entity묶음을 db로 저장
        articleRepository.findById(-1L).orElseThrow(  // 강제 예외 발생
            (() -> new IllegalArgumentException("결제 실패!"))
        );
        return articleList;
    }
}
